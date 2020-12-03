package com.tyzz.mvc.config.chain.request;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: zhangzhao<zhao.zhang@eventslack.com>
 * Date: 2020/12/2
 * Time: 17:04
 */
public interface RequestOperate {
    void execute(Annotation annotation, Object instance, Method method, String[] params, Class<?>[] classes);

    void setNextOperate(RequestOperate operate);
}
