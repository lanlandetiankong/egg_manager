package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.user;


import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.user.UserJob;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserJobDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserJobTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserJobVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {UserJobTransfer.class}
)
public interface UserJobVoMapstruct extends MyBaseMysqlVoMapstruct<UserJob, UserJobVo, UserJobDto> {
    UserJobVoMapstruct INSTANCE = Mappers.getMapper(UserJobVoMapstruct.class);


    UserJob transferVoToEntity(UserJobVo vo);

    UserJobVo transferEntityToVo(UserJob entity);

    UserJobVo transferDtoToVo(UserJobDto dto);
}
