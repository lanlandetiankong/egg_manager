package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.define;


import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.db.mysql.entity.define.DefineMenu;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineMenuDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.announcement.AnnouncementTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.define.DefineMenuTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineMenuVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {DefineMenuTransfer.class}
)
public interface DefineMenuVoMapstruct extends MyBaseMysqlVoMapstruct<DefineMenu,DefineMenuVo, DefineMenuDto> {
    DefineMenuVoMapstruct INSTANCE = Mappers.getMapper(DefineMenuVoMapstruct.class);


    DefineMenu transferVoToEntity(DefineMenuVo vo);

    DefineMenuVo transferEntityToVo(DefineMenu entity);

    DefineMenuVo transferDtoToVo(DefineMenuDto dto);
}
