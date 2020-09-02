package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.user;


import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.user.UserRole;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserRoleDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserRoleTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserJobVo;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserRoleVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {UserRoleTransfer.class}
)
public interface UserRoleVoMapstruct extends MyBaseMysqlVoMapstruct<UserRole, UserRoleVo, UserRoleDto> {
    UserRoleVoMapstruct INSTANCE = Mappers.getMapper(UserRoleVoMapstruct.class);


    UserRole transferVoToEntity(UserRoleVo vo);

    UserRoleVo transferEntityToVo(UserRole entity);

    UserRoleVo transferDtoToVo(UserRoleDto dto);

}
