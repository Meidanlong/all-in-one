package com.mdl.common.validation.validator;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/9/5 11:23
 */


import com.mdl.common.validation.annotation.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义手机号约束注解关联验证器
 */
public class PhoneValidator
        implements ConstraintValidator<Phone, String> {

    /**
     * 自定义校验逻辑方法
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value,
                           ConstraintValidatorContext context) {

        // 手机号验证规则：
        //1、以1开头
        //2、中间三位规则如下
        //3、如果第二位为3，看第三位是否为4，如果是4，则第四位为0到8之间的数字，否则第三位为0到3和5到9之间的数字，第四位为0-9之间的数字；
        //4、如果第二位为4，则第三位为5、7、9数字，第四位为0-9之间的数字；
        //5、如果第二位为5，则第三位为0到3和5到9之间的数字，第四位为0-9之间的数字；
        //6、如果第二位为6，则第三位为6，第四位为0-9之间的数字；
        //7、如果第二位为7，则第三位为3，5，6，7， 8的数字，第四位为0-9之间的数字；
        //8、如果第二位为8，则第三位为0-9之间的数字，第四位为0-9之间的数字；
        //9、如果第二位为9，则第三位为1，3，8，9的数字，第四位为0-9之间的数字；
        //10、后面七位为0到9之间的数字；
        String check = "/^1((34[0-8])|(8\\d{2})|(([35][0-35-9]|4[579]|66|7[35678]|9[1389])\\d{1}))\\d{7}$/";
        Pattern regex = Pattern.compile(check);

        // 空值处理
        String phone = Optional.ofNullable(value).orElse("");
        Matcher matcher = regex.matcher(phone);

        return matcher.matches();
    }
}
