package com.egg.manager.persistence.pojo.mysql.transfer.role;

import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.role.RolePermissionMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.BaseMysqlTransfer;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @version V1.0
 * @description:
 * @date 2020/10/20
 */
@Component
@Named("rolePermissionTransfer")
public class RolePermissionTransfer extends BaseMysqlTransfer {

    static RolePermissionMapstruct rolePermissionMapstruct = RolePermissionMapstruct.INSTANCE;


}
