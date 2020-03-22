package com.yangyang.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created on 2019/11/20 16:24
 *
 * @Author Xiaoxu
 * @Version 1.0
 */
@Component
public class ValidatorImpl implements InitializingBean {
    /**
     * 使用Javax实现的接口
     */
    private Validator validator;
    /**
     * 实现校验方法，返回校验结果
     */
    public ValidationResult validate(Object bean){
        ValidationResult validationResult = new ValidationResult();
        Set<ConstraintViolation<Object>> validateSet = validator.validate(bean);
        if (validateSet.size() > 0){
            Map<String, String> map = new HashMap<>();
            validationResult.setHasError(true);
            validateSet.forEach(validate->{
                //这里应该是获取存在错误的字段名
                String errorMessage = validate.getMessage();
                String propertyName = validate.getPropertyPath().toString();
                map.put(propertyName,errorMessage);
            });
            validationResult.setErrorMsgMap(map);

        }
        return validationResult;
    }
    /**
     * 在spring初始化完成之后会回调此方法
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate validator通过工厂的初始化方式使其实例化，build一个实现了javax接口的校验器
      this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
