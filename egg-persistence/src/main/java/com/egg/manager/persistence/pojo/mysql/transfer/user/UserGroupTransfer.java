package com.egg.manager.persistence.pojo.mysql.transfer.user;

import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.user.UserGroupMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.MyBaseMysqlTransfer;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@Named("userGroupTransfer")
public class UserGroupTransfer extends MyBaseMysqlTransfer {

    static UserGroupMapstruct userGroupMapstruct = UserGroupMapstruct.INSTANCE;

}
