package com.egg.manager.persistence.pojo.dto.announcement;

import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.dto.MyBaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/21
 * \* Time: 13:42
 * \* Description:
 * \
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDraftDto extends MyBaseDto {

    private String fid;

    /**
     * 标题
     */
    private String title;
    /**
     * 关键字
     */
    private String keyWord;
    /**
     * 发布部门
     */
    private String publishDepartment;
    /**
     * 内容
     */
    private String content;
    /**
     * 公告标签 集合
     */
    private String tagIds;
    /**
     * 附件
     */
    private String accessory;
    /**
     * 是否已提交
     */
    private short isPublished;


    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    private Short state;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 创建用户id
     */
    private String createUserId;
    /**
     * 最后修改用户id
     */
    private String lastModifyerId;
    /**
     * 概要的 公告内容
     */
    private String shortContent;
    /**
     * 公告标签 集合
     */
    private List<String> tagNames;
    /**
     * 公告标签 集合转字符串
     */
    private String tagNameOfStr;

    /**
     * 创建人-vo
     */
    private UserAccount createUser;
    /**
     * 最后更新人-vo
     */
    private UserAccount lastModifyer;


}
