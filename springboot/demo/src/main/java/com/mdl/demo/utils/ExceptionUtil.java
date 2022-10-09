package com.mdl.demo.utils;

import org.apache.logging.log4j.util.Strings;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/10/9 19:00
 */
public class ExceptionUtil {

    private final static String NEW_LINE = "\n";
    private final static String TRACE_LINE = NEW_LINE + "    at ";

    public static String stackTrace(RuntimeException re){
        if(re == null){
            return Strings.EMPTY;
        }
        String cause = re.toString();
        StringBuilder sb = new StringBuilder(NEW_LINE + cause + NEW_LINE);
        StackTraceElement[] stackTrace = re.getStackTrace();
        for (StackTraceElement element : stackTrace){
            sb.append(TRACE_LINE).append(element.toString());
        }
        return sb.toString();
    }
}
