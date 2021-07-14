package com.zzf.config;

import com.zzf.component.IgnoreAuth;
import com.zzf.component.ResultAccessDeniedHandler;
import com.zzf.component.ResultAuthenticationEntryPoint;
import com.zzf.filter.JwtAuthorizationFilter;
import com.zzf.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.protocol.HttpRequestHandlerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;
import java.util.Objects;


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
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    @Autowired
    JwtAuthorizationFilter jwtAuthorizationFilter;
    @Autowired
    ResultAccessDeniedHandler resultAccessDeniedHandler;
    @Autowired
    ResultAuthenticationEntryPoint resultAuthenticationEntryPoint;
    @Autowired
    private RequestMappingHandlerMapping handlerMapping;
    @Override
    public void configure(WebSecurity web) throws Exception {
        WebSecurity and = web.ignoring().and();
        Map<RequestMappingInfo, HandlerMethod> handlerMethods =
                handlerMapping.getHandlerMethods();
        handlerMethods.forEach((info, method) -> {
            if (!Objects.isNull(method.getMethodAnnotation(IgnoreAuth.class))) {
                // 根据请求类型做不同的处理
                info.getMethodsCondition().getMethods().forEach(requestMethod -> {
                    switch (requestMethod) {
                        case GET:
                            // getPatternsCondition得到请求url数组，遍历处理
                            info.getPatternsCondition().getPatterns().forEach(pattern -> {
                                // 放行
                                and.ignoring().antMatchers(HttpMethod.GET, pattern);
                            });
                            break;
                        case POST:
                            info.getPatternsCondition().getPatterns().forEach(pattern -> {
                                and.ignoring().antMatchers(HttpMethod.POST, pattern);
                            });
                            break;
                        case DELETE:
                            info.getPatternsCondition().getPatterns().forEach(pattern -> {
                                and.ignoring().antMatchers(HttpMethod.DELETE, pattern);
                            });
                            break;
                        case PUT:
                            info.getPatternsCondition().getPatterns().forEach(pattern -> {
                                and.ignoring().antMatchers(HttpMethod.PUT, pattern);
                            });
                            break;
                        default:
                            break;
                    }
                });
            }
        });
        web.ignoring().antMatchers(HttpMethod.GET,
                "/v3/api-docs",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui/**",
                "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll();
        httpSecurity.cors();
        httpSecurity
                .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/**/non/**"
                )
                .permitAll()
                .antMatchers(HttpMethod.POST,
                        "/**/non/**"
                )
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(resultAccessDeniedHandler)
                .authenticationEntryPoint(resultAuthenticationEntryPoint);
        httpSecurity.headers().cacheControl();
        httpSecurity.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
