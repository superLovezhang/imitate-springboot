package com.tyzz.mvc.config.chain.request;

import com.tyzz.mvc.anno.RequestMapping;
import com.tyzz.mvc.dispatcher.RequestMethod;
import com.tyzz.mvc.dispatcher.UniversalDispatcher;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import static com.tyzz.mvc.config.core.summer.SummerApplicationContext.DISPATCH_MAP;
import static com.tyzz.mvc.config.core.summer.SummerApplicationContext.PREFIX_PATH_MAP;

/**
 * Created by IntelliJ IDEA.
 * User: zhangzhao<zhao.zhang@eventslack.com>
 * Date: 2020/12/2
 * Time: 17:05
 */
public class RequestMappingOperate implements RequestOperate {
    private RequestOperate operate;

    @Override
    public void execute(Annotation annotation, Object instance, Method method, String[] params, Class<?>[] classes) {
        if (annotation.annotationType() == RequestMapping.class) {
            RequestMapping requestMapping = (RequestMapping) annotation;
            RequestMethod[] methods = requestMapping.method();
            String value = requestMapping.value();
            if (value == null || value.equals("")) {
                if (operate != null) operate.execute(annotation, instance, method, params, classes);
                return;
            }
            RequestMethod[] requestMethods = methods;
            UniversalDispatcher dispatcher = new UniversalDispatcher();
            dispatcher.instance = instance;
            dispatcher.method = method;
            dispatcher.params = params;
            dispatcher.classes = classes;
            dispatcher.setRequestMethods(requestMethods);
            DISPATCH_MAP.put(PREFIX_PATH_MAP.get(instance) + value, dispatcher);
            System.out.println(Arrays.asList(requestMethods) + " 成功将" + PREFIX_PATH_MAP.get(instance)+ value + "映射路由");
        }
        if (operate != null) operate.execute(annotation, instance, method, params, classes);
    }

    @Override
    public void setNextOperate(RequestOperate operate) {
        this.operate = operate;
    }
}
