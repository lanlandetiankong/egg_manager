package com.egg.manager.persistence.transfer.announcement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.egg.manager.common.util.str.MyStringUtil;
import com.egg.manager.persistence.dto.announcement.AnnouncementDto;
import com.egg.manager.persistence.entity.announcement.Announcement;
import com.egg.manager.persistence.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.transfer.user.UserAccountTransfer;
import com.egg.manager.persistence.vo.announcement.AnnouncementVo;
import com.egg.manager.persistence.vo.user.UserAccountVo;
import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AnnouncementTransfer {

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

        announcement.setRemark(announcementVo.getRemark());
        announcement.setState(announcementVo.getState());
        announcement.setCreateTime(announcementVo.getCreateTime());
        announcement.setUpdateTime(announcementVo.getUpdateTime());
        announcement.setCreateUserId(announcementVo.getCreateUserId());
        announcement.setLastModifyerId(announcementVo.getLastModifyerId());
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

        announcementVo.setRemark(announcement.getRemark());
        announcementVo.setState(announcement.getState());
        announcementVo.setCreateTime(announcement.getCreateTime());
        announcementVo.setUpdateTime(announcement.getUpdateTime());
        announcementVo.setCreateUserId(announcement.getCreateUserId());
        announcementVo.setLastModifyerId(announcement.getLastModifyerId());
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


    public static AnnouncementVo transferDtoToVo(AnnouncementDto announcementDto,Map<String,AnnouncementTag> announcementTagMap) {
        if(announcementDto == null){
            return null ;
        }
        AnnouncementVo announcementVo = new AnnouncementVo() ;
        announcementVo.setFid(announcementDto.getFid());
        announcementVo.setTitle(announcementDto.getTitle());
        announcementVo.setKeyWord(announcementDto.getKeyWord());
        announcementVo.setPublishDepartment(announcementDto.getPublishDepartment());
        String content = announcementDto.getContent() ;
        announcementVo.setContent(content);
        announcementVo.setContent(content);
        if(StringUtils.isNotBlank(content)){
            announcementVo.setShortContent(MyStringUtil.htmlDomToText(content,null));
        }   else {
            announcementVo.setShortContent(content);
        }
        String tagIds = announcementDto.getTagIds() ;
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
        announcementVo.setAccessory(announcementDto.getAccessory());

        announcementVo.setRemark(announcementDto.getRemark());
        announcementVo.setState(announcementDto.getState());
        announcementVo.setCreateTime(announcementDto.getCreateTime());
        announcementVo.setUpdateTime(announcementDto.getUpdateTime());
        announcementVo.setCreateUserId(announcementDto.getCreateUserId());
        announcementVo.setLastModifyerId(announcementDto.getLastModifyerId());
        announcementVo.setCreateUser(UserAccountTransfer.transferEntityToVo(announcementDto.getCreateUser()));
        announcementVo.setLastModifyer(UserAccountTransfer.transferEntityToVo(announcementDto.getLastModifyer()));
        return announcementVo ;
    }

    public static List<AnnouncementVo> transferDtoToVoList(List<AnnouncementDto> announcementDtos, Map<String,AnnouncementTag> announcementTagMap){
        if(announcementDtos == null){
            return null ;
        }   else {
            List<AnnouncementVo> list = new ArrayList<>() ;
            for (AnnouncementDto announcementDto : announcementDtos){
                list.add(transferDtoToVo(announcementDto,announcementTagMap));
            }
            return list ;
        }
    }

}
