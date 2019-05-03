package com.swg.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.swg.exception.ParamException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * @Author swg.
 * @Date 2019/5/3 15:09
 * @CONTACT 317758022@qq.com
 * @DESC 参数校验工具类
 */
@Slf4j
public class BeanValidator {
    //校验工厂
    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    //传入一个参数或者0个参数
    public static <T> Map<String, String> validate(T t) {
        Validator validator = validatorFactory.getValidator();
        Set validateResult = validator.validate(t);
        if (validateResult.isEmpty()) {
            return Collections.emptyMap();
        } else {
            LinkedHashMap errors = Maps.newLinkedHashMap();
            Iterator iterator = validateResult.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation violation = (ConstraintViolation)iterator.next();
                //字段+错误信息
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return errors;
        }
    }

    //往往一个类中需要校验的参数可能是多个，那么就直接传入一个集合比较方便
    public static Map<String, String> validateList(Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        Iterator iterator = collection.iterator();
        Map errors;
        do {
            if (!iterator.hasNext()) {
                return Collections.emptyMap();
            }
            Object object = iterator.next();
            errors = validate(object);
        } while (errors.isEmpty());

        return errors;
    }


    //对0个参数或者多个参数分开处理，效率高一点，这样调用的时候也好用一点
    public static Map<String, String> validateObject(Object first, Object... objects) {
        if (objects != null && objects.length > 0) {
            return validateList(Lists.asList(first, objects));
        } else {
            return validate(first);
        }
    }

    //如果存在校验不通过的情况，则直接抛出自定义异常ParamException，所以调用这个方法的接口上也需要显示声明可能要抛出这个自定义异常
    public static void check(Object param) throws ParamException {
        Map<String, String> map = BeanValidator.validateObject(param);
        for(Map.Entry<String,String> res : map.entrySet()){
            log.info("{}->{}",res.getKey(),res.getKey());
        }
        if (MapUtils.isNotEmpty(map)) {
            throw new ParamException(map.toString());
        }
    }
}
