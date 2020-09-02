package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.user;


import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.user.UserGroup;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserGroupDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserGroupTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserGroupVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {UserGroupTransfer.class}
)
public interface UserGroupVoMapstruct extends MyBaseMysqlVoMapstruct<UserGroup,UserGroupVo, UserGroupDto> {
    UserGroupVoMapstruct INSTANCE = Mappers.getMapper(UserGroupVoMapstruct.class);

    UserGroup transferVoToEntity(UserGroupVo vo);

    UserGroupVo transferEntityToVo(UserGroup entity);

    UserGroupVo transferDtoToVo(UserGroupDto dto);
}
