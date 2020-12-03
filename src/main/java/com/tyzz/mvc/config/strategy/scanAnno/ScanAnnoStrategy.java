package com.tyzz.mvc.config.strategy.scanAnno;

import java.lang.annotation.Annotation;

/**
 * Created by IntelliJ IDEA.
 * User: zhangzhao<zhao.zhang@eventslack.com>
 * Date: 2020/11/26
 * Time: 8:48
 */
public interface ScanAnnoStrategy {
    <T> void execute(Object instance, Class c, Annotation annotation, T... arg);
}
