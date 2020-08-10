package com.api.jello.controller;

import com.alibaba.fastjson.JSON;
import com.api.jello.dao.UserDao;
import com.api.jello.entity.User;
import com.api.jello.util.JwtTokenUtil;
import com.api.jello.util.RedisUtil;
import com.api.jello.util.ResultUtil;
import com.api.jello.vo.LoginVO;
import com.api.jello.vo.TokenVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.io.IOException;
import java.util.*;

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
     * @param loginVO 用户实体
     * @return 登录结果
     */
    @PostMapping("login")
    public Object login(@RequestBody LoginVO loginVO) {
        User user = userDao.selectOne(new QueryWrapper<User>().eq("USERNAME", loginVO.getUsername()));
        if (null != user) {
            BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
            if(bCryptPasswordEncoder.matches(loginVO.getPassword(),user.getPassword())){
                Map<String,Object> map=new HashMap<>();
                map.put("username",user.getUsername());
                map.put("uid",user.getId());
                String token = JwtTokenUtil.generateToken(map);
                redisUtil.set(user.getId(), token, JwtTokenUtil.EXPIRATION);
                TokenVO tokenVO=new TokenVO();
                tokenVO.setToken(token);
                tokenVO.setRefreshToken(JwtTokenUtil.generateRefreshToken(map));
                tokenVO.setExpired(JwtTokenUtil.EXPIRATION);
                return ResultUtil.success(tokenVO);
            }else {
                return ResultUtil.error("密码错误");
            }
        } else {
            return ResultUtil.error("用户不存在");
        }
    }

    /**
     * 注册
     * @param user
     * @return
     */
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
     * @param authorization
     * @return
     */
    @GetMapping("getUserState")
    public Object getUserState(@RequestHeader String authorization) {
        String token = authorization.substring(JwtTokenUtil.TOKEN_PREFIX.length());
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
     * @param authorization
     * @return
     */
    @GetMapping("getUserInfo")
    public Object getUserInfo(@RequestHeader String authorization) {
        String token = authorization.substring(JwtTokenUtil.TOKEN_PREFIX.length());
        String uid = JwtTokenUtil.getUserIdFromToken(token);
        if (null != uid) {
            return ResultUtil.success(userDao.selectById(uid));
        } else {
            return ResultUtil.success(false);
        }
    }
}