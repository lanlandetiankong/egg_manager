package com.egg.manager.persistence.em.announcement.pojo.transfer;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementDraftEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementEntity;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementTagEntity;
import com.egg.manager.persistence.em.announcement.pojo.dto.AnnouncementDto;
import com.egg.manager.persistence.em.announcement.pojo.mapstruct.imap.AnnouncementMapstruct;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementVo;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccountEntity;
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
@Named("announcementTransfer")
public class AnnouncementTransfer extends BaseMysqlTransfer {
    static AnnouncementMapstruct announcementMapstruct = AnnouncementMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static AnnouncementEntity transferVoToEntity(AnnouncementVo vo) {
        if (vo == null) {
            return null;
        }
        AnnouncementEntity entity = announcementMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static AnnouncementVo transferEntityToVo(AnnouncementEntity entity, Map<String, AnnouncementTagEntity> announcementTagMap) {
        if (entity == null) {
            return null;
        }
        AnnouncementVo vo = announcementMapstruct.transferEntityToVo(entity, announcementTagMap);
        String tagIds = entity.getTagIds();
        if (StringUtils.isNotBlank(tagIds)) {
            try {
                List<String> tagList = JSONArray.parseArray(tagIds, String.class);
                if (CollectionUtil.isNotEmpty(tagList)) {
                    vo.setTagIds(tagList);
                    List<String> tagNameList = new ArrayList<>();
                    if (CollectionUtil.isNotEmpty(announcementTagMap)) {
                        for (String tagId : tagList) {
                            AnnouncementTagEntity announcementTagEntity = announcementTagMap.get(tagId);
                            if (announcementTagEntity != null) {
                                tagNameList.add(announcementTagEntity.getName());
                            }
                        }
                    }
                    vo.setTagNames(tagNameList);
                    vo.setTagNameOfStr(Joiner.on(",").join(tagNameList));
                }
            } catch (JSONException e) {
                log.error("执行异常--->",e);
            }
        }
        return vo;
    }

    public static List<AnnouncementVo> transferEntityToVoList(List<AnnouncementEntity> entityList, Map<String, AnnouncementTagEntity> announcementTagMap) {
        if (entityList == null) {
            return null;
        } else {
            List<AnnouncementVo> list = new ArrayList<>();
            for (AnnouncementEntity entity : entityList) {
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
    public static AnnouncementVo transferDtoToVo(AnnouncementDto dto, Map<String, AnnouncementTagEntity> announcementTagMap) {
        if (dto == null) {
            return null;
        }
        AnnouncementVo vo = announcementMapstruct.transferDtoToVo(dto, announcementTagMap);
        String tagIds = dto.getTagIds();
        if (StringUtils.isNotBlank(tagIds)) {
            try {
                List<String> tagList = JSONArray.parseArray(tagIds, String.class);
                if (CollectionUtil.isNotEmpty(tagList)) {
                    vo.setTagIds(tagList);
                    List<String> tagNameList = new ArrayList<>();
                    if (CollectionUtil.isNotEmpty(announcementTagMap)) {
                        for (String tagId : tagList) {
                            AnnouncementTagEntity announcementTagEntity = announcementTagMap.get(tagId);
                            if (announcementTagEntity != null) {
                                tagNameList.add(announcementTagEntity.getName());
                            }
                        }
                    }
                    vo.setTagNames(tagNameList);
                    vo.setTagNameOfStr(Joiner.on(",").join(tagNameList));
                }
            } catch (JSONException e) {
                log.error("执行异常--->",e);
            }
        }
        return vo;
    }

    public static List<AnnouncementVo> transferDtoToVoList(List<AnnouncementDto> dtos, Map<String, AnnouncementTagEntity> announcementTagMap) {
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

    /**
     * 公告草稿 转 公告 entity
     * @param loginUser
     * @param announcementDraftEntity
     * @return
     */
    public static AnnouncementEntity transferFromDraft(UserAccountEntity loginUser, AnnouncementDraftEntity announcementDraftEntity) {
        AnnouncementEntity announcementEntity = announcementMapstruct.transferFromDraftEntity(announcementDraftEntity);
        if (loginUser != null) {
            announcementEntity.setCreateUserId(loginUser.getFid());
            announcementEntity.setLastModifyerId(loginUser.getFid());
        } else {
            announcementEntity.setCreateUserId(announcementDraftEntity.getCreateUserId());
            announcementEntity.setLastModifyerId(announcementDraftEntity.getLastModifyerId());
        }
        return announcementEntity;
    }
}
