package com.api.jello.util;

import com.api.jello.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;

/**
 * @author cc
 */
@Slf4j
public class JwtTokenUtil {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    /**
     * 秘钥
     */
    private static final String SECRET = "jwtSECRET";

    /**
     * 过期时间是3600秒，既是1个小时
     */
    private static final long EXPIRATION = 3600L;


    /**
     * 创建token
     *
     * @param user 用户实体
     * @return token
     */
    public static String createToken(User user) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("role", user.getRoleId());
        map.put("username", user.getUsername());
        map.put("userid", user.getId());
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                .compact();
    }

    /**
     * 是否过期
     * @param token
     * @return
     */
    public static boolean isTokenExpired(String token) {
        if (null != getClaimsFromToken(token)) {
            return getClaimsFromToken(token).getExpiration().before(new Date());
        } else {
            return false;
        }
    }

    /**
     * 获取载荷
     * @param token
     * @return
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.info("JWT格式验证失败:{}", token);
        }
        return claims;
    }

    /**
     * 从token中获取登录用户名
     */
    public static String getUserNameFromToken(String token) {
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            if(null!=claims){
                username=(String)claims.get("username");
            }
            return username;
        } catch (Exception e) {
            log.error("token 解析错误");
            return null;
        }
    }
    /**
     * 从token中获取登录角色
     */
    public static String getUserRoleFromToken(String token) {
        String role = null;
        try {
            Claims claims = getClaimsFromToken(token);
            if(null!=claims){
                role=(String)claims.get("role");
            }
            return role;
        } catch (Exception e) {
            log.error("token 解析错误");
            return null;
        }
    }
}