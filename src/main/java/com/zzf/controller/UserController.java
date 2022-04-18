package com.zzf.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzf.annotation.IgnoreAuth;
import com.zzf.entity.User;
import com.zzf.mapper.UserDao;
import com.zzf.util.JwtTokenUtil;
import com.zzf.util.RedisUtil;
import com.zzf.util.ResultUtil;
import com.zzf.vo.LoginVO;
import com.zzf.vo.TokenVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * USER(TUser)表控制层
 *
 * @author nanaouyang
 * @since 2020-04-11 22:40:47
 */
@RestController
@RequestMapping("user")
@Slf4j
@Api(tags = "用户管理")
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
    @IgnoreAuth
    public Object login(@RequestBody LoginVO loginVO) {
        User user = userDao.selectOne(new QueryWrapper<User>().eq("USERNAME", loginVO.getUsername()));
        if (null != user) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if (bCryptPasswordEncoder.matches(loginVO.getPassword(), user.getPassword())) {
                Map<String, Object> map = new HashMap<>();
                map.put("username", user.getUsername());
                map.put("uid", user.getId());
                String token = JwtTokenUtil.generateToken(map);
                redisUtil.set(user.getId(), token, JwtTokenUtil.EXPIRATION);
                TokenVO tokenVO = new TokenVO();
                tokenVO.setToken(token);
                tokenVO.setRefreshToken(JwtTokenUtil.generateRefreshToken(map));
                tokenVO.setExpired(JwtTokenUtil.EXPIRATION);
                log.info("用户{}登录成功", loginVO.getUsername());
                return ResultUtil.success(tokenVO);
            } else {
                return ResultUtil.error("密码错误");
            }
        } else {
            return ResultUtil.error("用户不存在");
        }
    }

    /**
     * 注册
     */
    @PostMapping("register")
    @IgnoreAuth
    public Object register(@RequestBody User user) {
        if (null == user.getNickName()) {
            user.setNickName(user.getUsername());
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword().trim()));
        return ResultUtil.success(userDao.insert(user));
    }

    /**
     * 注销
     */
    @PostMapping("logout")
    public Object logout() {
        return ResultUtil.success(null);
    }

    /**
     * 验证token是否正确
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
     */
    @GetMapping("getUserInfo")
    public Object getUserInfo(@RequestHeader String authorization) {
        String token = authorization.substring(JwtTokenUtil.TOKEN_PREFIX.length());
        String uid = JwtTokenUtil.getUserIdFromToken(token);
        if (null != uid) {
            return ResultUtil.success(userDao.getUser(uid));
        } else {
            return ResultUtil.success(false);
        }
    }
}