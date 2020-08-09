package com.api.jello.config;

import com.api.jello.filter.JwtAuthorizationFilter;
import com.api.jello.service.impl.UserServiceImpl;
import com.api.jello.vo.AccessDeniedVO;
import com.api.jello.vo.NoLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 对SpringSecurity的配置的扩展，支持自定义白名单资源路径和查询用户逻辑
 *
 * @author macro
 * @date 2019/11/5
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    JwtAuthorizationFilter jwtAuthorizationFilter;
    @Autowired
    AccessDeniedVO accessDeniedVO;
    @Autowired
    NoLoginVO noLoginVO;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll();
        httpSecurity.cors();
        httpSecurity.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/swagger-resources/**",
                        "/swagger-ui.html#/**",
                        "/swagger-ui.html",
                        "/v2/api-docs/**"
                )
                .permitAll()
                .antMatchers(HttpMethod.POST,
                        "/user/login")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedVO)
                .authenticationEntryPoint(noLoginVO);
        httpSecurity.headers().cacheControl();
        httpSecurity.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
