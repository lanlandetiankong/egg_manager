package com.egg.manager.common.redis.user;

import com.egg.manager.entity.user.UserAccount;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/10/5
 * \* Time: 16:34
 * \* Description:
 * \
 */
public class UserTokenRedisGet {
    @Autowired
    private RedisTemplate redisTemplate ;


    public static UserAccount getUserAccountByToken(String token){
        if(StringUtils.isBlank(token)){
            return null ;
        }   else {
            return null ;
        }
    }
}
