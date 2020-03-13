package com.egg.manager.vo.announcement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.baomidou.mybatisplus.annotations.TableField;
import com.egg.manager.common.base.enums.module.DefineMenuUrlJumpTypeEnum;
import com.egg.manager.common.util.str.MyStringUtil;
import com.egg.manager.dto.announcement.AnnouncementDraftDto;
import com.egg.manager.dto.define.DefineMenuDto;
import com.egg.manager.entity.announcement.AnnouncementDraft;
import com.egg.manager.entity.announcement.AnnouncementTag;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.vo.define.DefineMenuVo;
import com.egg.manager.vo.user.UserAccountVo;
import com.google.common.base.Joiner;
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
    private String tagNameOfStr ; //公告标签 集合转字符串
    private String accessory ;      //附件
    private Integer isPublished ;      //是否已提交

    private Integer state ;
    private String remark;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;






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
        announcementDraft.setCreateUserId(announcementDraftVo.getCreateUserId());
        announcementDraft.setLastModifyerId(announcementDraftVo.getLastModifyerId());
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
                    announcementDraftVo.setTagNameOfStr(Joiner.on(",").join(tagNameList));
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
        announcementDraftVo.setCreateUserId(announcementDraft.getCreateUserId());
        announcementDraftVo.setLastModifyerId(announcementDraft.getLastModifyerId());
        return announcementDraftVo ;
    }


    public static AnnouncementDraftVo transferDtoToVo(AnnouncementDraftDto announcementDraftDto,Map<String,AnnouncementTag> announcementTagMap) {
        if(announcementDraftDto == null){
            return null ;
        }
        AnnouncementDraftVo announcementDraftVo = new AnnouncementDraftVo() ;
        announcementDraftVo.setFid(announcementDraftDto.getFid());
        announcementDraftVo.setTitle(announcementDraftDto.getTitle());
        announcementDraftVo.setKeyWord(announcementDraftDto.getKeyWord());
        announcementDraftVo.setPublishDepartment(announcementDraftDto.getPublishDepartment());
        String content = announcementDraftDto.getContent() ;
        announcementDraftVo.setContent(content);
        announcementDraftVo.setContent(content);
        if(StringUtils.isNotBlank(content)){
            announcementDraftVo.setShortContent(MyStringUtil.htmlDomToText(content,null));
        }   else {
            announcementDraftVo.setShortContent(content);
        }
        String tagIds = announcementDraftDto.getTagIds() ;
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
                    announcementDraftVo.setTagNameOfStr(Joiner.on(",").join(tagNameList));
                }
            }   catch (JSONException e){
                e.printStackTrace();
            }
        }
        announcementDraftVo.setAccessory(announcementDraftDto.getAccessory());
        announcementDraftVo.setIsPublished(announcementDraftDto.getIsPublished());
        announcementDraftVo.setState(announcementDraftDto.getState());
        announcementDraftVo.setRemark(announcementDraftDto.getRemark());
        announcementDraftVo.setCreateTime(announcementDraftDto.getCreateTime());
        announcementDraftVo.setUpdateTime(announcementDraftDto.getUpdateTime());
        announcementDraftVo.setCreateUserId(announcementDraftDto.getCreateUserId());
        announcementDraftVo.setLastModifyerId(announcementDraftDto.getLastModifyerId());
        announcementDraftVo.setCreateUser(UserAccountVo.transferEntityToVo(announcementDraftDto.getCreateUser()));
        announcementDraftVo.setLastModifyer(UserAccountVo.transferEntityToVo(announcementDraftDto.getLastModifyer()));
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


    public static List<AnnouncementDraftVo> transferDtoToVoList(List<AnnouncementDraftDto> announcementDraftDtos,Map<String,AnnouncementTag> announcementTagMap){
        if(announcementDraftDtos == null){
            return null ;
        }   else {
            List<AnnouncementDraftVo> list = new ArrayList<>() ;
            for (AnnouncementDraftDto announcementDraftDto : announcementDraftDtos){
                list.add(transferDtoToVo(announcementDraftDto,announcementTagMap));
            }
            return list ;
        }
    }

}
