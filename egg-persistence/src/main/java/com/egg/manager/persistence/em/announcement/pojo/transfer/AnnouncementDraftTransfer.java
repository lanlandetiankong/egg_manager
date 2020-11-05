package com.egg.manager.persistence.em.announcement.pojo.transfer;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementDraft;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementTag;
import com.egg.manager.persistence.em.announcement.pojo.dto.AnnouncementDraftDto;
import com.egg.manager.persistence.em.announcement.pojo.mapstruct.imap.AnnouncementDraftMapstruct;
import com.egg.manager.persistence.enhance.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementDraftVo;
import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Component
@Named("announcementDraftTransfer")
public class AnnouncementDraftTransfer extends BaseMysqlTransfer {

    static AnnouncementDraftMapstruct announcementDraftMapstruct = AnnouncementDraftMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static AnnouncementDraft transferVoToEntity(AnnouncementDraftVo vo) {
        if (vo == null) {
            return null;
        }
        AnnouncementDraft entity = announcementDraftMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static AnnouncementDraftVo transferEntityToVo(AnnouncementDraft entity, Map<Long, AnnouncementTag> announcementTagMap) {
        if (entity == null) {
            return null;
        }
        AnnouncementDraftVo vo = announcementDraftMapstruct.transferEntityToVo(entity);
        AnnouncementDraftTransfer.doSetTagInfoToVo(entity.getTagIds(), vo, announcementTagMap);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static AnnouncementDraftVo transferDtoToVo(AnnouncementDraftDto dto, Map<Long, AnnouncementTag> announcementTagMap) {
        if (dto == null) {
            return null;
        }
        AnnouncementDraftVo vo = announcementDraftMapstruct.transferDtoToVo(dto);
        //设置 tag相关信息
        AnnouncementDraftTransfer.doSetTagInfoToVo(dto.getTagIds(), vo, announcementTagMap);
        return vo;
    }


    public static List<AnnouncementDraftVo> transferEntityToVoList(List<AnnouncementDraft> announcementDrafts, Map<Long, AnnouncementTag> announcementTagMap) {
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


    public static List<AnnouncementDraftVo> transferDtoToVoList(List<AnnouncementDraftDto> announcementDraftDtos, Map<Long, AnnouncementTag> announcementTagMap) {
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
     * 设置tag的相关信息
     * @param tagIds
     * @param vo
     * @param announcementTagMap
     */
    private static void doSetTagInfoToVo(String tagIds, AnnouncementDraftVo vo, Map<Long, AnnouncementTag> announcementTagMap) {
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
