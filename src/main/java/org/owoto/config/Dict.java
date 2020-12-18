package org.owoto.config;

import java.lang.annotation.*;

/**
 * @author cc
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {

    public String target();

    public String codeType() default "";

}
