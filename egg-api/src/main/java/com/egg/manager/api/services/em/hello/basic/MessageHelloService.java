package com.egg.manager.api.services.em.hello.basic;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
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
