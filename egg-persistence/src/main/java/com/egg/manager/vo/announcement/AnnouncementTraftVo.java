package com.egg.manager.vo.announcement;

import com.egg.manager.common.base.enums.define.DefineJobTypeEnum;
import com.egg.manager.entity.announcement.AnnouncementTraft;
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
public class AnnouncementTraftVo {

    private String fid ;

    private String title ;
    private String keyWord ;    //关键字
    private String publishDepartment ;  //发布部门
    private String content ;
    private String tagIds ; //公告标签 集合
    private String accessory ;      //附件

    private Integer state ;
    private String remark;
    private Date createTime ;
    private Date updateTime ;
    private String createUser ;
    private String lastModifyer;



    public static AnnouncementTraft transferVoToEntity(AnnouncementTraftVo announcementTraftVo) {
        if(announcementTraftVo == null){
            return null ;
        }
        AnnouncementTraft announcementTraft = new AnnouncementTraft() ;
        announcementTraft.setFid(announcementTraftVo.getFid());
        announcementTraft.setTitle(announcementTraftVo.getTitle());
        announcementTraft.setKeyWord(announcementTraftVo.getKeyWord());
        announcementTraft.setPublishDepartment(announcementTraftVo.getPublishDepartment());
        announcementTraft.setContent(announcementTraftVo.getContent());
        announcementTraft.setTagIds(announcementTraftVo.getTagIds());
        announcementTraft.setAccessory(announcementTraftVo.getAccessory());
        announcementTraft.setRemark(announcementTraftVo.getRemark());
        announcementTraft.setCreateTime(announcementTraftVo.getCreateTime());
        announcementTraft.setUpdateTime(announcementTraftVo.getUpdateTime());
        announcementTraft.setCreateUser(announcementTraftVo.getCreateUser());
        announcementTraft.setLastModifyer(announcementTraftVo.getLastModifyer());
        return announcementTraft ;
    }

    public static AnnouncementTraftVo transferEntityToVo(AnnouncementTraft announcementTraft) {
        if(announcementTraft == null){
            return null ;
        }
        AnnouncementTraftVo announcementTraftVo = new AnnouncementTraftVo() ;
        announcementTraftVo.setFid(announcementTraft.getFid());
        announcementTraftVo.setTitle(announcementTraft.getTitle());
        announcementTraftVo.setKeyWord(announcementTraft.getKeyWord());
        announcementTraftVo.setPublishDepartment(announcementTraft.getPublishDepartment());
        announcementTraftVo.setContent(announcementTraft.getContent());
        announcementTraftVo.setTagIds(announcementTraft.getTagIds());
        announcementTraftVo.setAccessory(announcementTraft.getAccessory());

        announcementTraftVo.setState(announcementTraft.getState());
        announcementTraftVo.setRemark(announcementTraft.getRemark());
        announcementTraftVo.setCreateTime(announcementTraft.getCreateTime());
        announcementTraftVo.setUpdateTime(announcementTraft.getUpdateTime());
        announcementTraftVo.setCreateUser(announcementTraft.getCreateUser());
        announcementTraftVo.setLastModifyer(announcementTraft.getLastModifyer());
        return announcementTraftVo ;
    }

    public static List<AnnouncementTraftVo> transferEntityToVoList(List<AnnouncementTraft> announcementTrafts){
        if(announcementTrafts == null){
            return null ;
        }   else {
            List<AnnouncementTraftVo> list = new ArrayList<>() ;
            for (AnnouncementTraft announcementTraft : announcementTrafts){
                list.add(transferEntityToVo(announcementTraft));
            }
            return list ;
        }
    }

}
