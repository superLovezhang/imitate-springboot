package com.tyzz.mvc.config.strategy.scanAnno;

import com.tyzz.mvc.anno.ComponentScan;
import com.tyzz.mvc.config.core.summer.SummerApplicationContext;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import static com.tyzz.mvc.config.core.summer.SummerApplicationContext.APPLICATION_ROOT_PATH;

/**
 * Created by IntelliJ IDEA.
 * User: zhangzhao<zhao.zhang@eventslack.com>
 * Date: 2020/11/26
 * Time: 9:39
 *
 * 用来装配包扫描路径的执行策略
 */
public class AssemblyPackageScanPathStrategy implements ScanAnnoStrategy {

    @Override
    public <T> void execute(Object instance, Class myClass, Annotation annotation, T... arg) {
        if (annotation.annotationType() == ComponentScan.class) {
            ComponentScan scan = (ComponentScan) annotation;
            String[] scanPaths = scan.value().length == 0 ? scan.basePackages() : scan.value();
            List<String> scanPathVales = new ArrayList<>();
            // @ComponentScan没有值 默认为当前主启动类包下
            if (scanPaths == null || scanPaths.length == 0) {
                scanPathVales.add(APPLICATION_ROOT_PATH);
                SummerApplicationContext.SCAN_PACKAGE_URL = scanPathVales;
                return;
            }
            // @ComponentScan有值 遍历
            for (String scanPath : scanPaths) {
                scanPathVales.add(scanPath.replaceAll("\\.", "/"));
            }
            SummerApplicationContext.SCAN_PACKAGE_URL = scanPathVales;
        }
    }
}
