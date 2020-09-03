package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.user;


import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserAccountDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserAccountTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserAccountVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {UserAccountTransfer.class}
)
public interface UserAccountVoMapstruct extends MyBaseMysqlVoMapstruct<UserAccount,UserAccountVo, UserAccountDto> {
    UserAccountVoMapstruct INSTANCE = Mappers.getMapper(UserAccountVoMapstruct.class);

    @Mappings({})
    UserAccount transferVoToEntity(UserAccountVo vo);

    @Mappings({
            @Mapping(target = "userTypeStr",expression = "java(handleUserTypeGetStr(entity.getUserType()))"),
            @Mapping(target = "uploadImgBean", ignore = true),
            @Mapping(target = "belongTenantId", ignore = true),
            @Mapping(target = "belongTenant", ignore = true),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    UserAccountVo transferEntityToVo(UserAccount entity);
    @Mappings({
            @Mapping(target = "userTypeStr",expression = "java(handleUserTypeGetStr(dto.getUserType()))"),
            @Mapping(target = "belongTenant",expression = "java(commonTranslateDefineTenantEntityToVo(dto.getBelongTenant()))"),
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    UserAccountVo transferDtoToVo(UserAccountDto dto);
}
