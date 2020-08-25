package com.egg.manager.web.mq.activemq.listener.consumer.topic;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TopicConsumerListener {
    //topic模式的消费者
    @JmsListener(destination="${egg.conf.mq.activemq.topic-name}", containerFactory="topicListener")
    public void readActiveQueue(String message) {
        System.out.println("topic接受到：" + message);
    }
}
