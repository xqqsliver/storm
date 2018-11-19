package com.storm.service.spout.model.view;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author bingling
 * @since 18/11/19-下午4:42
 */
public class MyInvocatioHandler implements InvocationHandler {

    private Object target;

    public MyInvocatioHandler(Object target) {
        this.target = target;
    }
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        //System.out.println("-----before-----");
        this.before();
        Object result = method.invoke(target, args);
        // System.out.println("-----end-----");
        this.after();
        return result;
    }
    // 生成代理对象
    public Object getProxy() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return Proxy.newProxyInstance(loader, interfaces, this);
    }

    private void before(){
        System.out.println("预处理!");
    }
    private void after(){
        System.out.println("善后处理!");
    }
    public static void main(String[] args) {

        ProxyModel service = new ProxyModelImpl();
        MyInvocatioHandler handler = new MyInvocatioHandler(service);
        ProxyModel serviceProxy = ( ProxyModel)handler.getProxy();
        serviceProxy.process();
    }

}
