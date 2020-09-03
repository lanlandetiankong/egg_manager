package com.egg.manager.persistence.pojo.transfer.mysql.user;

import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.user.UserGroupVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@Named("UserGroupTransfer")
public class UserGroupTransfer extends MyBaseMysqlTransfer {

    static UserGroupVoMapstruct userGroupVoMapstruct = UserGroupVoMapstruct.INSTANCE ;

}
