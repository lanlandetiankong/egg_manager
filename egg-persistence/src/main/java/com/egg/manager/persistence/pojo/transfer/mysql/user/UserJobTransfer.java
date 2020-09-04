package com.egg.manager.persistence.pojo.transfer.mysql.user;

import com.egg.manager.persistence.db.mysql.entity.user.UserJob;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserJobDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.user.UserJobMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserJobVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("UserJobTransfer")
public class UserJobTransfer extends MyBaseMysqlTransfer {

    static UserJobMapstruct userJobVoMapstruct = UserJobMapstruct.INSTANCE ;

    public static UserJob transferVoToEntity(UserJobVo vo) {
        if (vo == null) {
            return null;
        }
        UserJob entity = userJobVoMapstruct.transferVoToEntity(vo);
        return entity;
    }


    public static UserJobVo transferEntityToVo(UserJob entity) {
        if (entity == null) {
            return null;
        }
        UserJobVo vo = userJobVoMapstruct.transferEntityToVo(entity);
        return vo;
    }


    public static UserJobVo transferDtoToVo(UserJobDto dto) {
        if (dto == null) {
            return null;
        }
        UserJobVo entity = userJobVoMapstruct.transferDtoToVo(dto);
        return entity;
    }

    public static List<UserJobVo> transferEntityToVoList(List<UserJob> userJobs) {
        if (userJobs == null) {
            return null;
        } else {
            List<UserJobVo> list = new ArrayList<>();
            for (UserJob job : userJobs) {
                list.add(transferEntityToVo(job));
            }
            return list;
        }
    }

    public static List<UserJobVo> transferDtoToVoList(List<UserJobDto> userJobDtos) {
        if (userJobDtos == null) {
            return null;
        } else {
            List<UserJobVo> list = new ArrayList<>();
            for (UserJobDto userJobDto : userJobDtos) {
                list.add(transferDtoToVo(userJobDto));
            }
            return list;
        }
    }

}
