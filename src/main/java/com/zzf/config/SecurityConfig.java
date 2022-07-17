package com.zzf.config;

import com.alibaba.fastjson.JSON;
import com.zzf.annotation.IgnoreAuth;
import com.zzf.filter.JwtAuthorizationFilter;
import com.zzf.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


/**
 * 对SpringSecurity的配置的扩展，支持自定义白名单资源路径和查询用户逻辑
 *
 * @author macro
 * @date 2019/11/5
 */
@Configuration
@EnableWebSecurity
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    @Resource
    JwtAuthorizationFilter jwtAuthorizationFilter;
    @Resource
    @Qualifier("requestMappingHandlerMapping")
    private RequestMappingHandlerMapping handlerMapping;
    private String[] getAnonymousUrls() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        Set<String> allAnonymousAccess = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethods.entrySet()) {
            HandlerMethod value = infoEntry.getValue();
            IgnoreAuth methodAnnotation = value.getMethodAnnotation(IgnoreAuth.class);
            if (!Objects.isNull(methodAnnotation)) {
                allAnonymousAccess.addAll(infoEntry.getKey().getPatternValues());
            }
        }
        return allAnonymousAccess.toArray(new String[0]);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/",
                "/swagger-ui.html",
                "/swagger-ui/",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/swagger-resources/**",
                "/v3/api-docs/**",
                "/v3/api-docs",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/article/search",
                "/swagger-ui/**",
                "/webjars/**").permitAll();
        httpSecurity.authorizeRequests().antMatchers("/websocket").permitAll();
        httpSecurity.authorizeRequests().antMatchers(getAnonymousUrls()).permitAll();
        httpSecurity.cors();
        httpSecurity
                .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
//                .accessDeniedHandler((req, res, auth) -> {
//                    res.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                    res.setCharacterEncoding("UTF-8");
//                    res.setStatus(200);
//                    res.getWriter().println(JSON.toJSONString(ResultUtil.error(4003, "权限不足，请联系管理员")));
//                    res.getWriter().flush();
//                })
                .authenticationEntryPoint((req, res, auth) -> {
                    res.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    res.setCharacterEncoding("UTF-8");
                    res.setStatus(200);
                    res.getWriter().println(JSON.toJSONString(ResultUtil.error(4001, "请认证后处理")));
                    res.getWriter().flush();
                });
        httpSecurity.headers().cacheControl();
        httpSecurity.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
