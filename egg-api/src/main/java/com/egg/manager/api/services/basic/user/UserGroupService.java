package com.egg.manager.api.services.basic.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.db.mysql.entity.user.UserGroup;
import com.egg.manager.persistence.db.mysql.mapper.user.UserGroupMapper;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserGroupVo;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface UserGroupService extends IService<UserGroup>, MyBaseMysqlService<UserGroup, UserGroupMapper, UserGroupVo> {


}
