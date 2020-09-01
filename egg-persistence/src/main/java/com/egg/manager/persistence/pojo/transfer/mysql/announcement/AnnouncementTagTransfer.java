package com.egg.manager.persistence.pojo.transfer.mysql.announcement;

import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagDto;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserAccountTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("AnnouncementTagTransfer")
public class AnnouncementTagTransfer extends MyBaseMysqlTransfer {


    @Deprecated
    public static AnnouncementTag transferVoToEntity(AnnouncementTagVo announcementTagVo) {
        if (announcementTagVo == null) {
            return null;
        }
        AnnouncementTag announcementTag = new AnnouncementTag();
        announcementTag.setFid(announcementTagVo.getFid());
        announcementTag.setName(announcementTagVo.getName());
        announcementTag.setDescription(announcementTagVo.getDescription());
        announcementTag.setOrdering(announcementTagVo.getOrdering());

        announcementTag.setRemark(announcementTagVo.getRemark());
        announcementTag.setState(announcementTagVo.getState());
        announcementTag.setCreateTime(announcementTagVo.getCreateTime());
        announcementTag.setUpdateTime(announcementTagVo.getUpdateTime());
        announcementTag.setCreateUserId(announcementTagVo.getCreateUserId());
        announcementTag.setLastModifyerId(announcementTagVo.getLastModifyerId());
        return announcementTag;
    }
    @Deprecated
    public static AnnouncementTagVo transferEntityToVo(AnnouncementTag announcementTag) {
        if (announcementTag == null) {
            return null;
        }
        AnnouncementTagVo announcementTagVo = new AnnouncementTagVo();
        announcementTagVo.setFid(announcementTag.getFid());
        announcementTagVo.setName(announcementTag.getName());
        announcementTagVo.setDescription(announcementTag.getDescription());
        announcementTagVo.setOrdering(announcementTag.getOrdering());

        announcementTagVo.setRemark(announcementTag.getRemark());
        announcementTagVo.setState(announcementTag.getState());
        announcementTagVo.setCreateTime(announcementTag.getCreateTime());
        announcementTagVo.setUpdateTime(announcementTag.getUpdateTime());
        announcementTagVo.setCreateUserId(announcementTag.getCreateUserId());
        announcementTagVo.setLastModifyerId(announcementTag.getLastModifyerId());
        return announcementTagVo;
    }

    public static AnnouncementTagVo transferDtoToVo(AnnouncementTagDto announcementTagDto) {
        if (announcementTagDto == null) {
            return null;
        }
        AnnouncementTagVo announcementTagVo = new AnnouncementTagVo();
        announcementTagVo.setFid(announcementTagDto.getFid());
        announcementTagVo.setName(announcementTagDto.getName());
        announcementTagVo.setDescription(announcementTagDto.getDescription());
        announcementTagVo.setOrdering(announcementTagDto.getOrdering());

        announcementTagVo.setRemark(announcementTagDto.getRemark());
        announcementTagVo.setState(announcementTagDto.getState());
        announcementTagVo.setCreateTime(announcementTagDto.getCreateTime());
        announcementTagVo.setUpdateTime(announcementTagDto.getUpdateTime());
        announcementTagVo.setCreateUserId(announcementTagDto.getCreateUserId());
        announcementTagVo.setLastModifyerId(announcementTagDto.getLastModifyerId());
        /**
         * TODO
         */
        announcementTagVo.setCreateUser(UserAccountTransfer.transferEntityToVo(announcementTagDto.getCreateUser()));
        announcementTagVo.setLastModifyer(UserAccountTransfer.transferEntityToVo(announcementTagDto.getLastModifyer()));
        return announcementTagVo;
    }

    public static List<AnnouncementTagVo> transferEntityToVoList(List<AnnouncementTag> announcementTags) {
        if (announcementTags == null) {
            return null;
        } else {
            List<AnnouncementTagVo> list = new ArrayList<>();
            for (AnnouncementTag announcementTag : announcementTags) {
                list.add(transferEntityToVo(announcementTag));
            }
            return list;
        }
    }

    public static List<AnnouncementTagVo> transferDtoToVoList(List<AnnouncementTagDto> announcementTagDtos) {
        if (announcementTagDtos == null) {
            return null;
        } else {
            List<AnnouncementTagVo> list = new ArrayList<>();
            for (AnnouncementTagDto announcementTagDto : announcementTagDtos) {
                list.add(transferDtoToVo(announcementTagDto));
            }
            return list;
        }
    }

}
