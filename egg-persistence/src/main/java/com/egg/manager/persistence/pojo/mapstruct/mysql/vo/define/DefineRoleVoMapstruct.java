package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.define;

import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.define.DefineRole;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineRoleDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.define.DefineRoleTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineRoleVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {DefineRoleTransfer.class}
)
public interface DefineRoleVoMapstruct extends MyBaseMysqlVoMapstruct<DefineRole, DefineRoleVo, DefineRoleDto> {
    DefineRoleVoMapstruct INSTANCE = Mappers.getMapper(DefineRoleVoMapstruct.class);


    DefineRole transferVoToEntity(DefineRoleVo vo);

    DefineRoleVo transferEntityToVo(DefineRole entity);

    DefineRoleVo transferDtoToVo(DefineRoleDto dto);
}
