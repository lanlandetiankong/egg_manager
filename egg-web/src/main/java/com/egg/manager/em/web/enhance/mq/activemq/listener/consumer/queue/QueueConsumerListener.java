package com.egg.manager.em.web.enhance.mq.activemq.listener.consumer.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Component
public class QueueConsumerListener {

    /**
     * queue模式的消费者
     * @param message
     */
    @JmsListener(destination = "${egg.conf.mq.activemq.queue-name}", containerFactory = "queueListener")
    public void readActiveQueue(String message) {
        log.debug("queue接受到：" + message);
    }


}
