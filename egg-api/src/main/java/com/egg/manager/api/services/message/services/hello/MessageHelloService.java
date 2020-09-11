package com.egg.manager.api.services.message.services.hello;

public interface MessageHelloService {

    String loadBalancePort();

    /**
     * service模块 的
     * @return
     */
    String loadServiceBalancePort();
}
