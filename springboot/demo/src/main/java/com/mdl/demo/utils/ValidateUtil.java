package com.mdl.demo.utils;

import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

/**
 * 类名称：ValidatorUtil
 * ********************************
 * <p>
 * 类描述：校验工具类
 *
 * @author meidanlong
 * @date 上午8:49
 */
public class ValidateUtil {

    /**
     * 校验器
     */
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 参数校验
     * @param object
     * @param groups
     * @param <T>
     */
    public static <T> void validate(T object, Class... groups) {
        Set<ConstraintViolation<T>> validateResultSet = validator.validate(object, groups);

        // 如果校验结果不为空
        if (!CollectionUtils.isEmpty(validateResultSet)) {
            validateResultSet.stream().findFirst().ifPresent(vr -> {
                throw new ValidationException(vr.getMessage());
            });
        }
    }

}
