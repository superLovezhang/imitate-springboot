package com.tyzz.mvc.dispatcher;

import com.tyzz.mvc.config.core.engine.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetDispatcher extends Dispatcher {
    private final RequestMethod requestMethod = RequestMethod.GET;

    @Override
    public RequestMethod[] getRequestMethod() {
        return new RequestMethod[] {requestMethod};
    }

    @Override
    protected Object invokeMethod(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 创建一个参数值列表
        Object[] arguments = new Object[params.length];
        // 遍历参数类型 给方法的参数值列表赋值
        for (int i = 0; i < classes.length; i++) {
            Class<?> argumentClass = classes[i];
            if (argumentClass == HttpServletRequest.class) {
                arguments[i] = request;
            } else if (argumentClass == HttpServletResponse.class) {
                arguments[i] = response;
            } else if (argumentClass == HttpSession.class) {
                arguments[i] = request.getSession();
            } else if (argumentClass == ModelAndView.class) {
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
                System.out.println("Miss params! type is" + argumentClass);
            }
        }
        // 通过反射 让目标方法执行并传入参数
        return this.method.invoke(instance, arguments);
    }
}
