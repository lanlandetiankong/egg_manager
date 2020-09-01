package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.define;

import com.egg.manager.persistence.db.mysql.entity.define.DefineRole;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineRoleDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineRoleVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DefineRoleVoMapstruct extends MyBaseMysqlVoMapstruct<DefineRole, DefineRoleVo, DefineRoleDto> {
    DefineRoleVoMapstruct INSTANCE = Mappers.getMapper(DefineRoleVoMapstruct.class);

}
