package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.define;

import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.define.DefineDepartment;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineDepartmentDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.define.DefineDepartmentTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineDepartmentVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {DefineDepartmentTransfer.class}
)
public interface DefineDepartmentVoMapstruct extends MyBaseMysqlVoMapstruct<DefineDepartment, DefineDepartmentVo, DefineDepartmentDto> {
    DefineDepartmentVoMapstruct INSTANCE = Mappers.getMapper(DefineDepartmentVoMapstruct.class);


    DefineDepartment transferVoToEntity(DefineDepartmentVo vo);

    DefineDepartmentVo transferEntityToVo(DefineDepartment entity);

    DefineDepartmentVo transferDtoToVo(DefineDepartmentDto dto);


}
