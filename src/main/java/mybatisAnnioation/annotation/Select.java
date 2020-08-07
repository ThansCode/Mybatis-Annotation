package mybatisAnnioation.annotation;

import java.lang.annotation.*;

/**查询信息
 * @program: XMind
 * @description
 * @author: yihang
 * @create: 2020-07-31 21:48
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Select {
    String value() default "";
}
