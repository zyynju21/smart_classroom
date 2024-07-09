package com.yiyue.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Author: XiaoQi
 * @Date: 2020/11/20 13:23
 */
public class ExceptionUtil {

    /**
     * 获取堆栈信息
     *
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }

}
