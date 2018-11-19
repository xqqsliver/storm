package com.storm.service.Bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * 所有的数据处理都由bolt完成
 * @author bingling
 * @since 18/11/19-下午2:50
 */
public class BoltTest extends BaseRichBolt {

    private OutputCollector collector;

    /**
     * 在Bolt启动前执行，提供Bolt启动环境配置的入口
     * @param map
     * @param topologyContext
     * @param collector
     */
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector collector) {
        System.out.println("prepare:"+map.get("test"));
        this.collector=collector;
    }

    public void execute(Tuple tuple) {
        String msg=tuple.getStringByField("word");
        System.out.println("开始分割单词:"+msg);
        String[] words = msg.toLowerCase().split(" ");
        for (String word : words) {
            this.collector.emit(new Values(word));//向下一个bolt发射数据
        }
    }

    /**
     * 用于声明数据格式
     * @param declarer
     */
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("count"));
    }

    @Override
    public void cleanup() {
        System.out.println("TestBolt的资源释放");
    }
}
