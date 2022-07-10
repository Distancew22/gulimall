package com.oyyy.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

public class ListValueConstraintValidator implements ConstraintValidator<ListValue,Integer> {
    private Set<Integer> set = new HashSet<>();
    //初始化方法
    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] vals = constraintAnnotation.vals();
        for (int val : vals) {
            set.add(val);
        }
    }

    //判断是否校验成功
    //value为需要校验的值
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        //判断set集合中是否包含这个值，如果不包含就报错
        return set.contains(value);
    }
}
