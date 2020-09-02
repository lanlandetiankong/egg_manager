package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.role;


import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.role.RoleMenu;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.dto.mysql.role.RoleMenuDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.role.RoleMenuTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import com.egg.manager.persistence.pojo.vo.mysql.role.RoleMenuVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {RoleMenuTransfer.class}
)
public interface RoleMenuVoMapstruct extends MyBaseMysqlVoMapstruct<RoleMenu,RoleMenuVo, RoleMenuDto> {
    RoleMenuVoMapstruct INSTANCE = Mappers.getMapper(RoleMenuVoMapstruct.class);


    RoleMenu transferVoToEntity(RoleMenuVo vo);

    RoleMenuVo transferEntityToVo(RoleMenu entity);

    RoleMenuVo transferDtoToVo(RoleMenuDto dto);
}
