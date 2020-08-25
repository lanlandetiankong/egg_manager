package com.egg.manager.web.controller.index.hello.mq.activemq;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egg.manager.api.message.hello.MessageHelloService;
import com.egg.manager.api.service.service.hello.HelloService;
import com.egg.manager.common.annotation.log.OperLog;
import com.egg.manager.persistence.helper.MyCommonResult;
import com.egg.manager.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/7/18
 * \* Time: 14:19
 * \* Description:
 * \
 */
@Slf4j
@Api(value = "API ==>>  ActiveMqHelloController ", description = "Index")
@RestController
@RequestMapping("/index/hello/mq/activemq")
public class ActiveMqHelloController extends BaseController {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Queue queue;
    @Autowired
    private Topic topic;

    @OperLog( action = "测试ActiveMq-Queue",fullPath = "/index/hello/mq/activemq/queue/test",flag = false)
    @ApiOperation(value = "测试ActiveMq-Queue",response = String.class, httpMethod = "POST")
    @PostMapping("/queue/test")
    public String sendQueue(@RequestBody String str) {
        this.sendMessage(this.queue, str);
        return "success";
    }
    @OperLog( action = "测试ActiveMq-Topic",fullPath = "/index/hello/mq/activemq/topic/test",flag = false)
    @ApiOperation(value = "测试ActiveMq-Topic",response = String.class, httpMethod = "POST")
    @PostMapping("/topic/test")
    public String sendTopic(@RequestBody String str) {
        this.sendMessage(this.topic, str);
        return "success";
    }

    // 发送消息，destination是发送到的队列，message是待发送的消息
    private void sendMessage(Destination destination, final String message){
        jmsMessagingTemplate.convertAndSend(destination, message);
    }

}
