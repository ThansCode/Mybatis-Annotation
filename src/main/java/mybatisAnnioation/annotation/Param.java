package mybatisAnnioation.annotation;

import java.lang.annotation.*;

/**参数注解信息
 * @program: XMind
 * @description
 * @author: yihang
 * @create: 2020-07-31 21:47
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface Param {
    String value() default "";
}
