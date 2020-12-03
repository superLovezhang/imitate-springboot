package com.tyzz.mvc.dispatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyzz.mvc.config.core.engine.ModelAndView;
import com.tyzz.mvc.exception.SummerRuntimeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.lang.reflect.Method;

public abstract class Dispatcher {
    public Object instance; // 映射的controller

    public Method method; // 映射的method

    public String[] params; // 该请求的参数名列表

    public Class<?>[] classes; // 该请求的参数类型列表

    private ObjectMapper objectMapper = new ObjectMapper(); // json转换工具

    private BufferedReader reader = null; // 请求Dispatcher的流

    /**
     * Dispatcher最终会被调用的方法
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public Object invoke(HttpServletRequest request, HttpServletResponse response) throws Exception {
     return this.invokeMethod(request, response);
    }

    /**
     * 获取当前Dispatcher支持的请求类型
     * @return
     */
    public abstract RequestMethod[] getRequestMethod();

    /**
     * invoke里面执行的方法
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    protected Object invokeMethod(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 创建一个参数值列表
        Object[] arguments = new Object[params.length];
        // 遍历参数类型 给方法的参数值列表赋值
        for (int i = 0; i < classes.length; i++) {
            Class argumentClass = classes[i];
            if (argumentClass == HttpServletRequest.class) {
                arguments[i] = request;
            } else if (argumentClass == HttpServletResponse.class) {
                arguments[i] = response;
            } else if (argumentClass == HttpSession.class) {
                arguments[i] = request.getSession();
            } else if (classes[i] == ModelAndView.class) {
                arguments[i] = new ModelAndView();
            } else if ((argumentClass == int.class) || (argumentClass == Integer.class)) {
                arguments[i] = Integer.valueOf(getParamOrDefault(request, params[i], "0"));
            } else if ((argumentClass == boolean.class) || (argumentClass == Boolean.class)) {
                arguments[i] = Boolean.valueOf(getParamOrDefault(request, params[i], "false"));
            } else if ((argumentClass == long.class) || (argumentClass == Long.class)) {
                arguments[i] = Long.valueOf(getParamOrDefault(request, params[i], "0"));
            } else if (argumentClass == String.class) {
                arguments[i] = String.valueOf(getParamOrDefault(request, params[i], ""));
            } else {
                if (reader != null) throw new SummerRuntimeException("Miss params! type is " + argumentClass);
                BufferedReader reader = request.getReader();
                arguments[i] = objectMapper.readValue(reader, argumentClass);
            }
        }
        // 通过反射 让目标方法执行并传入参数
        return this.method.invoke(instance, arguments);
    }

    /**
     * 获取方法参数值
     * @param request
     * @param paramName
     * @param defaultValue
     * @return
     */
    protected String getParamOrDefault(HttpServletRequest request, String paramName, String defaultValue) {
        String value = request.getParameter(paramName);
        return value == null ? defaultValue : value;
    }
}
