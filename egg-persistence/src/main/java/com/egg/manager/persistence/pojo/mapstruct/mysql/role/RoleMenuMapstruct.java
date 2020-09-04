package com.egg.manager.persistence.pojo.mapstruct.mysql.role;


import com.egg.manager.persistence.db.mysql.entity.role.RoleMenu;
import com.egg.manager.persistence.pojo.dto.mysql.role.RoleMenuDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.role.RoleMenuTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.role.RoleMenuVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {RoleMenuTransfer.class}
)
public interface RoleMenuMapstruct extends MyBaseMysqlMapstruct<RoleMenu,RoleMenuVo, RoleMenuDto> {
    RoleMenuMapstruct INSTANCE = Mappers.getMapper(RoleMenuMapstruct.class);


    @Mappings({})
    RoleMenu transferVoToEntity(RoleMenuVo vo);

    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    RoleMenuVo transferEntityToVo(RoleMenu entity);

    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    RoleMenuVo transferDtoToVo(RoleMenuDto dto);
}
