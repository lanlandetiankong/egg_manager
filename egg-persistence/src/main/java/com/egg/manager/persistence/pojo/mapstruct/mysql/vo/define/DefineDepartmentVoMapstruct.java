package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.define;

import com.egg.manager.persistence.db.mysql.entity.define.DefineDepartment;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineDepartmentDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineDepartmentVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DefineDepartmentVoMapstruct extends MyBaseMysqlVoMapstruct<DefineDepartment, DefineDepartmentVo, DefineDepartmentDto> {
    DefineDepartmentVoMapstruct INSTANCE = Mappers.getMapper(DefineDepartmentVoMapstruct.class);

}
