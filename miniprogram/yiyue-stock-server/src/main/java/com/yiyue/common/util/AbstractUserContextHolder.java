package com.yiyue.common.util;

/**
 * @Author: XiaoQi
 * @Date: 2020/9/15 9:42
 * 用于获取登录用户信息
 */
public abstract class AbstractUserContextHolder<T> {

    private ThreadLocal<T> threadLocal;

    public AbstractUserContextHolder() {
        this.threadLocal = new ThreadLocal<>();
    }

    public void setContext(T t) {
        threadLocal.set(t);
    }

    public T getContext() {
        return threadLocal.get();
    }

    public void clearContext() {
        threadLocal.remove();
    }

}
