package com.zzf.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zzf.entity.JwtUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author cc
 */
@Slf4j
@Component
public class JwtTokenUtil {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    /**
     * 秘钥
     */
    public static String SECRET;

    @Value("${secret}")
    private void setSecret(String secret) {
        SECRET = secret;
    }

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
     *
     * @param claims
     * @return
     */
    public static String generateToken(Map<String, Object> claims) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create().withPayload(claims).withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION * 1000)).sign(algorithm);
    }

    /**
     * 创建refreshToken
     *
     * @param claims
     * @return
     */
    public static String generateRefreshToken(Map<String, Object> claims) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create().withPayload(claims).withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION * 1000)).sign(algorithm);
    }

    /**
     * 是否过期
     *
     * @param token
     * @return
     */
    public static boolean isTokenExpired(String token) {
        Map<String,Claim> claimMap=getClaimsFromToken(token);
        if (null != claimMap) {
            return claimMap.get("exp").asDate().before(new Date());
        } else {
            return false;
        }
    }

    public static Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        String username = getUserNameFromToken(token);
        return (Objects.equals(username, user.getUsername()) && !isTokenExpired(token));
    }

    /**
     * 获取载荷
     *
     * @param token
     * @return
     */
    private static Map<String, Claim> getClaimsFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (JWTVerificationException exception) {
            log.error("JWT格式验证失败:{}", token);
            return null;
        }
    }

    /**
     * 从token中获取登录用户名
     */
    public static String getUserNameFromToken(String token) {
        String username = null;
        try {
            Map<String, Claim> claims = getClaimsFromToken(token);
            if (null != claims) {
                username = claims.get("username").asString();
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
            Map<String,Claim> claims = getClaimsFromToken(token);
            if (null != claims) {
                role = claims.get("uid").asString();
            }
            return role;
        } catch (Exception e) {
            log.error("token 解析错误");
            return null;
        }
    }
}