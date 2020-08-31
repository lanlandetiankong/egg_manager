package com.egg.manager.persistence.pojo.transfer.mysql.announcement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.egg.manager.common.util.str.MyStringUtil;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementDraft;
import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.pojo.dto.mysql.announcement.AnnouncementDraftDto;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserAccountTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementDraftMysqlVo;
import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AnnouncementDraftTransfer extends MyBaseMysqlTransfer {


    public static AnnouncementDraft transferVoToEntity(AnnouncementDraftMysqlVo announcementDraftVo) {
        if (announcementDraftVo == null) {
            return null;
        }
        AnnouncementDraft announcementDraft = new AnnouncementDraft();
        announcementDraft.setFid(announcementDraftVo.getFid());
        announcementDraft.setTitle(announcementDraftVo.getTitle());
        announcementDraft.setKeyWord(announcementDraftVo.getKeyWord());
        announcementDraft.setPublishDepartment(announcementDraftVo.getPublishDepartment());
        announcementDraft.setIsPublished(announcementDraftVo.getIsPublished());
        announcementDraft.setContent(announcementDraftVo.getContent());
        List<String> tagIds = announcementDraftVo.getTagIds();
        if (tagIds != null && tagIds.size() > 0) {
            announcementDraft.setTagIds(JSON.toJSONString(tagIds));
        }
        announcementDraft.setAccessory(announcementDraftVo.getAccessory());

        announcementDraft.setRemark(announcementDraftVo.getRemark());
        announcementDraft.setState(announcementDraftVo.getState());
        announcementDraft.setCreateTime(announcementDraftVo.getCreateTime());
        announcementDraft.setUpdateTime(announcementDraftVo.getUpdateTime());
        announcementDraft.setCreateUserId(announcementDraftVo.getCreateUserId());
        announcementDraft.setLastModifyerId(announcementDraftVo.getLastModifyerId());
        return announcementDraft;
    }

    public static AnnouncementDraftMysqlVo transferEntityToVo(AnnouncementDraft announcementDraft, Map<String, AnnouncementTag> announcementTagMap) {
        if (announcementDraft == null) {
            return null;
        }
        AnnouncementDraftMysqlVo announcementDraftVo = new AnnouncementDraftMysqlVo();
        announcementDraftVo.setFid(announcementDraft.getFid());
        announcementDraftVo.setTitle(announcementDraft.getTitle());
        announcementDraftVo.setKeyWord(announcementDraft.getKeyWord());
        announcementDraftVo.setPublishDepartment(announcementDraft.getPublishDepartment());
        String content = announcementDraft.getContent();
        announcementDraftVo.setContent(content);
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
        announcementDraftVo.setAccessory(announcementDraft.getAccessory());

        announcementDraftVo.setRemark(announcementDraft.getRemark());
        announcementDraftVo.setState(announcementDraft.getState());
        announcementDraftVo.setCreateTime(announcementDraft.getCreateTime());
        announcementDraftVo.setUpdateTime(announcementDraft.getUpdateTime());
        announcementDraftVo.setCreateUserId(announcementDraft.getCreateUserId());
        announcementDraftVo.setLastModifyerId(announcementDraft.getLastModifyerId());
        return announcementDraftVo;
    }


    public static AnnouncementDraftMysqlVo transferDtoToVo(AnnouncementDraftDto announcementDraftDto, Map<String, AnnouncementTag> announcementTagMap) {
        if (announcementDraftDto == null) {
            return null;
        }
        AnnouncementDraftMysqlVo announcementDraftVo = new AnnouncementDraftMysqlVo();
        announcementDraftVo.setFid(announcementDraftDto.getFid());
        announcementDraftVo.setTitle(announcementDraftDto.getTitle());
        announcementDraftVo.setKeyWord(announcementDraftDto.getKeyWord());
        announcementDraftVo.setPublishDepartment(announcementDraftDto.getPublishDepartment());
        String content = announcementDraftDto.getContent();
        announcementDraftVo.setContent(content);
        announcementDraftVo.setContent(content);
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
        announcementDraftVo.setAccessory(announcementDraftDto.getAccessory());
        announcementDraftVo.setIsPublished(announcementDraftDto.getIsPublished());

        announcementDraftVo.setRemark(announcementDraftDto.getRemark());
        announcementDraftVo.setState(announcementDraftDto.getState());
        announcementDraftVo.setCreateTime(announcementDraftDto.getCreateTime());
        announcementDraftVo.setUpdateTime(announcementDraftDto.getUpdateTime());
        announcementDraftVo.setCreateUserId(announcementDraftDto.getCreateUserId());
        announcementDraftVo.setLastModifyerId(announcementDraftDto.getLastModifyerId());
        announcementDraftVo.setCreateUser(UserAccountTransfer.transferEntityToVo(announcementDraftDto.getCreateUser()));
        announcementDraftVo.setLastModifyer(UserAccountTransfer.transferEntityToVo(announcementDraftDto.getLastModifyer()));
        return announcementDraftVo;
    }

    public static List<AnnouncementDraftMysqlVo> transferEntityToVoList(List<AnnouncementDraft> announcementDrafts, Map<String, AnnouncementTag> announcementTagMap) {
        if (announcementDrafts == null) {
            return null;
        } else {
            List<AnnouncementDraftMysqlVo> list = new ArrayList<>();
            for (AnnouncementDraft announcementDraft : announcementDrafts) {
                list.add(transferEntityToVo(announcementDraft, announcementTagMap));
            }
            return list;
        }
    }


    public static List<AnnouncementDraftMysqlVo> transferDtoToVoList(List<AnnouncementDraftDto> announcementDraftDtos, Map<String, AnnouncementTag> announcementTagMap) {
        if (announcementDraftDtos == null) {
            return null;
        } else {
            List<AnnouncementDraftMysqlVo> list = new ArrayList<>();
            for (AnnouncementDraftDto announcementDraftDto : announcementDraftDtos) {
                list.add(transferDtoToVo(announcementDraftDto, announcementTagMap));
            }
            return list;
        }
    }
}
