package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.define;


import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.define.DefineJob;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineJobDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.define.DefineJobTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineJobVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {DefineJobTransfer.class}
)
public interface DefineJobVoMapstruct extends MyBaseMysqlVoMapstruct<DefineJob, DefineJobVo, DefineJobDto> {
    DefineJobVoMapstruct INSTANCE = Mappers.getMapper(DefineJobVoMapstruct.class);


    DefineJob transferVoToEntity(DefineJobVo vo);

    DefineJobVo transferEntityToVo(DefineJob entity);

    DefineJobVo transferDtoToVo(DefineJobDto dto);
}
