//package com.api.jello.filter;
//
///**
// * @author longzhonghua
// * @data 3/5/2019 11:59 PM
// */
//
//import com.api.jello.util.JwtTokenUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//@Slf4j
//public class JwtAuthorizationFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain chain) throws IOException, ServletException {
//
//        String tokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
//        // 如果请求头中没有Authorization信息则直接放行了
//        if (tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
//            chain.doFilter(request, response);
//            return;
//        }
//        String username = JwtTokenUtil.getUserNameFromToken(tokenHeader);
//        log.info("用户名：{}",username);
////        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(null, null,null);
////        SecurityContextHolder.getContext().setAuthentication(authentication);
//        chain.doFilter(request, response);
//    }
//}