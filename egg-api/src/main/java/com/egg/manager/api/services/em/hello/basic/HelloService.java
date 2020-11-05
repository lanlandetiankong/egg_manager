package com.egg.manager.api.services.em.hello.basic;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface HelloService {
    /**
     * 返回hello
     * @return
     */
    String sayHello();

    /**
     * 测试
     * @return
     */
    String loadBalanceTest();
}
