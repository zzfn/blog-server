package com.api.jello.controller;

import com.alibaba.fastjson.JSON;
import com.api.jello.dao.UserDao;
import com.api.jello.entity.User;
import com.api.jello.util.JwtTokenUtil;
import com.api.jello.util.RedisUtil;
import com.api.jello.util.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * USER(TUser)表控制层
 *
 * @author nanaouyang
 * @since 2020-04-11 22:40:47
 */
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserDao userDao;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 通过账号密码登录
     *
     * @param user 用户实体
     * @return 登录结果
     */
    @PostMapping("login")
    public Object login(@RequestBody User user) {
        User user1 = userDao.selectOne(new QueryWrapper<User>().eq("USERNAME", user.getUsername()));
        if (null != user1) {
            BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
            if(bCryptPasswordEncoder.matches(user.getPassword(),user1.getPassword())){
                String token = JwtTokenUtil.createToken(user1);
                redisUtil.set(user1.getId(), token, JwtTokenUtil.EXPIRATION);
                return ResultUtil.success(token);
            }else {
                return ResultUtil.error("密码错误");
            }
        } else {
            return ResultUtil.error("用户不存在");
        }
    }

    @PostMapping("register")
    public Object register(@RequestBody User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword().trim()));
        return ResultUtil.success(userDao.insert(user));
    }

    /**
     * 微信登录
     *
     * @param code
     * @return
     */
    @GetMapping("wxLogin")
    public Object wxLogin(String code) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("https://api.weixin.qq.com/sns/jscode2session?appid=wx1f2446ebdbed4405&secret=efe6ebfccacd94cb2c2a83f14873ecac&grant_type=authorization_code&js_code=" + code);
        try {
            HttpResponse response = httpClient.execute(get);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String res = EntityUtils.toString(response.getEntity());
                String openid = JSON.parseObject(res).get("openid").toString();
                if (0 == userDao.selectCount(new QueryWrapper<User>().eq("OPENID", openid))) {
                    User user = new User();
                    user.setOpenid(openid);
                    userDao.insert(user);
                }
                String uuid = UUID.randomUUID().toString();
                redisUtil.set(uuid, userDao.selectOne(new QueryWrapper<User>().select("ID").eq("OPENID", openid)).getId(), -1);
                return ResultUtil.success(uuid);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return ResultUtil.success(null);
    }


    /**
     * 验证token是否正确
     *
     * @param request
     * @return
     */
    @GetMapping("getUserState")
    public Object getUserState(HttpServletRequest request) {
        String tokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        String token = tokenHeader.substring(JwtTokenUtil.TOKEN_PREFIX.length());
        String uid = JwtTokenUtil.getUserIdFromToken(token);
        if (null != uid) {
            return ResultUtil.success(redisUtil.hasKey(uid));
        } else {
            return ResultUtil.success(false);
        }
    }


    /**
     * 获取用户信息
     *
     * @param request
     * @return
     */
    @GetMapping("getUserInfo")
    @PreAuthorize("hasRole('USER')")
    public Object getUserInfo(HttpServletRequest request) {
        String tokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        String token = tokenHeader.substring(JwtTokenUtil.TOKEN_PREFIX.length());
        String uid = JwtTokenUtil.getUserIdFromToken(token);
        if (null != uid) {
            return ResultUtil.success(userDao.selectById(uid));
        } else {
            return ResultUtil.success(false);
        }
    }
}