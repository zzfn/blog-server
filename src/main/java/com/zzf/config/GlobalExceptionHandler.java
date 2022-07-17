package com.zzf.config;

import com.zzf.exception.BusinessException;
import com.zzf.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author cc
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object handle(Exception e) {
        log.error("{}",e);
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            return ResultUtil.error(businessException.getCode(), businessException.getMessage());
        }
        return ResultUtil.error(9999, e.getMessage());
    }
    @ResponseBody
    @ExceptionHandler(value = AccessDeniedException.class)
    public Object handleAccessRE(AccessDeniedException e) {
        log.error("{}",e);
        return ResultUtil.error(4003,"权限不足，请联系管理员");
    }
    @ResponseBody
    @ExceptionHandler(value = AuthenticationCredentialsNotFoundException.class)
    public Object handleAccess(AccessDeniedException e) {
        return ResultUtil.error(4001,"请认证后处理");
    }
}
