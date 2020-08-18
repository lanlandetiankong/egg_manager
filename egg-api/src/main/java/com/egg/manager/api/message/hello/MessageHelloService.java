package com.egg.manager.api.message.hello;

public interface MessageHelloService {

    String loadBalancePort();

    /**
     * service模块 的
     * @return
     */
    String loadServiceBalancePort();
}
