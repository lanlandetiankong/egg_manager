package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.user;


import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.user.UserTenant;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserTenantDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserTenantTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserTenantVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {UserTenantTransfer.class}
)
public interface UserTenantVoMapstruct extends MyBaseMysqlVoMapstruct<UserTenant, UserTenantVo, UserTenantDto> {
    UserTenantVoMapstruct INSTANCE = Mappers.getMapper(UserTenantVoMapstruct.class);

    UserTenant transferVoToEntity(UserTenantVo vo);

    UserTenantVo transferEntityToVo(UserTenant entity);

    UserTenantVo transferDtoToVo(UserTenantDto dto);
}
