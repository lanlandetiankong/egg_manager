package com.egg.manager.api.services.message.services.hello;

public interface MessageHelloService {
    /**
     * 测试 负载均衡端口号
     * @return
     */
    String loadBalancePort();

    /**
     * 测试 service 负载均衡的端口号
     * @return
     */
    String loadServiceBalancePort();
}
