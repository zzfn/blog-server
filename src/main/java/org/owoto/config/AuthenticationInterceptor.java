//package org.owoto.config;
//import PassToken;
//import ResultEnum;
//import BusinessException;
//import RedisUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.lang.reflect.Method;
//
///**
// * @author cach1e
// */
//@Slf4j
//public class AuthenticationInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) {
//        String uuid = httpServletRequest.getHeader("Authorization");
//        if (!(object instanceof HandlerMethod)) {
//            return true;
//        }
//        HandlerMethod handlerMethod = (HandlerMethod) object;
//        Method method = handlerMethod.getMethod();
//
//        if (method.isAnnotationPresent(PassToken.class)) {
//            PassToken passToken = method.getAnnotation(PassToken.class);
//            if (passToken.required()) {
//                return true;
//            }
//        }
//        if (uuid == null) {
//            throw new BusinessException(ResultEnum.ACCESS_DENIED);
//        }
////        String userId = (String) RedisUtil.get(uuid);
////        if (userId == null) {
////            throw new BusinessException(ResultEnum.ACCESS_DENIED);
////        }
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//
//    }
//}
