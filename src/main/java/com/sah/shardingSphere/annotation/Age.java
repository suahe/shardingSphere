package com.sah.shardingSphere.annotation;

import org.hibernate.validator.constraints.Range;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.lang.annotation.*;

/**
 * @author suahe
 * @date 2023/3/31
 * @ApiNote
 */
@Constraint(
        validatedBy = {}
)
@SupportedValidationTarget({ValidationTarget.ANNOTATED_ELEMENT})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD,
        ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE})
@NotNull
@Range(min = 1, max = 50)
@Documented
@ReportAsSingleViolation
public @interface Age {
    // message 必须有
    String message() default "年龄必须填写，且范围为 1-50 ";

    // 可选
    Class<?>[] groups() default {};

    // 可选
    Class<? extends Payload>[] payload() default {};
}
