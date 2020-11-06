package com.egg.manager.persistence.em.user.pojo.mapstruct.imap;


import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.user.pojo.excel.export.user.UserAccountXlsOutModel;
import com.egg.manager.persistence.em.user.pojo.excel.introduce.user.UserAccountXlsInModel;
import com.egg.manager.persistence.em.user.pojo.dto.UserAccountDto;
import com.egg.manager.persistence.em.user.pojo.mapstruct.conversion.UserAccountConversion;
import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.em.user.pojo.vo.UserAccountVo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {UserAccountConversion.class}
)
public interface UserAccountMapstruct extends MyBaseMysqlMapstruct<UserAccount, UserAccountVo, UserAccountDto> {

    UserAccountMapstruct INSTANCE = Mappers.getMapper(UserAccountMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    UserAccount transferVoToEntity(UserAccountVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "userTypeStr", expression = "java(handleUserTypeGetStr(entity.getUserType()))"),
            @Mapping(target = "uploadImgBean", ignore = true),
            @Mapping(target = "belongTenantId", ignore = true),
            @Mapping(target = "belongTenant", ignore = true),
            @Mapping(target = "belongDepartmentId", ignore = true),
            @Mapping(target = "belongDepartment", ignore = true),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    UserAccountVo transferEntityToVo(UserAccount entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "userTypeStr", expression = "java(handleUserTypeGetStr(dto.getUserType()))"),
            @Mapping(target = "belongTenant", expression = "java(commonTranslateDefineTenantDtoToVo(dto.getBelongTenant()))"),
            @Mapping(target = "belongDepartment", expression = "java(commonTranslateDefineDepartmentDtoToVo(dto.getBelongDepartment()))"),
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    UserAccountVo transferDtoToVo(UserAccountDto dto);

    /**
     * entity转excel导出模型类
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "userTypeStr", expression = "java(handleUserTypeGetStr(entity.getUserType()))"),
            @Mapping(target = "sexStr", expression = "java(handleUserSexGetName(entity.getSex()))"),
            @Mapping(target = "lockedStr", expression = "java(handleUserAccountStateGetInfo(entity.getLocked()))"),
    })
    UserAccountXlsOutModel entityToXlsOutModel(UserAccount entity);

    /**
     * excel导入模型类转entity
     * @param xlsInModel
     * @param loginUser
     * @return
     */
    @Mappings({
            @Mapping(target = "sex", expression = "java(handleUserSexGetValue(xlsInModel.getSexStr()))"),
            @Mapping(target = "userType", expression = "java(handleGetUserAccountDefaultUserType())"),
            @Mapping(target = "userTypeNum", expression = "java(handleGetUserAccountDefaultUserTypeNum())"),
            @Mapping(target = "state", expression = "java(handleGetUserAccountDefaultState())"),
            @Mapping(target = "locked", expression = "java(handleGetUserAccountDefaultLocked())"),
            @Mapping(target = "createTime", expression = "java(handleGetNowDate())"),
            @Mapping(target = "updateTime", expression = "java(handleGetNowDate())"),
            @Mapping(target = "createUserId", expression = "java(handleGetLoginUserId(loginUser,false))"),
            @Mapping(target = "lastModifyerId", expression = "java(handleGetLoginUserId(loginUser,false))"),
            @Mapping(target = "version", ignore = true),
    })
    UserAccount xlsInModelToEntity(UserAccountXlsInModel xlsInModel, @Context UserAccount loginUser);
}
