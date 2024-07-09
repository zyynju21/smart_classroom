package com.yiyue.common.util;


import com.yiyue.common.exception.CustomException;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @Author: XiaoQi
 * @Date: 2020/9/16 14:10
 */
public class Assert {

    /**
     * 判断字符串是否为空 为空则抛出异常信息
     *
     * @param str
     * @param msg
     */
    public static void isBlank(String str, String msg) {
        if (StringUtils.isBlank(str)) {
            throw new CustomException(msg);
        }
    }


    /**
     *  判断first value 数据大于 second value, 如果大于 first value 小于 second value ，抛异常
     * @param firstValue
     * @param secondValue
     * @param msg
     */
    public static void compareValue(BigDecimal firstValue, BigDecimal secondValue, String msg) {
        if (firstValue.compareTo(secondValue) == -1) {
            throw new CustomException(msg);
        }
    }

    /**
     * 空对象 抛出异常消息
     *
     * @param object
     * @param msg
     */
    public static void isNull(Object object, String msg) {
        if (Objects.isNull(object)) {
            throw new CustomException(msg);
        }
    }

    /**
     * 非空对象 抛出异常消息
     *
     * @param object
     * @param msg
     */
    public static void isNotNull(Object object, String msg) {
        if (Objects.nonNull(object)) {
            throw new CustomException(msg);
        }
    }

    /**
     * 判断字符串是否已经超过了指定的长度
     * 超过了则抛出异常信息
     *
     * @param str
     * @param length
     * @param msg
     */
    public static void isMaxLength(String str, int length, String msg) {
        if (StringUtils.isEmpty(str)) {
            throw new CustomException(str + "not null!");
        }
        if (str.length() > length) {
            throw new CustomException(msg);
        }
    }

    /**
     * 两个值不相等 抛出异常消息
     *
     * @param objectOne
     * @param objectTwo
     * @param msg
     */
    public static void notEquals(Object objectOne, Object objectTwo, String msg) {
        if (!Objects.equals(objectOne, objectTwo)) {
            throw new CustomException(msg);
        }
    }

    /**
     * 两个值相等 抛出异常消息
     *
     * @param objectOne
     * @param objectTwo
     * @param msg
     */
    public static void isEquals(Object objectOne, Object objectTwo, String msg) {
        if (Objects.equals(objectOne, objectTwo)) {
            throw new CustomException(msg);
        }
    }

    /**
     * flag假，抛出异常消息
     *
     * @param expression
     * @param msg
     */
    public static void isTrue(boolean expression, String msg) {
        if (!expression) {
            throw new CustomException(msg);
        }
    }
}
