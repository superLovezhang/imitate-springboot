package com.tyzz.mvc.config.chain.Ioc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangzhao<zhao.zhang@eventslack.com>
 * Date: 2020/11/24
 * Time: 17:50
 */
public class OperateChainFactory {
    private static List<Operate> operateChain = new ArrayList<>();

    static {
        operateChain.add(new IocOperate());
    }

    public static void setOperateChain(Operate operate) {
        operateChain.add(operate);
    }

    public static Operate getInstance() {
        for (int i = 1; i < operateChain.size(); i++) {
            operateChain.get(i - 1).setNextOperate(operateChain.get(i));
        }
        return operateChain.get(0);
    }
}
