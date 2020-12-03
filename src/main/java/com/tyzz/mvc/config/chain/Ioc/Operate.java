package com.tyzz.mvc.config.chain.Ioc;

/**
 * Created by IntelliJ IDEA.
 * User: zhangzhao<zhao.zhang@eventslack.com>
 * Date: 2020/11/24
 * Time: 17:54
 */
public abstract class Operate {
    protected Operate operate;

    public abstract void execute(Class<?> myClass, Object instance) throws IllegalAccessException, InstantiationException;

    void setNextOperate(Operate operate) {
        this.operate = operate;
    }
}
