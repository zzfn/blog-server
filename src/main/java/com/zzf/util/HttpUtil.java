package com.zzf.util;

import com.alibaba.druid.util.DruidWebUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author zzfn
 * @date 2021-06-09 11:44 下午
 */
public class HttpUtil {
    public static String getRequestHeader(String name) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
        return request.getHeader(name);
    }

    public static String getUserId() {
        String token = getRequestHeader("authorization");
        if (null != token&&token.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            token = token.substring(JwtTokenUtil.TOKEN_PREFIX.length());
            return JwtTokenUtil.getUserIdFromToken(token);
        } else {
            return null;
        }
    }
    public static String getSystem() {
        return getRequestHeader("System");
    }
    public static String getIp() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
        return DruidWebUtils.getRemoteAddr(request);
    }
}
