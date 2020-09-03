package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.define;


import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.define.DefineGroup;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineGroupDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.define.DefineGroupTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineGroupVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {DefineGroupTransfer.class}
)
public interface DefineGroupVoMapstruct extends MyBaseMysqlVoMapstruct<DefineGroup, DefineGroupVo, DefineGroupDto> {
    DefineGroupVoMapstruct INSTANCE = Mappers.getMapper(DefineGroupVoMapstruct.class);


    DefineGroup transferVoToEntity(DefineGroupVo vo);

    DefineGroupVo transferEntityToVo(DefineGroup entity);

    DefineGroupVo transferDtoToVo(DefineGroupDto dto);
}
