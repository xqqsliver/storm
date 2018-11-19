package com.storm.service.spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * Spout 是拓扑中数据流的来源
 * @author bingling
 * @since 18/11/19-下午2:43
 */
public class SpoutTest extends BaseRichSpout {

    private SpoutOutputCollector collector;

    private static final String field="word";

    private int count=1;

    private String[] message =  {
            "My nickname is xuwujing",
            "My blog address is http://www.panchengming.com/",
            "My interest is playing games"
    };

    /**
     * 初始化的时候被调用
     * @param map
     * @param topologyContext
     * @param collector
     */
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector collector) {
        System.out.println("open:"+map.get("test"));
        this.collector = collector;
    }

    public void nextTuple() {
        if(count<=message.length){
            System.out.println("第"+count +"次开始发送数据...");
            this.collector.emit(new Values(message[count-1]));
        }
        count++;
    }

    /*
     *用于声明数据格式
     */
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        System.out.println("定义格式...");
        outputFieldsDeclarer.declare(new Fields(field));
    }

    /**
     * 当一个Tuple处理成功时，会调用这个方法
     */
    @Override
    public void ack(Object obj) {
        System.out.println("ack:"+obj);
    }

    /**
     * 当Topology停止时，会调用这个方法
     */
    @Override
    public void close() {
        System.out.println("关闭...");
    }

    /**
     * 当一个Tuple处理失败时，会调用这个方法
     */
    @Override
    public void fail(Object obj) {
        System.out.println("失败:"+obj);
    }
}
