package com.egg.manager.persistence.obl.user.pojo.mapstruct.imap;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.obl.user.db.mysql.entity.OblUserAccountEntity;
import com.egg.manager.persistence.obl.user.pojo.dto.OblUserAccountDto;
import com.egg.manager.persistence.obl.user.pojo.mapstruct.conversion.OblUserAccountConversion;
import com.egg.manager.persistence.obl.user.pojo.vo.OblUserAccountVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description
 * @date 2020-12-07
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {OblUserAccountConversion.class}
)
public interface OblUserAccountMapstruct extends MyBaseMysqlMapstruct<OblUserAccountEntity, OblUserAccountVo, OblUserAccountDto> {
    OblUserAccountMapstruct INSTANCE = Mappers.getMapper(OblUserAccountMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    OblUserAccountEntity transferVoToEntity(OblUserAccountVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    OblUserAccountVo transferEntityToVo(OblUserAccountEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    OblUserAccountVo transferDtoToVo(OblUserAccountDto dto);


}