package com.egg.manager.facade.persistence.em.user.pojo.transfer;

import com.egg.manager.facade.persistence.em.user.pojo.mapstruct.imap.EmRolePermissionMapstruct;
import com.egg.manager.facade.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Component
@Named("rolePermissionTransfer")
public class EmRolePermissionTransfer extends BaseMysqlTransfer {

    static EmRolePermissionMapstruct emRolePermissionMapstruct = EmRolePermissionMapstruct.INSTANCE;


}
