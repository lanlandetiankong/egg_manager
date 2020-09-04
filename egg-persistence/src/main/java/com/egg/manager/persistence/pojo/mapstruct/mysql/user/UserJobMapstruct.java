package com.egg.manager.persistence.pojo.mapstruct.mysql.user;


import com.egg.manager.persistence.db.mysql.entity.user.UserJob;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserJobDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.MyBaseMysqlMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserJobTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserJobVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {UserJobTransfer.class}
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
