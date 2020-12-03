package com.tyzz.mvc.config.strategy.scanAnno;

import com.tyzz.mvc.anno.Component;
import com.tyzz.mvc.anno.Controller;
import com.tyzz.mvc.anno.Repository;
import com.tyzz.mvc.anno.Service;
import com.tyzz.mvc.config.core.summer.SummerApplicationContext;
import com.tyzz.mvc.exception.SummerRuntimeException;

import java.lang.annotation.Annotation;

import static com.tyzz.mvc.config.core.summer.SummerApplicationContext.CURRENT_INSTANCE;
import static com.tyzz.mvc.config.core.summer.SummerApplicationContext.PRESENT;

/**
 * Created by IntelliJ IDEA.
 * User: zhangzhao<zhao.zhang@eventslack.com>
 * Date: 2020/11/26
 * Time: 10:20
 *
 * 组装IOC容器策略
 */
public class AssemblyIocContainerStrategy implements ScanAnnoStrategy {
    @Override
    public <T> void execute(Object instance, Class myClass, Annotation annotation, T... arg) {
        if (judgmentIsComponent(annotation.annotationType())) {
            if (instance == PRESENT) {
                try {
                    instance = myClass.newInstance();
                    CURRENT_INSTANCE.set(instance);
                    // 默认将类名称首字母小写作为bean名称
                    char[] nameChars = myClass.getSimpleName().toCharArray();
                    nameChars[0] += 32;
                    if (SummerApplicationContext.IOC_CONTAINER.put(new String(nameChars), instance) != null) {
                        // 检查配置文件是否允许相同类型注入IOC
                        // 没设置或者不允许直接抛出异常
                        throw new SummerRuntimeException("has same type bean been injected, but config not set allow this \n" +
                                "if you want to allow same type injection, please set [allow.overrideBean=true] in your configuration");
                    }
                    System.out.println("已将" + instance + "添加进IOC容器");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断类是否符合组件要求
     * @param type 注解类型
     * @return
     */
    private boolean judgmentIsComponent(Class type) {
        return (type == Component.class) || (type == Repository.class) || (type == Service.class) || (type == Controller.class);
    }
}
