package com.egg.manager.persistence.db.mysql.entity.announcement;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告草稿-entity
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
@TableName("em_announcement_draft")
public class AnnouncementDraft extends Model<AnnouncementDraft> {

    @TableId(value = "fid")
    private String fid;

    /**
     * 标题
     */
    @TableField("title")
    private String title;
    /**
     * 关键字
     */
    @TableField("key_word")
    private String keyWord;
    /**
     * 发布部门
     */
    @TableField("publish_department")
    private String publishDepartment;
    /**
     * 内容
     */
    @TableField("content")
    private String content;
    /**
     * 公告标签 集合
     */
    @TableField("tag_ids")
    private String tagIds;
    /**
     * 附件
     */
    @TableField("accessory")
    private String accessory;
    /**
     * 是否已提交
     */
    @TableField("is_published")
    private short isPublished;


    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 状态
     */
    @TableField(value = "state",fill = FieldFill.INSERT)
    private Short state;
    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 创建用户id
     */
    @TableField(value = "create_user_id")
    private String createUserId;
    /**
     * 最后修改用户id
     */
    @TableField(value = "last_modifyer_id")
    private String lastModifyerId;
    /**
     * 版本号
     */
    @Version
    @TableField(value = "version")
    private Integer version ;

    @Override
    protected Serializable pkVal() {
        return this.fid;
    }

}
