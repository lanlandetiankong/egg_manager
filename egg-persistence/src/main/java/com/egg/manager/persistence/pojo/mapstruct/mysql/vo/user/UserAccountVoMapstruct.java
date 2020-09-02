package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.user;


import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserAccountDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserAccountTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserAccountVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {UserAccountTransfer.class}
)
public interface UserAccountVoMapstruct extends MyBaseMysqlVoMapstruct<UserAccount,UserAccountVo, UserAccountDto> {
    UserAccountVoMapstruct INSTANCE = Mappers.getMapper(UserAccountVoMapstruct.class);

    UserAccount transferVoToEntity(UserAccountVo vo);

    UserAccountVo transferEntityToVo(UserAccount entity);

    UserAccountVo transferDtoToVo(UserAccountDto dto);
}
