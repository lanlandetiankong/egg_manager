package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.module;


import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.module.DefineModule;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.dto.mysql.module.DefineModuleDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.module.DefineModuleTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import com.egg.manager.persistence.pojo.vo.mysql.module.DefineModuleVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {DefineModuleTransfer.class}
)
public interface DefineModuleVoMapstruct extends MyBaseMysqlVoMapstruct<DefineModule, DefineModuleVo, DefineModuleDto> {
    DefineModuleVoMapstruct INSTANCE = Mappers.getMapper(DefineModuleVoMapstruct.class);


    DefineModule transferVoToEntity(DefineModuleVo vo);

    DefineModuleVo transferEntityToVo(DefineModule entity);

    DefineModuleVo transferDtoToVo(DefineModuleDto dto);
}
