package com.storm.service.main;

import com.storm.service.Bolt.BoltTest;
import com.storm.service.Bolt.BoltTotal;
import com.storm.service.spout.SpoutTest;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * spout(数据)-bolt1(分割单词)-bolt2(统计)
 * @author bingling
 * @since 18/11/19-下午2:57
 */
public class Topology {
    private static final String test_spout = "test_spout";
    private static final String test_bolt = "test_bolt";
    private static final String test2_bolt = "test2_bolt";

    public static void main(String[] args) {
        //定义一个拓扑
        TopologyBuilder builder = new TopologyBuilder();
       // DRPCSpout spout = new DRPCSpout("exclamation");
        //设置一个Executeor(线程)，默认一个
        builder.setSpout(test_spout, new SpoutTest(), 1);
        //shuffleGrouping:表示是随机分组
        //设置一个Executeor(线程)，和一个task
        builder.setBolt(test_bolt, new BoltTest(), 1).setNumTasks(1).shuffleGrouping(test_spout);
        //fieldsGrouping:表示是按字段分组
        //设置一个Executeor(线程)，和一个task
        builder.setBolt(test2_bolt, new BoltTotal(), 1).setNumTasks(1).fieldsGrouping(test_bolt, new Fields("count"));
        Config conf = new Config();
        conf.put("test", "test");
        try {
            //运行拓扑
            if (args != null && args.length > 0) { //有参数时，表示向集群提交作业，并把第一个参数当做topology名称
                System.out.println("运行远程模式");
                StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
            } else {//没有参数时，本地提交
                //启动本地模式
                System.out.println("运行本地模式");
                LocalCluster cluster = new LocalCluster();
                cluster.submitTopology("Word-counts", conf, builder.createTopology());
                Thread.sleep(20000);
                //  //关闭本地集群
                cluster.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
