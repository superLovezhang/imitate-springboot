package com.tyzz.mvc.config.core.summer;

import com.tyzz.mvc.anno.ComponentScan;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import javax.servlet.ServletContext;
import java.io.File;

public class SummerApplication {
    public static void run(Class<?> c, String[] args) throws LifecycleException {
        // 启动tomcat
        start(c);
    }

    private static void start(Class<?> c) throws LifecycleException {
        // 启动内嵌tomcat插件
        Tomcat tomcat = new Tomcat();
        // 设置端口
        tomcat.setPort(8848);
        // 连接
        tomcat.getConnector();
        StandardServer server = (StandardServer) tomcat.getServer();
        AprLifecycleListener listener = new AprLifecycleListener();
        server.addLifecycleListener(listener);
        // 获取上下文 参数一表示项目访问根路径 参数二表示静态资源访问根路径
        Context ctx = tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());
        StandardRoot root = new StandardRoot(ctx);
        root.addPreResources(new DirResourceSet(root, "/WEB-INF/classes", new File("target/classes").getAbsolutePath(), "/"));
        ctx.setResources(root);
        ServletContext servletContext = ctx.getServletContext();
        servletContext.setAttribute("application", c.getName());
        // 启动
        tomcat.start();
    }
}
