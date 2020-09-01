package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.user;


import com.egg.manager.persistence.db.mysql.entity.user.UserJob;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserJobDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserJobVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserJobVoMapstruct extends MyBaseMysqlVoMapstruct<UserJob, UserJobVo, UserJobDto> {
    UserJobVoMapstruct INSTANCE = Mappers.getMapper(UserJobVoMapstruct.class);



}
