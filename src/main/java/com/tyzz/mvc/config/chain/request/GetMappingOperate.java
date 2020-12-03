package com.tyzz.mvc.config.chain.request;

import com.tyzz.mvc.anno.GetMapping;
import com.tyzz.mvc.dispatcher.Dispatcher;
import com.tyzz.mvc.dispatcher.GetDispatcher;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import static com.tyzz.mvc.config.core.summer.SummerApplicationContext.DISPATCH_MAP;
import static com.tyzz.mvc.config.core.summer.SummerApplicationContext.PREFIX_PATH_MAP;

/**
 * Created by IntelliJ IDEA.
 * User: zhangzhao<zhao.zhang@eventslack.com>
 * Date: 2020/12/2
 * Time: 17:05
 */
public class GetMappingOperate implements RequestOperate {
    private RequestOperate operate;

    @Override
    public void execute(Annotation annotation, Object instance, Method method, String[] params, Class<?>[] classes) {
        if (annotation.annotationType() == GetMapping.class) {
            GetMapping getMapping = (GetMapping) annotation;
            Dispatcher dispatcher = new GetDispatcher();
            String value = getMapping.value();
            dispatcher.instance = instance;
            dispatcher.method = method;
            dispatcher.params = params;
            dispatcher.classes = classes;
            DISPATCH_MAP.put(PREFIX_PATH_MAP.get(instance) + value, dispatcher);
            System.out.println("[GET] 成功将" + PREFIX_PATH_MAP.get(instance)+ value + "映射路由");
        }
        if (operate != null) operate.execute(annotation, instance, method, params, classes);
    }

    @Override
    public void setNextOperate(RequestOperate operate) {
        this.operate = operate;
    }
}
