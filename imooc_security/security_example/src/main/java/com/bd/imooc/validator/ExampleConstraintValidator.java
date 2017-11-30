package com.bd.imooc.validator;

import com.bd.imooc.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 实现ConstraintValidator接口，Spring会声明ExampleConstraintValidator为1个Bean
 */
public class ExampleConstraintValidator implements ConstraintValidator<ExampleConstraint, Object> {
    @Autowired
    private ExampleService exampleService;

    @Override
    public void initialize(ExampleConstraint constraintAnnotation) {
        System.out.println("ExampleValidator init");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        exampleService.greeting("pyl");
        /*
            value是被@ExampleConstraint注解的成员变量
            此处验证value的合法性
         */
        System.out.println(value);

        //false表示验证没通过
        return false;
    }

}