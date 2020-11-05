package com.egg.manager.persistence.em.define.pojo.mapstruct.imap;


import com.egg.manager.persistence.em.define.db.mysql.entity.DefineGroup;
import com.egg.manager.persistence.em.define.pojo.dto.DefineGroupDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.conversion.DefineGroupConversion;
import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.em.define.pojo.vo.DefineGroupVo;
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
        uses = {DefineGroupConversion.class}
)
public interface DefineGroupMapstruct extends MyBaseMysqlMapstruct<DefineGroup, DefineGroupVo, DefineGroupDto> {
    DefineGroupMapstruct INSTANCE = Mappers.getMapper(DefineGroupMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    DefineGroup transferVoToEntity(DefineGroupVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    DefineGroupVo transferEntityToVo(DefineGroup entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    DefineGroupVo transferDtoToVo(DefineGroupDto dto);
}
