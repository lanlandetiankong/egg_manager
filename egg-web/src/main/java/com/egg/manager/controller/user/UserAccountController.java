package com.egg.manager.controller.user;

import com.egg.manager.annotation.log.OperLog;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.user.UserAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/user_account")
public class UserAccountController {

    @Autowired
    private UserAccountMapper userAccountMapper ;

    @OperLog(action="test",modelName = "test login",description = "测试日志记录")
    @PostMapping("/test")
    public void test(String name1) {
        List<UserAccount> all = userAccountMapper.selectList(null);
        System.out.println("for debug");
    }
}
