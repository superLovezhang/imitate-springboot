package com.tyzz.mvc.config.chain.request;

import com.tyzz.mvc.anno.PostMapping;
import com.tyzz.mvc.dispatcher.Dispatcher;
import com.tyzz.mvc.dispatcher.PostDispatcher;

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
public class PostMappingOperate implements RequestOperate {
    private RequestOperate operate;

    @Override
    public void execute(Annotation annotation, Object instance, Method method, String[] params, Class<?>[] classes) {
        if (annotation.annotationType() == PostMapping.class) {
            PostMapping postMapping = (PostMapping) annotation;
            Dispatcher dispatcher = new PostDispatcher();
            String value = postMapping.value();
            dispatcher.instance = instance;
            dispatcher.method = method;
            dispatcher.params = params;
            dispatcher.classes = classes;
            DISPATCH_MAP.put(PREFIX_PATH_MAP.get(instance) + value, dispatcher);
            System.out.println("[POST] 成功将" + PREFIX_PATH_MAP.get(instance)+ value + "映射路由");
        }
        if (operate != null) operate.execute(annotation, instance, method, params, classes);
    }

    @Override
    public void setNextOperate(RequestOperate operate) {
        this.operate = operate;
    }
}
