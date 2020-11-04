package com.egg.manager.persistence.em.user.pojo.transfer;

import com.egg.manager.persistence.em.user.pojo.mapstruct.imap.UserGroupMapstruct;
import com.egg.manager.persistence.expand.pojo.mysql.transfer.BaseMysqlTransfer;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Component
@Named("userGroupTransfer")
public class UserGroupTransfer extends BaseMysqlTransfer {

    static UserGroupMapstruct userGroupMapstruct = UserGroupMapstruct.INSTANCE;

}
