package com.egg.manager.persistence.em.user.pojo.mapstruct.imap.session;

import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
import com.egg.manager.persistence.em.user.pojo.bean.UserAccountToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface UserAccountTokenMapstruct {
    UserAccountTokenMapstruct INSTANCE = Mappers.getMapper(UserAccountTokenMapstruct.class);

    /**
     * 复制-UserAccount->UserAccountToken
     * @param userAccountEntity
     * @return
     */
    @Mappings({
            @Mapping(target = "userAccountId", source = "fid"),
            @Mapping(target = "userBelongTenantId", ignore = true),
            @Mapping(target = "token", ignore = true),
            @Mapping(target = "authorization", ignore = true)
    })
    UserAccountToken translateEntityToToken(UserAccountEntity userAccountEntity);


}
