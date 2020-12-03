package com.tyzz.mvc.config.chain.request;

import com.tyzz.mvc.dispatcher.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangzhao<zhao.zhang@eventslack.com>
 * Date: 2020/12/2
 * Time: 17:04
 */
public class RequestChainFactory {
    private static List<RequestOperate> operateList = new ArrayList<>();

    static {
        operateList.add(new RequestMappingOperate());
        operateList.add(new GetMappingOperate());
        operateList.add(new PostMappingOperate());
        setNextChain();
    }

    public static void setOperateList(List<RequestOperate> operateList) {
        RequestChainFactory.operateList = operateList;
        setNextChain();
    }

    public static void addOperateList(RequestOperate operate) {
        operateList.add(operate);
        setNextChain();
    }

    public static RequestOperate getInstance() {
        return operateList.get(0);
    }

    private static void setNextChain() {
        for (int i = 1; i < operateList.size(); i++) {
            operateList.get(i - 1).setNextOperate(operateList.get(i));
        }
    }
}
