package com.egg.manager.web.mq.activemq.listener.consumer.queue;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumerListener {

    //queue模式的消费者
    @JmsListener(destination="${egg.conf.mq.activemq.queue-name}", containerFactory="queueListener")
    public void readActiveQueue(String message) {
        System.out.println("queue接受到：" + message);
    }


}
