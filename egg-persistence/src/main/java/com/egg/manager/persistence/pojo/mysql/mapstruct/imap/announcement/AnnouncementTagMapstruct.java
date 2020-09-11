package com.egg.manager.persistence.pojo.mysql.mapstruct.imap.announcement;

import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.pojo.mysql.dto.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.conversion.announcement.AnnouncementTagConversion;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.baseExtend.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.mysql.vo.announcement.AnnouncementTagVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {AnnouncementTagConversion.class}
)
public interface AnnouncementTagMapstruct extends MyBaseMysqlMapstruct<AnnouncementTag, AnnouncementTagVo, AnnouncementTagDto> {
    AnnouncementTagMapstruct INSTANCE = Mappers.getMapper(AnnouncementTagMapstruct.class);


    @Mappings({})
    AnnouncementTag transferVoToEntity(AnnouncementTagVo vo);

    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    AnnouncementTagVo transferEntityToVo(AnnouncementTag entity);

    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    AnnouncementTagVo transferDtoToVo(AnnouncementTagDto dto);

}
