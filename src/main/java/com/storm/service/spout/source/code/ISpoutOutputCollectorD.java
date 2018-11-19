package com.storm.service.spout.source.code;

import org.apache.storm.task.IErrorReporter;

import java.util.List;

/**
 * @see org.apache.storm.spout.ISpoutOutputCollector 的源码
 * 实现类是SpoutOutputCollector，它是一个代理类
 * @author bingling
 * @since 18/11/19-下午3:49
 */
public interface ISpoutOutputCollectorD extends IErrorReporter {

    /**
     * 该方法用来向外发送数据,它的返回值是该消息所有发送目标的taskID集合;
     * 参数:
     * streamId:消息Tuple将要被输出到的流
     * tuple:要输出的消息,是一个Object列表
     * messageId:输出消息的标记信息,如果messageId被设置为null,则Storm不会追踪该消息,
     * 否则它会被用来追踪所发出的消息处理情况
     */
    List<Integer> emit(String streamId, List<Object> tuple, Object messageId);
    //List<Integer> emit(String var1, List<Object> var2, Object var3);

    /**
     * 该方法与上面emit方法类似,区别在于:
     * 1.数据(消息)只由所指定taskId的Task接收;(这就意味着如果没有下游节点接收该消息,则该消息就没有被真正发送)
     * 2.该方法要求参数streamId所对应的流必须为直接流,接收端的Task必须以直接分组的方式来接收消息,
     * 否则会抛出异常.
     */
    void emitDirect(int taskId, String streamId, List<Object> tuple, Object messageId);
    //void emitDirect(int var1, String var2, List<Object> var3, Object var4);

    long getPendingCount();
}
