package com.egg.manager.vo.announcement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.egg.manager.common.util.str.MyStringUtil;
import com.egg.manager.entity.announcement.Announcement;
import com.egg.manager.entity.announcement.AnnouncementTag;
import com.google.common.base.Joiner;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
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
public class AnnouncementVo {

    private String fid ;

    private String title ;
    private String keyWord ;    //关键字
    private String publishDepartment ;  //发布部门
    private String content ;
    private String shortContent ;   //概要的 公告内容
    private List<String> tagIds ; //公告标签 集合
    private List<String> tagNames ; //公告标签 集合
    private String tagNameOfStr ; //公告标签 集合转字符串
    private String accessory ;      //附件

    private Integer state ;
    private String remark;

    private Date createTime ;
    private Date updateTime ;

    private String createUser ;
    private String lastModifyer;





    public static Announcement transferVoToEntity(AnnouncementVo announcementVo) {
        if(announcementVo == null){
            return null ;
        }
        Announcement announcement = new Announcement() ;
        announcement.setFid(announcementVo.getFid());
        announcement.setTitle(announcementVo.getTitle());
        announcement.setKeyWord(announcementVo.getKeyWord());
        announcement.setPublishDepartment(announcementVo.getPublishDepartment());
        announcement.setContent(announcementVo.getContent());
        List<String> tagIds = announcementVo.getTagIds();
        if(tagIds != null && tagIds.size() > 0){
            announcement.setTagIds(JSON.toJSONString(tagIds));
        }
        announcement.setAccessory(announcementVo.getAccessory());

        announcement.setState(announcementVo.getState());
        announcement.setRemark(announcementVo.getRemark());
        announcement.setCreateTime(announcementVo.getCreateTime());
        announcement.setUpdateTime(announcementVo.getUpdateTime());
        announcement.setCreateUser(announcementVo.getCreateUser());
        announcement.setLastModifyer(announcementVo.getLastModifyer());
        return announcement ;
    }

    public static AnnouncementVo transferEntityToVo(Announcement announcement,Map<String,AnnouncementTag> announcementTagMap) {
        if(announcement == null){
            return null ;
        }
        AnnouncementVo announcementVo = new AnnouncementVo() ;
        announcementVo.setFid(announcement.getFid());
        announcementVo.setTitle(announcement.getTitle());
        announcementVo.setKeyWord(announcement.getKeyWord());
        announcementVo.setPublishDepartment(announcement.getPublishDepartment());
        String content = announcement.getContent() ;
        announcementVo.setContent(content);
        if(StringUtils.isNotBlank(content)){
            announcementVo.setShortContent(MyStringUtil.htmlDomToText(content,null));
        }   else {
            announcementVo.setShortContent(content);
        }
        String tagIds = announcement.getTagIds() ;
        if(StringUtils.isNotBlank(tagIds)){
            try{
                List<String> tagList = JSONArray.parseArray(tagIds,String.class);
                if(tagList != null && tagList.isEmpty() == false){
                    announcementVo.setTagIds(tagList);
                    List<String> tagNameList = new ArrayList<>() ;
                    if(announcementTagMap != null && announcementTagMap.isEmpty() == false){
                        for (String tagId : tagList){
                            AnnouncementTag announcementTag = announcementTagMap.get(tagId);
                            if(announcementTag != null){
                                tagNameList.add(announcementTag.getName());
                            }
                        }
                    }
                    announcementVo.setTagNames(tagNameList);
                    announcementVo.setTagNameOfStr(Joiner.on(",").join(tagNameList));
                }
            }   catch (JSONException e){
                e.printStackTrace();
            }
        }

        announcementVo.setAccessory(announcement.getAccessory());
        announcementVo.setState(announcement.getState());
        announcementVo.setRemark(announcement.getRemark());
        announcementVo.setCreateTime(announcement.getCreateTime());
        announcementVo.setUpdateTime(announcement.getUpdateTime());
        announcementVo.setCreateUser(announcement.getCreateUser());
        announcementVo.setLastModifyer(announcement.getLastModifyer());
        return announcementVo ;
    }

    public static List<AnnouncementVo> transferEntityToVoList(List<Announcement> announcements,Map<String,AnnouncementTag> announcementTagMap){
        if(announcements == null){
            return null ;
        }   else {
            List<AnnouncementVo> list = new ArrayList<>() ;
            for (Announcement announcement : announcements){
                list.add(transferEntityToVo(announcement,announcementTagMap));
            }
            return list ;
        }
    }

}
