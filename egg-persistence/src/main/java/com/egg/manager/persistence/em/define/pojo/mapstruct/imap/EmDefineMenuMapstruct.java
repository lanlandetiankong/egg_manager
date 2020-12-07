package com.egg.manager.persistence.em.define.pojo.mapstruct.imap;


import com.egg.manager.persistence.em.define.db.mysql.entity.EmDefineMenuEntity;
import com.egg.manager.persistence.em.define.pojo.dto.EmDefineMenuDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.conversion.EmDefineMenuConversion;
import com.egg.manager.persistence.em.define.pojo.vo.EmDefineMenuVo;
import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
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
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {EmDefineMenuConversion.class}
)
public interface EmDefineMenuMapstruct extends MyBaseMysqlMapstruct<EmDefineMenuEntity, EmDefineMenuVo, EmDefineMenuDto> {
    EmDefineMenuMapstruct INSTANCE = Mappers.getMapper(EmDefineMenuMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    EmDefineMenuEntity transferVoToEntity(EmDefineMenuVo vo);

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
    EmDefineMenuVo transferEntityToVo(EmDefineMenuEntity entity);

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
    EmDefineMenuVo transferDtoToVo(EmDefineMenuDto dto);
}
