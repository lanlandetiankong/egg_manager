package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.role;


import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.role.RolePermission;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.dto.mysql.role.RolePermissionDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.role.RolePermissionTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import com.egg.manager.persistence.pojo.vo.mysql.role.RolePermissionVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {RolePermissionTransfer.class}
)
public interface RolePermissionVoMapstruct extends MyBaseMysqlVoMapstruct<RolePermission, RolePermissionVo, RolePermissionDto> {

    RolePermissionVoMapstruct INSTANCE = Mappers.getMapper(RolePermissionVoMapstruct.class);


    RolePermission transferVoToEntity(RolePermissionVo vo);

    RolePermissionVo transferEntityToVo(RolePermission entity);

    RolePermissionVo transferDtoToVo(RolePermissionDto dto);
}
