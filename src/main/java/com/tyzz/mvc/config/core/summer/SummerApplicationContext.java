package com.tyzz.mvc.config.core.summer;

import com.tyzz.mvc.dispatcher.Dispatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 当前应用的上下文
 */
public class SummerApplicationContext {
    // 全局空对象
    public static final Object PRESENT = new Object();

    // 当前实例组件
    public static ThreadLocal<Object> CURRENT_INSTANCE = new ThreadLocal<>();

    // 项目根地址
    public volatile static String PROJECT_ROOT_PATH;

    // 主启动类所在地址
    public volatile static String APPLICATION_ROOT_PATH;

    // 包扫描地址列表
    public volatile static List<String> SCAN_PACKAGE_URL;

    // IOC容器
    public volatile static HashMap<String, Object> IOC_CONTAINER = new HashMap<>(25);

    // 路由前缀表
    public volatile static ConcurrentHashMap<Object, String> PREFIX_PATH_MAP = new ConcurrentHashMap<>();

    // 路由映射表
    public volatile static Map<String, Dispatcher> DISPATCH_MAP = new HashMap<>(20);
}
