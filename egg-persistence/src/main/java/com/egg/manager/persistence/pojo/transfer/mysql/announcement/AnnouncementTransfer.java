package com.egg.manager.persistence.pojo.transfer.mysql.announcement;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.egg.manager.persistence.db.mysql.entity.announcement.Announcement;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.announcement.AnnouncementMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementVo;
import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Named("AnnouncementTransfer")
public class AnnouncementTransfer extends MyBaseMysqlTransfer {
    static AnnouncementMapstruct announcementVoMapstruct = AnnouncementMapstruct.INSTANCE;

    public static Announcement transferVoToEntity(AnnouncementVo vo) {
        if (vo == null) {
            return null;
        }
        Announcement entity = announcementVoMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * TODO
     * @param entity
     * @param announcementTagMap
     * @return
     */
    public static AnnouncementVo transferEntityToVo(Announcement entity, Map<String, AnnouncementTag> announcementTagMap) {
        if (entity == null) {
            return null;
        }
        AnnouncementVo vo = announcementVoMapstruct.transferEntityToVo(entity,announcementTagMap);
        String tagIds = entity.getTagIds();
        if (StringUtils.isNotBlank(tagIds)) {
            try {
                List<String> tagList = JSONArray.parseArray(tagIds, String.class);
                if (tagList != null && tagList.isEmpty() == false) {
                    vo.setTagIds(tagList);
                    List<String> tagNameList = new ArrayList<>();
                    if (announcementTagMap != null && announcementTagMap.isEmpty() == false) {
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
        return vo;
    }

    public static List<AnnouncementVo> transferEntityToVoList(List<Announcement> entityList, Map<String, AnnouncementTag> announcementTagMap) {
        if (entityList == null) {
            return null;
        } else {
            List<AnnouncementVo> list = new ArrayList<>();
            for (Announcement entity : entityList) {
                list.add(transferEntityToVo(entity, announcementTagMap));
            }
            return list;
        }
    }


    public static AnnouncementVo transferDtoToVo(AnnouncementDto dto, Map<String, AnnouncementTag> announcementTagMap) {
        if (dto == null) {
            return null;
        }
        AnnouncementVo vo = announcementVoMapstruct.transferDtoToVo(dto,announcementTagMap);
        String tagIds = dto.getTagIds();
        if (StringUtils.isNotBlank(tagIds)) {
            try {
                List<String> tagList = JSONArray.parseArray(tagIds, String.class);
                if (tagList != null && tagList.isEmpty() == false) {
                    vo.setTagIds(tagList);
                    List<String> tagNameList = new ArrayList<>();
                    if (announcementTagMap != null && announcementTagMap.isEmpty() == false) {
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
        return vo;
    }

    public static List<AnnouncementVo> transferDtoToVoList(List<AnnouncementDto> dtos, Map<String, AnnouncementTag> announcementTagMap) {
        if (dtos == null) {
            return null;
        } else {
            List<AnnouncementVo> list = new ArrayList<>();
            for (AnnouncementDto dto : dtos) {
                list.add(transferDtoToVo(dto, announcementTagMap));
            }
            return list;
        }
    }

}
