package mybatisAnnioation.annotation;

import java.lang.annotation.*;

/**
 * @program: XMind
 * @description
 * @author: yihang
 * @create: 2020-07-31 21:46
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Insert {

    // 插入数据注解
    String value() default "";
}
