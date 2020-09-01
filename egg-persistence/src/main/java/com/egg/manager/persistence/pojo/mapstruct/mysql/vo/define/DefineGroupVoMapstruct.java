package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.define;


import com.egg.manager.persistence.db.mysql.entity.define.DefineGroup;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineGroupDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineGroupVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DefineGroupVoMapstruct extends MyBaseMysqlVoMapstruct<DefineGroup, DefineGroupVo, DefineGroupDto> {
    DefineGroupVoMapstruct INSTANCE = Mappers.getMapper(DefineGroupVoMapstruct.class);

}
