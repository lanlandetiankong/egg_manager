package com.egg.manager.web.config.aspect.pservice;


import com.egg.manager.persistence.db.mongo.repository.log.pc.web.PcWebLoginLogRepository;
import com.egg.manager.persistence.db.mongo.repository.log.pc.web.PcWebOperationLogRepository;
import com.egg.manager.persistence.db.mongo.repository.log.pc.web.PcWebQueryLogRepository;
import com.egg.manager.web.wservices.wservice.aspect.web.ControllerAspectService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

/**
 * @author zhoucj
 * @description: 对@Service接口切面，对查询缓存的
 * @date 2020/10/20
 */
@Slf4j
@Aspect
@Configuration
public class EggRedisQueryCacheAspect {
    @Autowired
    private PcWebQueryLogRepository pcWebQueryLogRepository;

    @Autowired
    private PcWebOperationLogRepository pcWebOperationLogRepository;
    @Autowired
    private PcWebLoginLogRepository pcWebLoginLogRepository;

    @Autowired
    private ControllerAspectService controllerAspectService;


    @Pointcut("@annotation(com.egg.manager.common.annotation.log.pc.pservice.EggRedisQueryCache)")
    public void entryPoint() {
    }

    /**
     * TODO
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "entryPoint()")
    public Object handleAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //方法返回值
        Object result = null;
        boolean defaultFlag = true;
        //计时器
        StopWatch watch = new StopWatch();
        watch.start();
        if (defaultFlag) {
            //调用目标方法，如果没调用这个则表示拦截实际的方法调用。
            result = joinPoint.proceed();
        } else {
            return null;
        }
        return result;
    }

    @After(value = "entryPoint()")
    public void handleAfter(JoinPoint joinPoint) throws Throwable {
    }






}
