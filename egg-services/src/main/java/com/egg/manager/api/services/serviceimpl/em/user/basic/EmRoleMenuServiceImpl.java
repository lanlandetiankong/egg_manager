package com.egg.manager.api.services.serviceimpl.em.user.basic;

import org.apache.dubbo.config.annotation.Service;
import com.egg.manager.api.exchange.servicesimpl.basic.MyBaseMysqlServiceImpl;
import com.egg.manager.api.services.em.user.basic.EmRoleMenuService;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmRoleMenuEntity;
import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.persistence.em.user.db.mysql.mapper.EmRoleMenuMapper;
import com.egg.manager.persistence.em.user.pojo.vo.EmRoleMenuVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service(interfaceClass = EmRoleMenuService.class)
public class EmRoleMenuServiceImpl extends MyBaseMysqlServiceImpl<EmRoleMenuMapper, EmRoleMenuEntity, EmRoleMenuVo> implements EmRoleMenuService {
    @Override
    public int batchUpdateStateByRole(String roleId, List<String> menuIdList, Short stateVal
            , EmUserAccountEntity loginUser) {
        return this.baseMapper.batchUpdateStateByRole(roleId, menuIdList, stateVal, loginUser);
    }
}
