package com.egg.manager.persistence.pojo.transfer.mysql.announcement;

import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementTagMysqlDto;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserAccountMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementTagMysqlVo;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementTagMysqlTransfer extends MyBaseMysqlTransfer {


    public static AnnouncementTag transferVoToEntity(AnnouncementTagMysqlVo announcementTagVo) {
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

    public static AnnouncementTagMysqlVo transferEntityToVo(AnnouncementTag announcementTag) {
        if (announcementTag == null) {
            return null;
        }
        AnnouncementTagMysqlVo announcementTagVo = new AnnouncementTagMysqlVo();
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

    public static AnnouncementTagMysqlVo transferDtoToVo(AnnouncementTagMysqlDto announcementTagDto) {
        if (announcementTagDto == null) {
            return null;
        }
        AnnouncementTagMysqlVo announcementTagVo = new AnnouncementTagMysqlVo();
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
        announcementTagVo.setCreateUser(UserAccountMysqlTransfer.transferEntityToVo(announcementTagDto.getCreateUser()));
        announcementTagVo.setLastModifyer(UserAccountMysqlTransfer.transferEntityToVo(announcementTagDto.getLastModifyer()));
        return announcementTagVo;
    }

    public static List<AnnouncementTagMysqlVo> transferEntityToVoList(List<AnnouncementTag> announcementTags) {
        if (announcementTags == null) {
            return null;
        } else {
            List<AnnouncementTagMysqlVo> list = new ArrayList<>();
            for (AnnouncementTag announcementTag : announcementTags) {
                list.add(transferEntityToVo(announcementTag));
            }
            return list;
        }
    }

    public static List<AnnouncementTagMysqlVo> transferDtoToVoList(List<AnnouncementTagMysqlDto> announcementTagDtos) {
        if (announcementTagDtos == null) {
            return null;
        } else {
            List<AnnouncementTagMysqlVo> list = new ArrayList<>();
            for (AnnouncementTagMysqlDto announcementTagDto : announcementTagDtos) {
                list.add(transferDtoToVo(announcementTagDto));
            }
            return list;
        }
    }

}
