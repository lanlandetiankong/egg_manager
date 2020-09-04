package com.egg.manager.persistence.pojo.transfer.mysql.user;

import com.egg.manager.persistence.pojo.mapstruct.mysql.user.UserGroupMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@Named("UserGroupTransfer")
public class UserGroupTransfer extends MyBaseMysqlTransfer {

    static UserGroupMapstruct userGroupVoMapstruct = UserGroupMapstruct.INSTANCE ;

}
