package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.define;


import com.egg.manager.persistence.db.mysql.entity.define.DefineJob;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineJobDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineJobVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DefineJobVoMapstruct extends MyBaseMysqlVoMapstruct<DefineJob, DefineJobVo, DefineJobDto> {
    DefineJobVoMapstruct INSTANCE = Mappers.getMapper(DefineJobVoMapstruct.class);

}
