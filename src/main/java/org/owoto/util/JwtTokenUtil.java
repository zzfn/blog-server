package org.owoto.util;

import org.owoto.entity.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

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
    public static final long EXPIRATION = 3600L;
    /**
     * 过期时间是21600秒，既是6个小时
     */
    public static final long REFRESH_EXPIRATION = 21600L;

    /**
     * 创建token
     * @param claims
     * @return
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                .compact();
    }

    /**
     * 创建refreshToken
     * @param claims
     * @return
     */
    public static String generateRefreshToken(Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION * 1000))
                .compact();
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put("created", LocalDate.now());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
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

    public static Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        String username = getUserNameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
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
     * 从token中获取登录用户id
     */
    public static String getUserIdFromToken(String token) {
        String role = null;
        try {
            Claims claims = getClaimsFromToken(token);
            if(null!=claims){
                role=(String)claims.get("uid");
            }
            return role;
        } catch (Exception e) {
            log.error("token 解析错误");
            return null;
        }
    }
}