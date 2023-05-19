package com.sah.shardingSphere.excel;

import com.sah.shardingSphere.excel.dto.DemoData;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author suahe
 * @date 2023/5/18 12:08
 */
public class ValidatorUtil {

    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public static <T> String validate(T obj) {
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        StringBuilder stringBuilder = new StringBuilder();
        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<T> violation : constraintViolations) {
                stringBuilder.append(violation.getMessage()).append(";");
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        DemoData demoData = new DemoData();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<DemoData>> violations = validator.validate(demoData);
        System.out.println(violations);
    }
}
