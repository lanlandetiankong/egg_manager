package com.egg.manager.vo.announcement;

import com.egg.manager.entity.announcement.AnnouncementTag;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/21
 * \* Time: 13:42
 * \* Description:
 * \
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnnouncementTagVo {
    private String fid;
    private String name;
    private String description;
    private Integer ordering;

    private Integer state;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private String createUser;
    private String lastModifyer;


    public static AnnouncementTag transferVoToEntity(AnnouncementTagVo announcementTagVo) {
        if (announcementTagVo == null) {
            return null;
        }
        AnnouncementTag announcementTag = new AnnouncementTag();
        announcementTag.setFid(announcementTagVo.getFid());
        announcementTag.setName(announcementTagVo.getName());
        announcementTag.setDescription(announcementTagVo.getDescription());
        announcementTag.setOrdering(announcementTagVo.getOrdering());
        announcementTag.setState(announcementTagVo.getState());
        announcementTag.setRemark(announcementTagVo.getRemark());
        announcementTag.setCreateTime(announcementTagVo.getCreateTime());
        announcementTag.setUpdateTime(announcementTagVo.getUpdateTime());
        announcementTag.setCreateUser(announcementTagVo.getCreateUser());
        announcementTag.setLastModifyer(announcementTagVo.getLastModifyer());
        return announcementTag;
    }

    public static AnnouncementTagVo transferEntityToVo(AnnouncementTag announcementTag) {
        if (announcementTag == null) {
            return null;
        }
        AnnouncementTagVo announcementTagVo = new AnnouncementTagVo();
        announcementTagVo.setFid(announcementTag.getFid());
        announcementTagVo.setName(announcementTag.getName());
        announcementTagVo.setDescription(announcementTag.getDescription());
        announcementTagVo.setOrdering(announcementTag.getOrdering());
        announcementTagVo.setState(announcementTag.getState());
        announcementTagVo.setRemark(announcementTag.getRemark());
        announcementTagVo.setCreateTime(announcementTag.getCreateTime());
        announcementTagVo.setUpdateTime(announcementTag.getUpdateTime());
        announcementTagVo.setCreateUser(announcementTag.getCreateUser());
        announcementTagVo.setLastModifyer(announcementTag.getLastModifyer());
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

}
