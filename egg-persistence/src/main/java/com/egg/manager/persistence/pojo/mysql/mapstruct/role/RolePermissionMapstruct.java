package com.egg.manager.persistence.pojo.mysql.mapstruct.role;


import com.egg.manager.persistence.db.mysql.entity.role.RolePermission;
import com.egg.manager.persistence.pojo.mysql.dto.role.RolePermissionDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.role.RolePermissionTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.role.RolePermissionVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {RolePermissionTransfer.class}
)
public interface RolePermissionMapstruct extends MyBaseMysqlMapstruct<RolePermission, RolePermissionVo, RolePermissionDto> {

    RolePermissionMapstruct INSTANCE = Mappers.getMapper(RolePermissionMapstruct.class);


    @Mappings({})
    RolePermission transferVoToEntity(RolePermissionVo vo);

    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    RolePermissionVo transferEntityToVo(RolePermission entity);

    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    RolePermissionVo transferDtoToVo(RolePermissionDto dto);
}
