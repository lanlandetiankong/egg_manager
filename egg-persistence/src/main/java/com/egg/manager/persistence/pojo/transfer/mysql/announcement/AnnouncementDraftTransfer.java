package com.egg.manager.persistence.pojo.transfer.mysql.announcement;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementDraft;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementDraftDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.announcement.AnnouncementDraftVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
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

    public static AnnouncementDraft transferVoToEntity(AnnouncementDraftVo vo) {
        if(vo == null){
            return null ;
        }
        AnnouncementDraft entity = announcementDraftVoMapstruct.transferVoToEntity(vo);
        return entity;
    }

    public static AnnouncementDraftVo transferEntityToVo(AnnouncementDraft entity, Map<String, AnnouncementTag> announcementTagMap) {
        if (entity == null) {
            return null;
        }
        AnnouncementDraftVo vo = announcementDraftVoMapstruct.transferEntityToVo(entity);
        AnnouncementDraftTransfer.doSetTagInfoToVo(entity.getTagIds(),vo,announcementTagMap);
        return vo;
    }


    public static AnnouncementDraftVo transferDtoToVo(AnnouncementDraftDto dto, Map<String, AnnouncementTag> announcementTagMap) {
        if (dto == null) {
            return null;
        }
        AnnouncementDraftVo vo = announcementDraftVoMapstruct.transferDtoToVo(dto);
        //设置 tag相关信息
        AnnouncementDraftTransfer.doSetTagInfoToVo(dto.getTagIds(),vo,announcementTagMap);
        return vo;
    }




    public static List<AnnouncementDraftVo> transferEntityToVoList(List<AnnouncementDraft> announcementDrafts, Map<String, AnnouncementTag> announcementTagMap) {
        if (announcementDrafts == null) {
            return null;
        } else {
            List<AnnouncementDraftVo> list = new ArrayList<>();
            for (AnnouncementDraft entity : announcementDrafts) {
                list.add(transferEntityToVo(entity, announcementTagMap));
            }
            return list;
        }
    }


    public static List<AnnouncementDraftVo> transferDtoToVoList(List<AnnouncementDraftDto> announcementDraftDtos, Map<String, AnnouncementTag> announcementTagMap) {
        if (announcementDraftDtos == null) {
            return null;
        } else {
            List<AnnouncementDraftVo> list = new ArrayList<>();
            for (AnnouncementDraftDto dto : announcementDraftDtos) {
                list.add(transferDtoToVo(dto, announcementTagMap));
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

    /**
     * 设置tag的相关信息
     * @param tagIds
     * @param vo
     * @param announcementTagMap
     */
    private static void doSetTagInfoToVo(String tagIds,AnnouncementDraftVo vo,Map<String, AnnouncementTag> announcementTagMap){
        if (StringUtils.isNotBlank(tagIds)) {
            try {
                List<String> tagList = JSONArray.parseArray(tagIds, String.class);
                if (CollectionUtil.isNotEmpty(tagList)) {
                    vo.setTagIds(tagList);
                    List<String> tagNameList = new ArrayList<>();
                    if (CollectionUtil.isNotEmpty(announcementTagMap)) {
                        for (String tagId : tagList) {
                            AnnouncementTag announcementTag = announcementTagMap.get(tagId);
                            if (announcementTag != null) {
                                tagNameList.add(announcementTag.getName());
                            }
                        }
                    }
                    vo.setTagNames(tagNameList);
                    vo.setTagNameOfStr(Joiner.on(",").join(tagNameList));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
