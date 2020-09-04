package com.egg.manager.persistence.pojo.mapstruct.mysql.define;


import com.egg.manager.persistence.db.mysql.entity.define.DefineGroup;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineGroupDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.define.DefineGroupTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineGroupVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {DefineGroupTransfer.class}
)
public interface DefineGroupMapstruct extends MyBaseMysqlMapstruct<DefineGroup, DefineGroupVo, DefineGroupDto> {
    DefineGroupMapstruct INSTANCE = Mappers.getMapper(DefineGroupMapstruct.class);


    @Mappings({})
    DefineGroup transferVoToEntity(DefineGroupVo vo);

    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    DefineGroupVo transferEntityToVo(DefineGroup entity);

    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    DefineGroupVo transferDtoToVo(DefineGroupDto dto);
}
