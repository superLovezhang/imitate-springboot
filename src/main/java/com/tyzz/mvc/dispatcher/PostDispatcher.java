package com.tyzz.mvc.dispatcher;

import java.io.BufferedReader;

public class PostDispatcher extends Dispatcher {
    private final RequestMethod requestMethod = RequestMethod.POST;

    @Override
    public RequestMethod[] getRequestMethod() {
        return new RequestMethod[] {requestMethod};
    }
}
