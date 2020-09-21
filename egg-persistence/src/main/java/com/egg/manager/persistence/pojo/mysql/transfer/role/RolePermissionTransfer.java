package com.egg.manager.persistence.pojo.mysql.transfer.role;

import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.role.RolePermissionMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.MyBaseMysqlTransfer;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@Named("rolePermissionTransfer")
public class RolePermissionTransfer extends MyBaseMysqlTransfer {

    static RolePermissionMapstruct rolePermissionMapstruct = RolePermissionMapstruct.INSTANCE;


}
