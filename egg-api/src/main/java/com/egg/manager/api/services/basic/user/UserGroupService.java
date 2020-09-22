package com.egg.manager.api.services.basic.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.db.mysql.entity.user.UserGroup;
import com.egg.manager.persistence.db.mysql.mapper.user.UserGroupMapper;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserGroupVo;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
public interface UserGroupService extends IService<UserGroup>,MyBaseMysqlService<UserGroupMapper,UserGroup,UserGroupVo> {

    
}
