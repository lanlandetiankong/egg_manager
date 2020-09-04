package com.egg.manager.persistence.pojo.transfer.mysql.role;

import com.egg.manager.persistence.pojo.mapstruct.mysql.role.RolePermissionMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@Named("RolePermissionTransfer")
public class RolePermissionTransfer extends MyBaseMysqlTransfer {

    static RolePermissionMapstruct rolePermissionVoMapstruct = RolePermissionMapstruct.INSTANCE;



}
