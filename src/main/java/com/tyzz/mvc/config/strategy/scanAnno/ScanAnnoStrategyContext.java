package com.tyzz.mvc.config.strategy.scanAnno;

import java.lang.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: zhangzhao<zhao.zhang@eventslack.com>
 * Date: 2020/11/26
 * Time: 8:49
 *
 * 扫描方法上所有注解 进行一些操作
 */
public class ScanAnnoStrategyContext {

    private ScanAnnoStrategy strategy;

    public void setStrategy(ScanAnnoStrategy scanAnnoStrategy) {
        strategy = scanAnnoStrategy;
    }

    /**
     * @param instance 当前方法的实体对象
     * @param c 当前方法的类
     * @param annotations 需要扫描的注解
     */
    public <T> void run(Object instance, Class c, Annotation[] annotations, T... arg) {
        for (Annotation annotation : annotations) {
            // 如果是基本注解 不进行操作
            if (ignoreAnnotation(annotation)) continue;
            // 先递归注解到最里一层
            run(instance, c, annotation.annotationType().getAnnotations(), arg);
            // 根据传入的策略执行对应操作...
            strategy.execute(instance, c, annotation, arg);
        }
    }

    // 判断当前注解是否是JDK自带注解
    private boolean ignoreAnnotation(Annotation annotation) {
        return annotation.annotationType() == Retention.class ||
                annotation.annotationType() == Target.class ||
                annotation.annotationType() == Deprecated.class ||
                annotation.annotationType() == SuppressWarnings.class ||
                annotation.annotationType() == Override.class ||
                annotation.annotationType() == Documented.class ||
                annotation.annotationType() == Documented.class ||
                annotation.annotationType() == Inherited.class;
    }
}
