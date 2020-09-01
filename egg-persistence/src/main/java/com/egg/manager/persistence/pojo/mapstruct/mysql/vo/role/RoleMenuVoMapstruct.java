package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.role;


import com.egg.manager.persistence.db.mysql.entity.role.RoleMenu;
import com.egg.manager.persistence.pojo.dto.mysql.role.RoleMenuDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.vo.mysql.role.RoleMenuVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMenuVoMapstruct extends MyBaseMysqlVoMapstruct<RoleMenu,RoleMenuVo, RoleMenuDto> {
    RoleMenuVoMapstruct INSTANCE = Mappers.getMapper(RoleMenuVoMapstruct.class);

}
