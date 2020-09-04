package com.egg.manager.persistence.pojo.mapstruct.mysql.define;


import com.egg.manager.persistence.db.mysql.entity.define.DefineJob;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineJobDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.define.DefineJobTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineJobVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {DefineJobTransfer.class}
)
public interface DefineJobMapstruct extends MyBaseMysqlMapstruct<DefineJob, DefineJobVo, DefineJobDto> {
    DefineJobMapstruct INSTANCE = Mappers.getMapper(DefineJobMapstruct.class);


    @Mappings({})
    DefineJob transferVoToEntity(DefineJobVo vo);

    @Mappings({
            @Mapping(target = "typeStr",source = "type",qualifiedByName = "handleDefineJobTypeGetGetLabel"),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    DefineJobVo transferEntityToVo(DefineJob entity);

    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    DefineJobVo transferDtoToVo(DefineJobDto dto);








}
