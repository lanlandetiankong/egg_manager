package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.define;


import com.egg.manager.persistence.db.mysql.entity.define.DefinePermission;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefinePermissionDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefinePermissionVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DefinePermissionVoMapstruct extends MyBaseMysqlVoMapstruct<DefinePermission, DefinePermissionVo, DefinePermissionDto> {
    DefinePermissionVoMapstruct INSTANCE = Mappers.getMapper(DefinePermissionVoMapstruct.class);

}
