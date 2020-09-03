package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.define;


import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.define.DefinePermission;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefinePermissionDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.define.DefinePermissionTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefinePermissionVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {DefinePermissionTransfer.class}
)
public interface DefinePermissionVoMapstruct extends MyBaseMysqlVoMapstruct<DefinePermission, DefinePermissionVo, DefinePermissionDto> {
    DefinePermissionVoMapstruct INSTANCE = Mappers.getMapper(DefinePermissionVoMapstruct.class);

    @Mappings({
            @Mapping(target = "ensure",expression = "java(handleSwitchStateGetShort(vo.getEnsure()))")
    })
    DefinePermission transferVoToEntity(DefinePermissionVo vo);
    @Mappings({
            @Mapping(target = "ensure",expression = "java(handleSwitchStateGetBoolean(entity.getEnsure()))"),
            @Mapping(target = "typeStr",source = "type",qualifiedByName = "doGetLabelOfDefinePermissionTypeEnum"),
    })
    DefinePermissionVo transferEntityToVo(DefinePermission entity);

    DefinePermissionVo transferDtoToVo(DefinePermissionDto dto);
}
