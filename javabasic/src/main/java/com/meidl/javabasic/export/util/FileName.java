package com.meidl.javabasic.export.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author Meidanlong
 * @version 1.0
 * @date 2019/11/13 10:56
 */
public class FileName {

    public String getFileName(HttpServletRequest request, String name) throws UnsupportedEncodingException {
        String userAgent = request.getHeader("USER-AGENT");
        // 防止中文乱码
        return userAgent.contains("Mozilla") ? new String(name.getBytes(), "ISO8859-1") : name;
//        return userAgent.contains("Mozilla") ? new String(name.getBytes(), "UTF-8") : name;
    }
}
