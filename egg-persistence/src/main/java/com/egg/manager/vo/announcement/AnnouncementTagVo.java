package com.egg.manager.vo.announcement;

import com.egg.manager.dto.announcement.AnnouncementDraftDto;
import com.egg.manager.dto.announcement.AnnouncementTagDto;
import com.egg.manager.entity.announcement.AnnouncementTag;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.vo.user.UserAccountVo;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    private String remark;
    private Integer state;
    private Date createTime;
    private Date updateTime;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;




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
        announcementTagVo.setCreateUser(UserAccountVo.transferEntityToVo(announcementTagDto.getCreateUser()));
        announcementTagVo.setLastModifyer(UserAccountVo.transferEntityToVo(announcementTagDto.getLastModifyer()));
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

    public static List<AnnouncementTagVo> transferDtoToVoList(List<AnnouncementTagDto> announcementTagDtos){
        if(announcementTagDtos == null){
            return null ;
        }   else {
            List<AnnouncementTagVo> list = new ArrayList<>() ;
            for (AnnouncementTagDto announcementTagDto : announcementTagDtos){
                list.add(transferDtoToVo(announcementTagDto));
            }
            return list ;
        }
    }

}
