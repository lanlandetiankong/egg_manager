package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.user;


import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserAccountDto;
import com.egg.manager.persistence.pojo.excel.export.user.UserAccountXlsOutModel;
import com.egg.manager.persistence.pojo.excel.introduce.user.UserAccountXlsInModel;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserAccountTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserAccountVo;
import org.mapstruct.*;
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

    @Mappings({
            @Mapping(target = "userTypeStr",expression = "java(handleUserTypeGetStr(entity.getUserType()))"),
            @Mapping(target = "sexStr",expression = "java(handleUserSexGetName(entity.getSex()))"),
            @Mapping(target = "lockedStr",expression = "java(handleUserAccountStateGetInfo(entity.getLocked()))"),
    })
    UserAccountXlsOutModel entityToXlsOutModel(UserAccount entity);

    @Mappings({
            @Mapping(target = "sex",expression = "java(handleUserSexGetValue(xlsInModel.getSexStr()))"),
            @Mapping(target = "userType",expression = "java(handleGetUserAccountDefaultUserType())"),
            @Mapping(target = "userTypeNum",expression = "java(handleGetUserAccountDefaultUserTypeNum())"),
            @Mapping(target = "state",expression = "java(handleGetUserAccountDefaultState())"),
            @Mapping(target = "locked",expression = "java(handleGetUserAccountDefaultLocked())"),
            @Mapping(target = "createTime",expression = "java(handleGetNowDate())"),
            @Mapping(target = "updateTime",expression = "java(handleGetNowDate())"),
            @Mapping(target = "createUserId",expression = "java(handleGetLoginUserId(loginUser,false))"),
            @Mapping(target = "lastModifyerId",expression = "java(handleGetLoginUserId(loginUser,false))"),
    })
    UserAccount xlsInModelToEntity(UserAccountXlsInModel xlsInModel, @Context UserAccount loginUser);
}
