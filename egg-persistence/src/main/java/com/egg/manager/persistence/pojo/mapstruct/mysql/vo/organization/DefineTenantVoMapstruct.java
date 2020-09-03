package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.organization;


import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.dto.mysql.organization.DefineTenantDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.organization.DefineTenantTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import com.egg.manager.persistence.pojo.vo.mysql.organization.DefineTenantVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {DefineTenantTransfer.class}
)
public interface DefineTenantVoMapstruct extends MyBaseMysqlVoMapstruct<DefineTenant, DefineTenantVo, DefineTenantDto> {

    DefineTenantVoMapstruct INSTANCE = Mappers.getMapper(DefineTenantVoMapstruct.class);

    DefineTenant transferVoToEntity(DefineTenantVo vo);

    DefineTenantVo transferEntityToVo(DefineTenant entity);

    DefineTenantVo transferDtoToVo(DefineTenantDto dto);
}
