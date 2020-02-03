package com.egg.manager.mapper.user;

import com.egg.manager.entity.user.UserAccount;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户账号表 Mapper 接口
 * </p>
 *
 * @author zhouchengjie123
 * @since 2019-09-12
 */
public interface UserAccountMapper extends BaseMapper<UserAccount> {


    //批量 伪删除
    int batchFakeDelByIds(List<String> delIds) ;
}
