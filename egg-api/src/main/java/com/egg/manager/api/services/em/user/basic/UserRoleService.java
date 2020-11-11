package com.egg.manager.api.services.em.user.basic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.manager.api.exchange.services.basic.MyBaseMysqlService;
import com.egg.manager.persistence.commons.base.beans.helper.MyCommonResult;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.persistence.commons.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.persistence.commons.base.query.form.QueryFormFieldBean;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserRoleEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.UserRoleMapper;
import com.egg.manager.persistence.em.user.pojo.bean.CurrentLoginUserInfo;
import com.egg.manager.persistence.em.user.pojo.dto.UserRoleDto;
import com.egg.manager.persistence.em.user.pojo.vo.UserRoleVo;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface UserRoleService extends IService<UserRoleEntity>, MyBaseMysqlService<UserRoleEntity, UserRoleMapper, UserRoleVo> {
    /**
     * 取得当前用户关联的 UserRole
     * @param account
     * @return
     */
    List<UserRoleEntity> dealGetAllByAccount(UserAccountEntity account);


}
