package com.egg.manager.persistence.em.user.pojo.transfer;

import com.egg.manager.persistence.em.user.db.mysql.entity.UserJobEntity;
import com.egg.manager.persistence.em.user.pojo.dto.UserJobDto;
import com.egg.manager.persistence.em.user.pojo.mapstruct.imap.UserJobMapstruct;
import com.egg.manager.persistence.em.user.pojo.vo.UserJobVo;
import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Component
@Named("userJobTransfer")
public class UserJobTransfer extends BaseMysqlTransfer {

    static UserJobMapstruct userJobMapstruct = UserJobMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static UserJobEntity transferVoToEntity(UserJobVo vo) {
        if (vo == null) {
            return null;
        }
        UserJobEntity entity = userJobMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static UserJobVo transferEntityToVo(UserJobEntity entity) {
        if (entity == null) {
            return null;
        }
        UserJobVo vo = userJobMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static UserJobVo transferDtoToVo(UserJobDto dto) {
        if (dto == null) {
            return null;
        }
        UserJobVo entity = userJobMapstruct.transferDtoToVo(dto);
        return entity;
    }

    public static List<UserJobVo> transferEntityToVoList(List<UserJobEntity> userJobEntities) {
        if (userJobEntities == null) {
            return null;
        } else {
            List<UserJobVo> list = new ArrayList<>();
            for (UserJobEntity job : userJobEntities) {
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
