package com.egg.manager.persistence.em.define.pojo.mapstruct.imap;


import com.egg.manager.persistence.em.define.db.mysql.entity.DefineMenu;
import com.egg.manager.persistence.em.define.pojo.dto.DefineMenuDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.conversion.DefineMenuConversion;
import com.egg.manager.persistence.expand.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.em.define.pojo.vo.DefineMenuVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {DefineMenuConversion.class}
)
public interface DefineMenuMapstruct extends MyBaseMysqlMapstruct<DefineMenu, DefineMenuVo, DefineMenuDto> {
    DefineMenuMapstruct INSTANCE = Mappers.getMapper(DefineMenuMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    DefineMenu transferVoToEntity(DefineMenuVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "urlJumpTypeStr", expression = "java(handleDefineMenuUrlJumpTypeGetLabel(entity.getUrlJumpType()))"),
            @Mapping(target = "parentMenu", ignore = true),
            @Mapping(target = "uploadExcelBeanList", ignore = true),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    DefineMenuVo transferEntityToVo(DefineMenu entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "urlJumpTypeStr", expression = "java(handleDefineMenuUrlJumpTypeGetLabel(dto.getUrlJumpType()))"),
            @Mapping(target = "parentMenu", expression = "java(transferDtoToVo(dto.getParentMenuDto()))"),
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    DefineMenuVo transferDtoToVo(DefineMenuDto dto);
}
