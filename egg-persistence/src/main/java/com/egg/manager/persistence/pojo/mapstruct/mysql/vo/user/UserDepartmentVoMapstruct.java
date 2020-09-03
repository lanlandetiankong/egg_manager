package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.user;


import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.user.UserDepartment;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserDepartmentDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserDepartmentTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserDepartmentVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {UserDepartmentTransfer.class}
)
public interface UserDepartmentVoMapstruct extends MyBaseMysqlVoMapstruct<UserDepartment, UserDepartmentVo, UserDepartmentDto> {
    UserDepartmentVoMapstruct INSTANCE = Mappers.getMapper(UserDepartmentVoMapstruct.class);

    UserDepartment transferVoToEntity(UserDepartmentVo vo);

    UserDepartmentVo transferEntityToVo(UserDepartment entity);

    UserDepartmentVo transferDtoToVo(UserDepartmentDto dto);
}
