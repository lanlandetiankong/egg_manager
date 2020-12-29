package com.egg.manager.facade.persistence.em.user.pojo.mapstruct.imap;


import com.egg.manager.facade.persistence.commons.base.query.FieldConst;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.facade.persistence.em.user.pojo.dto.EmUserAccountDto;
import com.egg.manager.facade.persistence.em.user.pojo.excel.export.user.EmUserAccountXlsOutModel;
import com.egg.manager.facade.persistence.em.user.pojo.excel.introduce.user.EmUserAccountXlsInModel;
import com.egg.manager.facade.persistence.em.user.pojo.mapstruct.conversion.EmUserAccountConversion;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmUserAccountVo;
import com.egg.manager.facade.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {EmUserAccountConversion.class}
)
public interface EmUserAccountMapstruct extends MyBaseMysqlMapstruct<EmUserAccountEntity, EmUserAccountVo, EmUserAccountDto> {

    EmUserAccountMapstruct INSTANCE = Mappers.getMapper(EmUserAccountMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({
            @Mapping(target = "salt", ignore = true)
    })
    EmUserAccountEntity transferVoToEntity(EmUserAccountVo vo);

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
    EmUserAccountVo transferEntityToVo(EmUserAccountEntity entity);

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
    EmUserAccountVo transferDtoToVo(EmUserAccountDto dto);

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
    EmUserAccountXlsOutModel entityToXlsOutModel(EmUserAccountEntity entity);

    /**
     * excel导入模型类转entity
     * @param xlsInModel
     * @param loginUser
     * @return
     */
    @Mappings({
            @Mapping(target = "sex", expression = "java(handleUserSexGetValue(xlsInModel.getSexStr()))"),
            @Mapping(target = "userType", expression = "java(handleGetUserAccountDefaultUserType())"),
            @Mapping(target = FieldConst.FIELD_STATE, expression = "java(handleGetUserAccountDefaultState())"),
            @Mapping(target = "locked", expression = "java(handleGetUserAccountDefaultLocked())"),
            @Mapping(target = "createTime", expression = "java(handleGetNowDate())"),
            @Mapping(target = "updateTime", expression = "java(handleGetNowDate())"),
            @Mapping(target = "createUserId", expression = "java(handleGetLoginUserId(loginUser,false))"),
            @Mapping(target = "lastModifyerId", expression = "java(handleGetLoginUserId(loginUser,false))"),
            @Mapping(target = FieldConst.COL_VERSION, ignore = true),
            @Mapping(target = "salt", ignore = true)
    })
    EmUserAccountEntity xlsInModelToEntity(EmUserAccountXlsInModel xlsInModel, @Context EmUserAccountEntity loginUser);
}
