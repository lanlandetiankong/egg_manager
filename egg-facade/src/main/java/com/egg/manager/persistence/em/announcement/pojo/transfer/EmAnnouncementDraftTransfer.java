package com.egg.manager.persistence.em.announcement.pojo.transfer;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.egg.manager.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.EmAnnouncementDraftEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.EmAnnouncementTagEntity;
import com.egg.manager.persistence.em.announcement.pojo.dto.EmAnnouncementDraftDto;
import com.egg.manager.persistence.em.announcement.pojo.mapstruct.imap.EmAnnouncementDraftMapstruct;
import com.egg.manager.persistence.em.announcement.pojo.vo.EmAnnouncementDraftVo;
import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Slf4j
@Component
@Named("announcementDraftTransfer")
public class EmAnnouncementDraftTransfer extends BaseMysqlTransfer {

    static EmAnnouncementDraftMapstruct emAnnouncementDraftMapstruct = EmAnnouncementDraftMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static EmAnnouncementDraftEntity transferVoToEntity(EmAnnouncementDraftVo vo) {
        if (vo == null) {
            return null;
        }
        EmAnnouncementDraftEntity entity = emAnnouncementDraftMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static EmAnnouncementDraftVo transferEntityToVo(EmAnnouncementDraftEntity entity, Map<String, EmAnnouncementTagEntity> announcementTagMap) {
        if (entity == null) {
            return null;
        }
        EmAnnouncementDraftVo vo = emAnnouncementDraftMapstruct.transferEntityToVo(entity);
        EmAnnouncementDraftTransfer.doSetTagInfoToVo(entity.getTagIds(), vo, announcementTagMap);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static EmAnnouncementDraftVo transferDtoToVo(EmAnnouncementDraftDto dto, Map<String, EmAnnouncementTagEntity> announcementTagMap) {
        if (dto == null) {
            return null;
        }
        EmAnnouncementDraftVo vo = emAnnouncementDraftMapstruct.transferDtoToVo(dto);
        //设置 tag相关信息
        EmAnnouncementDraftTransfer.doSetTagInfoToVo(dto.getTagIds(), vo, announcementTagMap);
        return vo;
    }


    public static List<EmAnnouncementDraftVo> transferEntityToVoList(List<EmAnnouncementDraftEntity> announcementDraftEntities, Map<String, EmAnnouncementTagEntity> announcementTagMap) {
        if (announcementDraftEntities == null) {
            return null;
        } else {
            List<EmAnnouncementDraftVo> list = new ArrayList<>();
            for (EmAnnouncementDraftEntity entity : announcementDraftEntities) {
                list.add(transferEntityToVo(entity, announcementTagMap));
            }
            return list;
        }
    }


    public static List<EmAnnouncementDraftVo> transferDtoToVoList(List<EmAnnouncementDraftDto> emAnnouncementDraftDtos, Map<String, EmAnnouncementTagEntity> announcementTagMap) {
        if (emAnnouncementDraftDtos == null) {
            return null;
        } else {
            List<EmAnnouncementDraftVo> list = new ArrayList<>();
            for (EmAnnouncementDraftDto dto : emAnnouncementDraftDtos) {
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
    private static void doSetTagInfoToVo(String tagIds, EmAnnouncementDraftVo vo, Map<String, EmAnnouncementTagEntity> announcementTagMap) {
        if (StringUtils.isNotBlank(tagIds)) {
            try {
                List<String> tagList = JSONArray.parseArray(tagIds, String.class);
                if (CollectionUtil.isNotEmpty(tagList)) {
                    vo.setTagIds(tagList);
                    List<String> tagNameList = new ArrayList<>();
                    if (CollectionUtil.isNotEmpty(announcementTagMap)) {
                        for (String tagId : tagList) {
                            EmAnnouncementTagEntity emAnnouncementTagEntity = announcementTagMap.get(tagId);
                            if (emAnnouncementTagEntity != null) {
                                tagNameList.add(emAnnouncementTagEntity.getName());
                            }
                        }
                    }
                    vo.setTagNames(tagNameList);
                    vo.setTagNameOfStr(Joiner.on(",").join(tagNameList));
                }
            } catch (JSONException e) {
                log.error(BaseRstMsgConstant.ErrorMsg.executionException("--->"), e);
            }
        }
    }
}
