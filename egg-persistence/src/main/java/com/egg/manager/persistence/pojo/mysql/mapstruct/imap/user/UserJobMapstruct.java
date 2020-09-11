package com.egg.manager.persistence.pojo.mysql.mapstruct.imap.user;


import com.egg.manager.persistence.db.mysql.entity.user.UserJob;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserJobDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.conversion.user.UserJobConversion;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.baseExtend.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserJobVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {UserJobConversion.class}
)
public interface UserJobMapstruct extends MyBaseMysqlMapstruct<UserJob, UserJobVo, UserJobDto> {
    UserJobMapstruct INSTANCE = Mappers.getMapper(UserJobMapstruct.class);


    @Mappings({})
    UserJob transferVoToEntity(UserJobVo vo);

    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer", ignore = true)
    })
    UserJobVo transferEntityToVo(UserJob entity);

    @Mappings({
            @Mapping(target = "createUser", expression = "java(translateCreateUserEntityToVo(dto.getLastModifyer()))"),
            @Mapping(target = "lastModifyer", expression = "java(translateUpdateUserEntityToVo(dto.getLastModifyer()))")
    })
    UserJobVo transferDtoToVo(UserJobDto dto);
}
