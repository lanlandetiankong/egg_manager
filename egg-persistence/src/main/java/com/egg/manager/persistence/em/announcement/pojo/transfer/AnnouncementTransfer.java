package com.egg.manager.persistence.em.announcement.pojo.transfer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.Announcement;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementDraft;
import com.egg.manager.persistence.em.announcement.db.mysql.entity.AnnouncementTag;
import com.egg.manager.persistence.em.user.db.mysql.entity.UserAccount;
import com.egg.manager.persistence.em.announcement.pojo.dto.AnnouncementDto;
import com.egg.manager.persistence.em.announcement.pojo.mapstruct.imap.AnnouncementMapstruct;
import com.egg.manager.persistence.expand.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.em.announcement.pojo.vo.AnnouncementVo;
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
@Named("announcementTransfer")
public class AnnouncementTransfer extends BaseMysqlTransfer {
    static AnnouncementMapstruct announcementMapstruct = AnnouncementMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static Announcement transferVoToEntity(AnnouncementVo vo) {
        if (vo == null) {
            return null;
        }
        Announcement entity = announcementMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static AnnouncementVo transferEntityToVo(Announcement entity, Map<Long, AnnouncementTag> announcementTagMap) {
        if (entity == null) {
            return null;
        }
        AnnouncementVo vo = announcementMapstruct.transferEntityToVo(entity, announcementTagMap);
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

    public static List<AnnouncementVo> transferEntityToVoList(List<Announcement> entityList, Map<Long, AnnouncementTag> announcementTagMap) {
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

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static AnnouncementVo transferDtoToVo(AnnouncementDto dto, Map<Long, AnnouncementTag> announcementTagMap) {
        if (dto == null) {
            return null;
        }
        AnnouncementVo vo = announcementMapstruct.transferDtoToVo(dto, announcementTagMap);
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

    public static List<AnnouncementVo> transferDtoToVoList(List<AnnouncementDto> dtos, Map<Long, AnnouncementTag> announcementTagMap) {
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
     * @param announcementDraft
     * @return
     */
    public static Announcement transferFromDraft(UserAccount loginUser, AnnouncementDraft announcementDraft) {
        Announcement announcement = announcementMapstruct.transferFromDraftEntity(announcementDraft);
        if (loginUser != null) {
            announcement.setCreateUserId(loginUser.getFid());
            announcement.setLastModifyerId(loginUser.getFid());
        } else {
            announcement.setCreateUserId(announcementDraft.getCreateUserId());
            announcement.setLastModifyerId(announcementDraft.getLastModifyerId());
        }
        return announcement;
    }
}
