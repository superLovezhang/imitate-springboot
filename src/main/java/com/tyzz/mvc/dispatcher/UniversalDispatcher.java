package com.tyzz.mvc.dispatcher;

import com.tyzz.mvc.config.core.engine.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: zhangzhao<zhao.zhang@eventslack.com>
 * Date: 2020/12/1
 * Time: 10:26
 *
 * 通用的dispatcher
 */
public class UniversalDispatcher extends Dispatcher {
    private RequestMethod[] requestMethods;

    public void setRequestMethods(RequestMethod[] requestMethods) {
        this.requestMethods = requestMethods;
    }

    @Override
    public RequestMethod[] getRequestMethod() {
        return requestMethods;
    }
}
