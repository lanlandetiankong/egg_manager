package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.define;


import com.egg.manager.persistence.db.mysql.entity.define.DefineMenu;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineMenuDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineMenuVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DefineMenuVoMapstruct extends MyBaseMysqlVoMapstruct<DefineMenu,DefineMenuVo, DefineMenuDto> {
    DefineMenuVoMapstruct INSTANCE = Mappers.getMapper(DefineMenuVoMapstruct.class);

}
