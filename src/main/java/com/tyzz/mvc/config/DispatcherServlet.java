package com.tyzz.mvc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyzz.mvc.config.core.engine.ModelAndView;
import com.tyzz.mvc.config.core.engine.ViewEngine;
import com.tyzz.mvc.config.core.summer.SummerApplicationContext;
import com.tyzz.mvc.dispatcher.Dispatcher;
import com.tyzz.mvc.dispatcher.RequestMethod;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.*;

@WebServlet(urlPatterns = "/")
public class DispatcherServlet extends HttpServlet {
    private ViewEngine viewEngine;

    private Map<String, Dispatcher> dispatcherMap = new HashMap<>(20);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uri = request.getRequestURI();
        String method = request.getMethod();
        if (uri.contains("favicon.ico")) {
            return;
        }
        response.setCharacterEncoding("utf8");
        // 通过当前uri找到对应的dispatcher并执行
        Dispatcher dispatcher = dispatcherMap.get(uri);
        // 找不到对应映射方法或者请求类型不正确 404
        if (dispatcher == null || !judgeMethodTrust(Arrays.asList(dispatcher.getRequestMethod()), method)) {
            response.sendError(404);
            return;
        }
        try {
            Object obj = dispatcher.invoke(request, response);

            // 如果返回null 什么都不做
            if (obj == null) return;

            // 返回的是任意其他对象 直接输出
            if (!(obj instanceof ModelAndView)) {
                response.setContentType("application/json;charset=utf8");
                ObjectMapper mapper = new ObjectMapper();
                response.getWriter().print(mapper.writeValueAsString(obj));
                return;
            }

            // 如果返回的类型是ModelAndView
            ModelAndView mv = (ModelAndView) obj;
            // 如果返回对象的views包含redirect 就重定向
            if (mv.getView().startsWith("redirect:")) {
                response.sendRedirect(mv.getView().substring(9));
                return;
            }
            // 把返回的内容交给渲染引擎渲染
            PrintWriter writer = response.getWriter();
            viewEngine.render(mv, writer);
            writer.flush();
        } catch (Exception e) {
            response.sendError(500);
            System.out.println(e);
        }
    }

    /**
     * 初始化成员变量 DispatcherMap
     */
    @Override
    public void init()  {
        ServletContext context = getServletContext();
        // 获取dispatchServlet集合
        dispatcherMap = SummerApplicationContext.DISPATCH_MAP;
        viewEngine = new ViewEngine(context);
    }

    private boolean judgeMethodTrust(List<RequestMethod> list, String rm) {
        for (RequestMethod requestMethod : list) {
            if (requestMethod.name().equals(rm)) return true;
        }
        return false;
    }
}
