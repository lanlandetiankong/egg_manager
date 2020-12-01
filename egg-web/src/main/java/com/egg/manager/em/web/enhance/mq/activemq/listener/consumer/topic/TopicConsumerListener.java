package com.egg.manager.em.web.enhance.mq.activemq.listener.consumer.topic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Slf4j
@Component
public class TopicConsumerListener {
    /**
     * topic模式的消费者
     * @param message
     */
    @JmsListener(destination = "${egg.conf.mq.activemq.topic-name}", containerFactory = "topicListener")
    public void readActiveQueue(String message) {
        log.debug("topic接受到：" + message);
    }
}
