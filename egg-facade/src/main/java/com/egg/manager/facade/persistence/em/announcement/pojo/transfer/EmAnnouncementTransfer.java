package com.egg.manager.facade.persistence.em.announcement.pojo.transfer;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.egg.manager.facade.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.facade.persistence.em.announcement.db.mysql.entity.EmAnnouncementDraftEntity;
import com.egg.manager.facade.persistence.em.announcement.db.mysql.entity.EmAnnouncementEntity;
import com.egg.manager.facade.persistence.em.announcement.db.mysql.entity.EmAnnouncementTagEntity;
import com.egg.manager.facade.persistence.em.announcement.pojo.dto.EmAnnouncementDto;
import com.egg.manager.facade.persistence.em.announcement.pojo.mapstruct.imap.EmAnnouncementMapstruct;
import com.egg.manager.facade.persistence.em.announcement.pojo.vo.EmAnnouncementVo;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.facade.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
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
@Named("announcementTransfer")
public class EmAnnouncementTransfer extends BaseMysqlTransfer {
    static EmAnnouncementMapstruct emAnnouncementMapstruct = EmAnnouncementMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static EmAnnouncementEntity transferVoToEntity(EmAnnouncementVo vo) {
        if (vo == null) {
            return null;
        }
        EmAnnouncementEntity entity = emAnnouncementMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static EmAnnouncementVo transferEntityToVo(EmAnnouncementEntity entity, Map<String, EmAnnouncementTagEntity> announcementTagMap) {
        if (entity == null) {
            return null;
        }
        EmAnnouncementVo vo = emAnnouncementMapstruct.transferEntityToVo(entity, announcementTagMap);
        String tagIds = entity.getTagIds();
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
                log.error(BaseRstMsgConstant.ErrorMsg.executionException("--->"),e);
            }
        }
        return vo;
    }

    public static List<EmAnnouncementVo> transferEntityToVoList(List<EmAnnouncementEntity> entityList, Map<String, EmAnnouncementTagEntity> announcementTagMap) {
        if (entityList == null) {
            return null;
        } else {
            List<EmAnnouncementVo> list = new ArrayList<>();
            for (EmAnnouncementEntity entity : entityList) {
                list.add(transferEntityToVo(entity, announcementTagMap));
            }
            return list;
        }
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static EmAnnouncementVo transferDtoToVo(EmAnnouncementDto dto, Map<String, EmAnnouncementTagEntity> announcementTagMap) {
        if (dto == null) {
            return null;
        }
        EmAnnouncementVo vo = emAnnouncementMapstruct.transferDtoToVo(dto, announcementTagMap);
        String tagIds = dto.getTagIds();
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
                log.error(BaseRstMsgConstant.ErrorMsg.executionException("--->"),e);
            }
        }
        return vo;
    }

    public static List<EmAnnouncementVo> transferDtoToVoList(List<EmAnnouncementDto> dtos, Map<String, EmAnnouncementTagEntity> announcementTagMap) {
        if (dtos == null) {
            return null;
        } else {
            List<EmAnnouncementVo> list = new ArrayList<>();
            for (EmAnnouncementDto dto : dtos) {
                list.add(transferDtoToVo(dto, announcementTagMap));
            }
            return list;
        }
    }

    /**
     * 公告草稿 转 公告 entity
     * @param loginUser
     * @param emAnnouncementDraftEntity
     * @return
     */
    public static EmAnnouncementEntity transferFromDraft(EmUserAccountEntity loginUser, EmAnnouncementDraftEntity emAnnouncementDraftEntity) {
        EmAnnouncementEntity emAnnouncementEntity = emAnnouncementMapstruct.transferFromDraftEntity(emAnnouncementDraftEntity);
        if (loginUser != null) {
            emAnnouncementEntity.setCreateUserId(loginUser.getFid());
            emAnnouncementEntity.setLastModifyerId(loginUser.getFid());
        } else {
            emAnnouncementEntity.setCreateUserId(emAnnouncementDraftEntity.getCreateUserId());
            emAnnouncementEntity.setLastModifyerId(emAnnouncementDraftEntity.getLastModifyerId());
        }
        return emAnnouncementEntity;
    }
}
