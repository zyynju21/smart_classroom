package com.yiyue.common.aspact;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface SystemPlatLog {
    String value() default "";

    /* 执行 controller 的功能描述 */
    String content() default "";
}
