package com.egg.manager.controller.user;

import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.user.UserAccountMapper;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/user_account")
public class UserAccountController {

    @Autowired
    private UserAccountMapper userAccountMapper ;

    @RequestMapping("/test")
    public void test() {
        List<UserAccount> all = userAccountMapper.selectList(null);
        System.out.println("for debug");
    }
}
