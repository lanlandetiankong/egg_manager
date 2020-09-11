package com.egg.manager.persistence.pojo.mysql.mapstruct.imap.define;


import com.egg.manager.persistence.db.mysql.entity.define.DefineMenu;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineMenuDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.conversion.define.DefineMenuConversion;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.baseExtend.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineMenuVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {DefineMenuConversion.class}
)
public interface DefineMenuMapstruct extends MyBaseMysqlMapstruct<DefineMenu,DefineMenuVo, DefineMenuDto> {
    DefineMenuMapstruct INSTANCE = Mappers.getMapper(DefineMenuMapstruct.class);


    @Mappings({})
    DefineMenu transferVoToEntity(DefineMenuVo vo);

    @Mappings({
            @Mapping(target = "urlJumpTypeStr",expression = "java(handleDefineMenuUrlJumpTypeGetLabel(entity.getUrlJumpType()))"),
            @Mapping(target = "parentMenu",ignore = true),
            @Mapping(target = "uploadExcelBeanList",ignore = true),
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    DefineMenuVo transferEntityToVo(DefineMenu entity);

    @Mappings({
            @Mapping(target = "urlJumpTypeStr",expression = "java(handleDefineMenuUrlJumpTypeGetLabel(dto.getUrlJumpType()))"),
            @Mapping(target = "parentMenu",expression = "java(transferDtoToVo(dto.getParentMenuDto()))"),
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    DefineMenuVo transferDtoToVo(DefineMenuDto dto);
}
