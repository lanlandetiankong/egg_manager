package com.egg.manager.persistence.pojo.transfer.mysql.announcement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.egg.manager.common.util.str.MyStringUtil;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementDraft;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementDraftDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.announcement.AnnouncementDraftVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserAccountTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementDraftVo;
import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Named("AnnouncementDraftTransfer")
public class AnnouncementDraftTransfer extends MyBaseMysqlTransfer {

    static AnnouncementDraftVoMapstruct announcementDraftVoMapstruct = AnnouncementDraftVoMapstruct.INSTANCE;

    public static AnnouncementDraft transferVoToEntity(AnnouncementDraftVo announcementDraftVo) {
        AnnouncementDraft announcementDraft = announcementDraftVoMapstruct.transferVoToEntity(announcementDraftVo);
        if(announcementDraft == null){
            return announcementDraft ;
        }
        List<String> tagIds = announcementDraftVo.getTagIds();
        if (tagIds != null && tagIds.size() > 0) {
            announcementDraft.setTagIds(JSON.toJSONString(tagIds));
        }
        return announcementDraft;
    }

    public static AnnouncementDraftVo transferEntityToVo(AnnouncementDraft announcementDraft, Map<String, AnnouncementTag> announcementTagMap) {
        AnnouncementDraftVo announcementDraftVo = announcementDraftVoMapstruct.transferEntityToVo(announcementDraft);
        String content = announcementDraft.getContent();
        if (StringUtils.isNotBlank(content)) {
            announcementDraftVo.setShortContent(MyStringUtil.htmlDomToText(content, null));
        } else {
            announcementDraftVo.setShortContent(content);
        }
        String tagIds = announcementDraft.getTagIds();
        if (StringUtils.isNotBlank(tagIds)) {
            try {
                List<String> tagList = JSONArray.parseArray(tagIds, String.class);
                if (tagList != null && tagList.isEmpty() == false) {
                    announcementDraftVo.setTagIds(tagList);
                    List<String> tagNameList = new ArrayList<>();
                    if (announcementTagMap != null && announcementTagMap.isEmpty() == false) {
                        for (String tagId : tagList) {
                            AnnouncementTag announcementTag = announcementTagMap.get(tagId);
                            if (announcementTag != null) {
                                tagNameList.add(announcementTag.getName());
                            }
                        }
                    }
                    announcementDraftVo.setTagNames(tagNameList);
                    announcementDraftVo.setTagNameOfStr(Joiner.on(",").join(tagNameList));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return announcementDraftVo;
    }


    public static AnnouncementDraftVo transferDtoToVo(AnnouncementDraftDto announcementDraftDto, Map<String, AnnouncementTag> announcementTagMap) {
        if (announcementDraftDto == null) {
            return null;
        }
        AnnouncementDraftVo announcementDraftVo = announcementDraftVoMapstruct.transferDtoToVo(announcementDraftDto);
        String content = announcementDraftDto.getContent();
        if (StringUtils.isNotBlank(content)) {
            announcementDraftVo.setShortContent(MyStringUtil.htmlDomToText(content, null));
        } else {
            announcementDraftVo.setShortContent(content);
        }
        String tagIds = announcementDraftDto.getTagIds();
        if (StringUtils.isNotBlank(tagIds)) {
            try {
                List<String> tagList = JSONArray.parseArray(tagIds, String.class);
                if (tagList != null && tagList.isEmpty() == false) {
                    announcementDraftVo.setTagIds(tagList);
                    List<String> tagNameList = new ArrayList<>();
                    if (announcementTagMap != null && announcementTagMap.isEmpty() == false) {
                        for (String tagId : tagList) {
                            AnnouncementTag announcementTag = announcementTagMap.get(tagId);
                            if (announcementTag != null) {
                                tagNameList.add(announcementTag.getName());
                            }
                        }
                    }
                    announcementDraftVo.setTagNames(tagNameList);
                    announcementDraftVo.setTagNameOfStr(Joiner.on(",").join(tagNameList));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        announcementDraftVo.setCreateUser(UserAccountTransfer.transferEntityToVo(announcementDraftDto.getCreateUser()));
        announcementDraftVo.setLastModifyer(UserAccountTransfer.transferEntityToVo(announcementDraftDto.getLastModifyer()));
        return announcementDraftVo;
    }

    public static List<AnnouncementDraftVo> transferEntityToVoList(List<AnnouncementDraft> announcementDrafts, Map<String, AnnouncementTag> announcementTagMap) {
        if (announcementDrafts == null) {
            return null;
        } else {
            List<AnnouncementDraftVo> list = new ArrayList<>();
            for (AnnouncementDraft announcementDraft : announcementDrafts) {
                list.add(transferEntityToVo(announcementDraft, announcementTagMap));
            }
            return list;
        }
    }


    public static List<AnnouncementDraftVo> transferDtoToVoList(List<AnnouncementDraftDto> announcementDraftDtos, Map<String, AnnouncementTag> announcementTagMap) {
        if (announcementDraftDtos == null) {
            return null;
        } else {
            List<AnnouncementDraftVo> list = new ArrayList<>();
            for (AnnouncementDraftDto announcementDraftDto : announcementDraftDtos) {
                list.add(transferDtoToVo(announcementDraftDto, announcementTagMap));
            }
            return list;
        }
    }






    /**
     * tagIds 转 json字符串
     * @param tagIds
     * @return
     */
    @Named("tagIdListToJsonString")
    public String tagIdListToJsonString(List<String> tagIds){
        return JSON.toJSONString(tagIds);
    }


    /**
     * tagIds 转 json字符串
     * @param tagIds
     * @return
     */
    @Named("tagIdJsonStringToList")
    public List<String>  tagIdJsonStringToList(String tagIds){
        List<String> tagList = null ;
        if (StringUtils.isNotBlank(tagIds)) {
            try {
                tagList = JSONArray.parseArray(tagIds, String.class);
                if (tagList != null && tagList.isEmpty() == false) {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return tagList ;
    }

}
