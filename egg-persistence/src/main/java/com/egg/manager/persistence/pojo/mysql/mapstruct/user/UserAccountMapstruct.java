package com.egg.manager.persistence.pojo.mysql.mapstruct.user;


import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.common.excel.export.user.UserAccountXlsOutModel;
import com.egg.manager.persistence.pojo.common.excel.introduce.user.UserAccountXlsInModel;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserAccountDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.baseExtend.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.user.UserAccountTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserAccountVo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {UserAccountTransfer.class}
)
public interface UserAccountMapstruct extends MyBaseMysqlMapstruct<UserAccount,UserAccountVo, UserAccountDto> {

    UserAccountMapstruct INSTANCE = Mappers.getMapper(UserAccountMapstruct.class);

    @Mappings({})
    UserAccount transferVoToEntity(UserAccountVo vo);

    @Mappings({
            @Mapping(target = "userTypeStr",expression = "java(handleUserTypeGetStr(entity.getUserType()))"),
            @Mapping(target = "uploadImgBean", ignore = true),
            @Mapping(target = "belongTenantId", ignore = true),
            @Mapping(target = "belongTenant", ignore = true),
            @Mapping(target = "belongDepartmentId", ignore = true),
            @Mapping(target = "belongDepartment", ignore = true),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    UserAccountVo transferEntityToVo(UserAccount entity);
    @Mappings({
            @Mapping(target = "userTypeStr",expression = "java(handleUserTypeGetStr(dto.getUserType()))"),
            @Mapping(target = "belongTenant",expression = "java(commonTranslateDefineTenantEntityToVo(dto.getBelongTenant()))"),
            @Mapping(target = "belongDepartment",expression = "java(commonTranslateDefineTenantDtoToVo(dto.getBelongDepartment()))"),
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
