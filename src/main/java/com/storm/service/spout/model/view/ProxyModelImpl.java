package com.storm.service.spout.model.view;

/**
 * @author bingling
 * @since 18/11/19-下午4:25
 */
public class ProxyModelImpl implements  ProxyModel{

    public void process() {
        System.out.println("ProxyModelImpl处理代理类方法");
    }
}
