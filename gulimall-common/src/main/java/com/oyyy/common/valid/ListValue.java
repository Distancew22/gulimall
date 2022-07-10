package com.oyyy.common.valid;

import com.alibaba.nacos.api.grpc.auto.Payload;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
//关联自定义的校验器
@Constraint(validatedBy = { ListValueConstraintValidator.class })
//注解可以放在哪里
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface ListValue {
    //校验注解发生异常的时候，提示信息该配置文件中获取
    String message() default "{com.oyyy.common.valid.ListValue.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    int[] vals() default { };
}
