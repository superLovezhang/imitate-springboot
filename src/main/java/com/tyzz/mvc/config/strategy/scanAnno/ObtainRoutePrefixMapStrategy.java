package com.tyzz.mvc.config.strategy.scanAnno;

import com.tyzz.mvc.anno.RequestMapping;
import com.tyzz.mvc.config.core.summer.SummerApplicationContext;

import java.lang.annotation.Annotation;

/**
 * Created by IntelliJ IDEA.
 * User: zhangzhao<zhao.zhang@eventslack.com>
 * Date: 2020/11/27
 * Time: 9:51
 */
public class ObtainRoutePrefixMapStrategy implements ScanAnnoStrategy {
    @Override
    public <T> void execute(Object instance, Class myClass, Annotation annotation, T... arg) {
        // 如果是一个控制器组件 获取value做为所有方法路由映射的前缀
        if (annotation.annotationType() == RequestMapping.class) {
            RequestMapping requestMapping = (RequestMapping) annotation;
            SummerApplicationContext.PREFIX_PATH_MAP.put(instance,  requestMapping.value());
        }
    }
}
