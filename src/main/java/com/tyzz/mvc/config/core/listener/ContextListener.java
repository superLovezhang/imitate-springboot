package com.tyzz.mvc.config.core.listener;

import com.tyzz.mvc.config.core.summer.SummerApplicationContext;
import com.tyzz.mvc.config.chain.Ioc.OperateChainFactory;
import com.tyzz.mvc.config.chain.Ioc.RouteMapOperate;
import com.tyzz.mvc.config.strategy.scanAnno.AssemblyPackageScanPathStrategy;
import com.tyzz.mvc.config.strategy.scanAnno.ScanAnnoStrategyContext;
import com.tyzz.mvc.exception.SummerCompileException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.lang.annotation.Annotation;

import static com.tyzz.mvc.config.core.summer.SummerApplicationContext.*;

/**
 * Created by IntelliJ IDEA.
 * User: zhangzhao<zhao.zhang@eventslack.com>
 * Date: 2020/11/24
 * Time: 16:33
 * <p>
 * Tomcat监听器 监听启动时需要做的一些操作
 */
@WebListener
public class ContextListener implements ServletContextListener {
    @Override

    public void contextInitialized(ServletContextEvent sce) {
        long start = System.currentTimeMillis();
        ServletContext context = sce.getServletContext();

        // 根据主启动类上的@ComponentScan注解设置全局包扫描路径
        try {
            setScanPathList(context);
        } catch (SummerCompileException e) {
            System.out.println(e);
            // 停止运行...
            return;
        }

        // 遍历包扫描路径数组 做一些操作
        traverseScanPaths();

        long time = System.currentTimeMillis() - start;
        System.out.println("-------------------------------------------项目启动成功^_^-----------------------------------------------\n" +
                "项目已在端口8848上运行\n" +
                "本次启动耗时:" + time);
    }

    /**
     * 获取主启动类 并对其进行操作
     *
     * @return
     */
    private ServletContext setScanPathList(ServletContext context) throws SummerCompileException {
        Class c = null;
        try {
            c = Class.forName((String) context.getAttribute("application"));
        } catch (ClassNotFoundException e) {
            throw new SummerCompileException();
        }
        PROJECT_ROOT_PATH = c.getClassLoader().getResource("").getPath().substring(1);
        String name = c.getName();
        String mainClassPath = name.substring(0, name.lastIndexOf("."));
        APPLICATION_ROOT_PATH = mainClassPath.replaceAll("\\.", "/");
        // 扫描启动类上面的注解
        Annotation[] annotations = c.getAnnotations();
        // 递归所有注解 根据不同注解进行不同操作 -> 策略模式
        ScanAnnoStrategyContext strategyContext = new ScanAnnoStrategyContext();
        strategyContext.setStrategy(new AssemblyPackageScanPathStrategy());
        strategyContext.run(PRESENT, c, annotations);
        return context;
    }

    /**
     * 遍历需要扫描的路径数组
     */
    private void traverseScanPaths() {
        // 遍历需要扫描的路径列表
        for (String scanPath : SummerApplicationContext.SCAN_PACKAGE_URL) {
            scanPathFile(scanPath);
        }
        System.out.println("IOC容器加载成功!");
        System.out.println("路由映射成功!");
    }

    /**
     * 递归扫描路径下所有目录和文件 对其进行操作
     *
     * @param scanPath
     */
    private void scanPathFile(String scanPath) {
        // xxx/xxx/xxx.class -- 为了使用File 确定文件类型以及存在不存在
        //        |
        // xx.xxx.xxx 为了使用ClassLoader加载类
        System.out.println("scanner path =====>" + scanPath);
        // getResource需要后缀名 要求路径/分隔
        String absoluteUrl = Thread.currentThread().getContextClassLoader().getResource(scanPath).getPath();
        File file = new File(absoluteUrl);
        if (!file.exists()) return;
        if (file.isDirectory()) {
            String[] list = file.list();
            for (String s : list) {
                scanPathFile(scanPath + "/" + s);
            }
            return;
        }
        if (file.isFile() && file.getName().endsWith(".class")) {
            try {
                // Class.forName不需要后缀名 并且路径要求.分隔
                String scanFilePath = scanPath.substring(0, scanPath.lastIndexOf("."));
                // 加载类
                Class<?> myClass = Class.forName(scanFilePath.replaceAll("/", "."));
                try {
                    operateByClass(myClass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 扫描整个类 进行操作
     *
     * @param myClass
     * @throws Exception
     */
    private void operateByClass(Class<?> myClass) throws Exception {
        // 如果是个注解 不需要反射
        if (myClass.isAnnotation()) {
            return;
        }

        // 对Bean执行一系列操作链 -> 责任链模式
        // 设置责任链 IOC -> RouteMap -> ...
        OperateChainFactory.setOperateChain(new RouteMapOperate());
        OperateChainFactory.getInstance().execute(myClass, PRESENT);
    }
}
