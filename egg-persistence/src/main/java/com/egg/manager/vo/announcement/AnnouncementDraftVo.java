package com.egg.manager.vo.announcement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.egg.manager.common.util.str.MyStringUtil;
import com.egg.manager.entity.announcement.AnnouncementDraft;
import com.egg.manager.entity.announcement.AnnouncementTag;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

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
public class AnnouncementDraftVo {

    private String fid ;

    private String title ;
    private String keyWord ;    //关键字
    private String publishDepartment ;  //发布部门
    private String content ;
    private String shortContent ;   //概要的 公告内容
    private List<String> tagIds ; //公告标签 集合
    private List<String> tagNames ; //公告标签 集合
    private String accessory ;      //附件
    private Integer isPublished ;      //是否已提交

    private Integer state ;
    private String remark;
    private Date createTime ;
    private Date updateTime ;
    private String createUser ;
    private String lastModifyer;



    public static AnnouncementDraft transferVoToEntity(AnnouncementDraftVo announcementDraftVo) {
        if(announcementDraftVo == null){
            return null ;
        }
        AnnouncementDraft announcementDraft = new AnnouncementDraft() ;
        announcementDraft.setFid(announcementDraftVo.getFid());
        announcementDraft.setTitle(announcementDraftVo.getTitle());
        announcementDraft.setKeyWord(announcementDraftVo.getKeyWord());
        announcementDraft.setPublishDepartment(announcementDraftVo.getPublishDepartment());
        announcementDraft.setIsPublished(announcementDraftVo.getIsPublished());
        announcementDraft.setContent(announcementDraftVo.getContent());
        List<String> tagIds = announcementDraftVo.getTagIds();
        if(tagIds != null && tagIds.size() > 0){
            announcementDraft.setTagIds(JSON.toJSONString(tagIds));
        }
        announcementDraft.setAccessory(announcementDraftVo.getAccessory());

        announcementDraft.setState(announcementDraftVo.getState());
        announcementDraft.setRemark(announcementDraftVo.getRemark());
        announcementDraft.setCreateTime(announcementDraftVo.getCreateTime());
        announcementDraft.setUpdateTime(announcementDraftVo.getUpdateTime());
        announcementDraft.setCreateUser(announcementDraftVo.getCreateUser());
        announcementDraft.setLastModifyer(announcementDraftVo.getLastModifyer());
        return announcementDraft ;
    }

    public static AnnouncementDraftVo transferEntityToVo(AnnouncementDraft announcementDraft,Map<String,AnnouncementTag> announcementTagMap) {
        if(announcementDraft == null){
            return null ;
        }
        AnnouncementDraftVo announcementDraftVo = new AnnouncementDraftVo() ;
        announcementDraftVo.setFid(announcementDraft.getFid());
        announcementDraftVo.setTitle(announcementDraft.getTitle());
        announcementDraftVo.setKeyWord(announcementDraft.getKeyWord());
        announcementDraftVo.setPublishDepartment(announcementDraft.getPublishDepartment());
        String content = announcementDraft.getContent() ;
        announcementDraftVo.setContent(content);
        if(StringUtils.isNotBlank(content)){
            announcementDraftVo.setShortContent(MyStringUtil.htmlDomToText(content,null));
        }   else {
            announcementDraftVo.setShortContent(content);
        }
        String tagIds = announcementDraft.getTagIds() ;
        if(StringUtils.isNotBlank(tagIds)){
            try{
                List<String> tagList = JSONArray.parseArray(tagIds,String.class);
                if(tagList != null && tagList.isEmpty() == false){
                    announcementDraftVo.setTagIds(tagList);
                    List<String> tagNameList = new ArrayList<>() ;
                    if(announcementTagMap != null && announcementTagMap.isEmpty() == false){
                        for (String tagId : tagList){
                            AnnouncementTag announcementTag = announcementTagMap.get(tagId);
                            if(announcementTag != null){
                                tagNameList.add(announcementTag.getName());
                            }
                        }
                    }
                    announcementDraftVo.setTagNames(tagNameList);
                }
            }   catch (JSONException e){
                e.printStackTrace();
            }
        }

        announcementDraftVo.setAccessory(announcementDraft.getAccessory());
        announcementDraftVo.setState(announcementDraft.getState());
        announcementDraftVo.setRemark(announcementDraft.getRemark());
        announcementDraftVo.setCreateTime(announcementDraft.getCreateTime());
        announcementDraftVo.setUpdateTime(announcementDraft.getUpdateTime());
        announcementDraftVo.setCreateUser(announcementDraft.getCreateUser());
        announcementDraftVo.setLastModifyer(announcementDraft.getLastModifyer());
        return announcementDraftVo ;
    }

    public static List<AnnouncementDraftVo> transferEntityToVoList(List<AnnouncementDraft> announcementDrafts,Map<String,AnnouncementTag> announcementTagMap){
        if(announcementDrafts == null){
            return null ;
        }   else {
            List<AnnouncementDraftVo> list = new ArrayList<>() ;
            for (AnnouncementDraft announcementDraft : announcementDrafts){
                list.add(transferEntityToVo(announcementDraft,announcementTagMap));
            }
            return list ;
        }
    }

}
