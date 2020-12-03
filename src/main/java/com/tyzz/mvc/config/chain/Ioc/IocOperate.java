package com.tyzz.mvc.config.chain.Ioc;

import com.tyzz.mvc.config.strategy.scanAnno.AssemblyIocContainerStrategy;
import com.tyzz.mvc.config.strategy.scanAnno.ScanAnnoStrategyContext;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Optional;

import static com.tyzz.mvc.config.core.summer.SummerApplicationContext.CURRENT_INSTANCE;
import static com.tyzz.mvc.config.core.summer.SummerApplicationContext.PRESENT;

/**
 * Created by IntelliJ IDEA.
 * User: zhangzhao<zhao.zhang@eventslack.com>
 * Date: 2020/11/24
 * Time: 18:01
 */
public class IocOperate extends Operate {
    /**
     * 将符合的类进行初始化并装配进IOC容器内
     *
     * @param myClass
     * @param instance
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public void execute(Class<?> myClass, Object instance) throws IllegalAccessException, InstantiationException {
        // 扫描当前类上的所有注解 对带有组件注解的加入到IOC容器内
        Annotation[] annotations = myClass.getAnnotations();

        ScanAnnoStrategyContext context = new ScanAnnoStrategyContext();
        context.setStrategy(new AssemblyIocContainerStrategy());
        context.run(instance, myClass, annotations);

        // 如果有下一个处理器并且当前类是组件就交给下一个处理
        if (operate != null && CURRENT_INSTANCE.get() != null) operate.execute(myClass, CURRENT_INSTANCE.get());
    }

}
