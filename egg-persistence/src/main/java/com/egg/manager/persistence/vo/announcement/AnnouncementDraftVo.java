package com.egg.manager.persistence.vo.announcement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.egg.manager.common.util.str.MyStringUtil;
import com.egg.manager.persistence.dto.announcement.AnnouncementDraftDto;
import com.egg.manager.persistence.entity.announcement.AnnouncementDraft;
import com.egg.manager.persistence.entity.announcement.AnnouncementTag;
import com.egg.manager.persistence.vo.user.UserAccountVo;
import com.google.common.base.Joiner;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/21
 * \* Time: 13:42
 * \* Description:
 * \
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnnouncementDraftVo {

    private String fid ;

    private String title ;
    private String keyWord ;    //关键字
    private String publishDepartment ;  //发布部门
    private String content ;
    private String shortContent ;   //概要的 公告内容
    private List<String> tagIds ; //公告标签 集合
    private List<String> tagNames ; //公告标签 集合
    private String tagNameOfStr ; //公告标签 集合转字符串
    private String accessory ;      //附件
    private Short isPublished ;      //是否已提交

    private String remark;
    private Short state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;




}
