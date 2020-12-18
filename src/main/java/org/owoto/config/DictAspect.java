package org.owoto.config;

import org.owoto.entity.BaseEntity;
import org.owoto.service.SysDictService;
import org.owoto.util.RedisUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author cc
 * @date 2020/3/28 22:23
 */
@Component
@Aspect
@Slf4j
public class DictAspect {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    SysDictService sysDictService;

    @Pointcut("execution( * org.owoto.controller.*.*(..))")
    public void dictExecution() {
    }

    @Around("dictExecution()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        Map<String, Object> resultMap = (HashMap) result;
        Object resultObj = null;
        if (null == resultMap) {
            return null;
        }
        if (null == resultMap.get("data")) {
            return result;
        }
        resultObj = resultMap.get("data");
        if (resultObj instanceof BaseEntity) {
            translation(resultObj);
        } else if (resultObj instanceof List) {
            List list = (List) resultObj;
            Optional.of((List) list).ifPresent(objects -> objects.forEach(this::translation));
        } else if (resultObj instanceof IPage) {
            IPage ipage = (IPage) resultObj;
            List list = (List) ipage.getRecords();
            Optional.of((List) list).ifPresent(objects -> objects.forEach(this::translation));
        } else {
            translation(resultObj);
        }
        return result;
    }

    private void translation(Object content) {
        try {
            Field[] fields = FieldUtils.getAllFields(content.getClass());
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Dict.class)) {
                    field.setAccessible(true);
                    Dict dict = field.getAnnotation(Dict.class);
                    String name = sysDictService.translate(dict.codeType(), (String) field.get(content));
                    Field targetField = FieldUtils.getField(content.getClass(), dict.target(), true);
                    targetField.set(content, name);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
