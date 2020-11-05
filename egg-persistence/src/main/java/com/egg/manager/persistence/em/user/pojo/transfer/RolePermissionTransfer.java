package com.egg.manager.persistence.em.user.pojo.transfer;

import com.egg.manager.persistence.em.user.pojo.mapstruct.imap.RolePermissionMapstruct;
import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Component
@Named("rolePermissionTransfer")
public class RolePermissionTransfer extends BaseMysqlTransfer {

    static RolePermissionMapstruct rolePermissionMapstruct = RolePermissionMapstruct.INSTANCE;


}
