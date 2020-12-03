package com.tyzz.mvc.config.chain.Ioc;

import com.tyzz.mvc.anno.GetMapping;
import com.tyzz.mvc.anno.PostMapping;
import com.tyzz.mvc.anno.RequestMapping;
import com.tyzz.mvc.config.core.summer.SummerApplicationContext;
import com.tyzz.mvc.config.strategy.scanAnno.AssemblyRouteMapStrategy;
import com.tyzz.mvc.config.strategy.scanAnno.ObtainRoutePrefixMapStrategy;
import com.tyzz.mvc.config.strategy.scanAnno.ScanAnnoStrategyContext;
import com.tyzz.mvc.dispatcher.Dispatcher;
import com.tyzz.mvc.dispatcher.GetDispatcher;
import com.tyzz.mvc.dispatcher.PostDispatcher;
import com.tyzz.mvc.dispatcher.UniversalDispatcher;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import static com.tyzz.mvc.config.core.summer.SummerApplicationContext.DISPATCH_MAP;
import static com.tyzz.mvc.config.core.summer.SummerApplicationContext.PREFIX_PATH_MAP;


/**
 * Created by IntelliJ IDEA.
 * User: zhangzhao<zhao.zhang@eventslack.com>
 * Date: 2020/11/25
 * Time: 17:30
 */
public class RouteMapOperate extends Operate {

    @Override
    public void execute(Class<?> myClass, Object instance) throws InstantiationException, IllegalAccessException {
        // 获取路由前缀
        Annotation[] annotations = myClass.getAnnotations();
        ScanAnnoStrategyContext context = new ScanAnnoStrategyContext();
        context.setStrategy(new ObtainRoutePrefixMapStrategy());
        context.run(instance, myClass, annotations);

        // 遍历所有方法 获取到方法的路由映射
        Method[] methods = myClass.getDeclaredMethods();
        context.setStrategy(new AssemblyRouteMapStrategy());
        for (Method method : methods) {
            scanMethodAssemblyDispatcher(instance, myClass, method, context);
        }

        // 如果有下一个处理器并且当前类是组件就交给下一个处理
        if (operate != null) operate.execute(myClass, instance);
    }

    /**
     * 扫描方法上面的注解 装配一个可执行的Dispatcher
     * @param instance
     * @param myClass
     * @param method
     * @param context
     */
    private void scanMethodAssemblyDispatcher(Object instance, Class<?> myClass, Method method, ScanAnnoStrategyContext context) {
        // 获取该方法上所有注解
        Annotation[] annotations = method.getDeclaredAnnotations();
        // 找到@RequestMapping注解 获取到method[]和value
        context.run(instance, myClass, annotations, method);
    }
}
