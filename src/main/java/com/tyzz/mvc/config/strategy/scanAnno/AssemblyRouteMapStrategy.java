package com.tyzz.mvc.config.strategy.scanAnno;

import com.tyzz.mvc.anno.GetMapping;
import com.tyzz.mvc.anno.PostMapping;
import com.tyzz.mvc.anno.RequestMapping;
import com.tyzz.mvc.config.chain.request.RequestChainFactory;
import com.tyzz.mvc.config.core.summer.SummerApplicationContext;
import com.tyzz.mvc.dispatcher.*;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import static com.tyzz.mvc.config.core.summer.SummerApplicationContext.DISPATCH_MAP;
import static com.tyzz.mvc.config.core.summer.SummerApplicationContext.PREFIX_PATH_MAP;

/**
 * Created by IntelliJ IDEA.
 * User: zhangzhao<zhao.zhang@eventslack.com>
 * Date: 2020/11/26
 * Time: 8:54
 *
 * description:
 * This strategy is used to scan the {@Annotation RequestMapping}
 * annotation on the method to assemble the global routing mapping table
 */
public class AssemblyRouteMapStrategy implements ScanAnnoStrategy {
    @Override
    public <T> void execute(Object instance, Class myClass, Annotation annotation, T... arg) {
        Method method = null;
        for (T t : arg) {
            if (t instanceof Method) method = (Method) t;
        }
        Assert.notNull(method, "current scan annotation method is null!");

        String[] params;

        Class<?>[] classes = method.getParameterTypes();
        LocalVariableTableParameterNameDiscoverer lv = new LocalVariableTableParameterNameDiscoverer();
        params = lv.getParameterNames(method);

        RequestChainFactory.getInstance().execute(annotation, instance, method, params, classes);
    }

    private RequestMethod[] convertRequestMethod(String[] s) {
        RequestMethod[] methods = new RequestMethod[s.length];
        for (int i = 0; i < s.length; i++) {
            methods[i] = RequestMethod.valueOf(s[i]);
        }
        return methods;
    }
}
