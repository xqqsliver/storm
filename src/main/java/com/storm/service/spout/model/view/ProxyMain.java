package com.storm.service.spout.model.view;

/**
 *静态代理有三个部分组成
 * 一个是接口
 * 两个实现类ab,a是被代理类，b委托类(代理类)
 * @author bingling
 * @since 18/11/19-下午4:26
 */
public class ProxyMain implements ProxyModel {

    private ProxyModel proxyModel;

    ProxyMain(ProxyModel proxyModel){
        this.proxyModel = proxyModel;
    }
    public void process() {
        System.out.println("ProxyMain.process");
        proxyModel.process();//委托ProxyModelImpl类处理具体逻辑
    }

    public static void main(String args[]){
        ProxyMain proxyMain=new ProxyMain(new ProxyModelImpl());
        proxyMain.process();

    }

}
