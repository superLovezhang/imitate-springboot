package com.tyzz.mvc;


import com.tyzz.mvc.anno.ComponentScan;
import com.tyzz.mvc.anno.SummerBootApplication;
import com.tyzz.mvc.config.core.summer.SummerApplication;
import org.apache.catalina.LifecycleException;


@SummerBootApplication
@ComponentScan("com.tyzz.mvc.controller")
public class Application {
    public static void main(String[] args) throws LifecycleException {
        SummerApplication.run(Application.class, args);
    }
}
