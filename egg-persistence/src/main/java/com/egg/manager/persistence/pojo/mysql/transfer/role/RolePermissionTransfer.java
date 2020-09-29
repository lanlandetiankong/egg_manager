package com.egg.manager.persistence.pojo.mysql.transfer.role;

import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.role.RolePermissionMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.BaseMysqlTransfer;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@Named("rolePermissionTransfer")
public class RolePermissionTransfer extends BaseMysqlTransfer {

    static RolePermissionMapstruct rolePermissionMapstruct = RolePermissionMapstruct.INSTANCE;


}
