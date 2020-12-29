package com.egg.manager.facade.persistence.em.announcement.pojo.mapstruct.imap;

import com.egg.manager.facade.persistence.em.announcement.db.mysql.entity.EmAnnouncementTagEntity;
import com.egg.manager.facade.persistence.em.announcement.pojo.dto.EmAnnouncementTagDto;
import com.egg.manager.facade.persistence.em.announcement.pojo.mapstruct.conversion.EmAnnouncementTagConversion;
import com.egg.manager.facade.persistence.em.announcement.pojo.vo.EmAnnouncementTagVo;
import com.egg.manager.facade.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental.MyBaseMysqlMapstruct;
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
        uses = {EmAnnouncementTagConversion.class}
)
public interface EmAnnouncementTagMapstruct extends MyBaseMysqlMapstruct<EmAnnouncementTagEntity, EmAnnouncementTagVo, EmAnnouncementTagDto> {
    EmAnnouncementTagMapstruct INSTANCE = Mappers.getMapper(EmAnnouncementTagMapstruct.class);

    /**
     * vo转entity
     * @param vo
     * @return
     */
    @Mappings({})
    EmAnnouncementTagEntity transferVoToEntity(EmAnnouncementTagVo vo);

    /**
     * entity转vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    EmAnnouncementTagVo transferEntityToVo(EmAnnouncementTagEntity entity);

    /**
     * dto转vo
     * @param dto
     * @return
     */
    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    EmAnnouncementTagVo transferDtoToVo(EmAnnouncementTagDto dto);

}
