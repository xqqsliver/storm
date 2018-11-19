package com.storm.kafka;

import org.apache.storm.spout.Scheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * @author bingling
 * @since 18/11/19-下午8:11
 */
public class MessageScheme implements Scheme {
    public List<Object> deserialize(ByteBuffer byteBuffer) {
        try {
            String msg = new String(byteBuffer.array(), "UTF-8");
            return new Values(msg);
        } catch (UnsupportedEncodingException e) {

        }
        return null;
    }

    public Fields getOutputFields() {
        return new Fields("msg");
    }
}
