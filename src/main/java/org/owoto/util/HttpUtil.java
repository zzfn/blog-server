package org.owoto.util;

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
        if (null != token) {
            token = token.substring(JwtTokenUtil.TOKEN_PREFIX.length());
            return JwtTokenUtil.getUserIdFromToken(token);
        } else {
            return null;
        }
    }
}
