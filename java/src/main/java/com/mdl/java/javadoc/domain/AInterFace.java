package com.mdl.java.javadoc.domain;

import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/1/10 09:51
 */
public interface AInterFace {

    /**
     * 做一些事情
     *
     * @see
     *  {@link <a href=""/a>}
     * @deprecated
     * @author meidanlong
     * @date 2023/1/10
     * @version 1.0.0
     * @param clazz fieldDesc @required
     * @param method fieldDesc @required
     * @param aa fieldDesc @required
     * @param bb fieldDesc @required
     * @param list fieldDesc @required
     * @return List<MethodDetailDTO>
     */
    List<MethodDetailDTO> doSth(ClassDetailDTO clazz, MethodDetailDTO method, int aa, String bb, List<String> list);
}
