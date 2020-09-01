package com.egg.manager.persistence.pojo.mapstruct.mysql.vo.announcement;

import com.egg.manager.persistence.db.mysql.entity.announcement.AnnouncementDraft;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.MyBaseMysqlVoMapstruct;
import com.egg.manager.persistence.pojo.vo.mysql.announcement.AnnouncementDraftMysqlVo;
import org.apache.poi.ss.formula.functions.T;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnnouncementDraftVoMapstruct extends MyBaseMysqlVoMapstruct<T> {

    @Mapping(source = "tagIds",target = "tagIds")
    AnnouncementDraft vo_CopyTo_Entity(AnnouncementDraftMysqlVo vo);
}
