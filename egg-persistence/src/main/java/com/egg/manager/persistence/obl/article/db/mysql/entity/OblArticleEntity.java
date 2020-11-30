package com.egg.manager.persistence.obl.article.db.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("obl_article")
public class OblArticleEntity extends Model<OblArticleEntity> {

    private static final long serialVersionUID = 5207865247400761539L;

    @TableId(type = IdType.ASSIGN_ID, value = FieldConst.COL_FID)
    private String fid;
    /**
     * 作者id
     */
    @TableField("fid")
    private String authorId;
    /**
     * 文章标题
     */
    @TableField("title")
    private String title;
    /**
     * 文章内容
     */
    @TableField("content")
    private String content;
    /**
     * 概要
     */
    @TableField("summary")
    private String summary;
    /**
     * 文章浏览数
     */
    @TableField("view_count")
    private Integer viewCount;
    /**
     * 文章评论数
     */
    @TableField("comment_count")
    private Integer commentCount;
    /**
     * 文章点赞数
     */
    @TableField("like_count")
    private Integer likeCount;


    /**
     * 备注
     */
    @TableField(FieldConst.COL_REMARK)
    private String remark;
    /**
     * 状态
     */
    @TableField(value = FieldConst.COL_STATE, fill = FieldFill.INSERT)
    private Short state;
    /**
     * 创建时间
     */
    @TableField(value = FieldConst.COL_CREATE_TIME, fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(value = FieldConst.COL_UPDATE_TIME, fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 创建用户id
     */
    @TableField(value = FieldConst.COL_CREATE_USER_ID)
    private String createUserId;
    /**
     * 最后修改用户id
     */
    @TableField(value = FieldConst.COL_LAST_MODIFYER_ID)
    private String lastModifyerId;
    /**
     * 版本号
     */
    @Version
    @TableField(value = FieldConst.COL_VERSION)
    private Integer version;
    /**
     * 是否已经删除，0:否 1:是
     */
    @TableLogic
    @TableField(value = FieldConst.COL_IS_DELETED)
    private short isDeleted;
    /**
     * 数据删除时间
     */
    @TableField(value = "deleted_time")
    private Date deletedTime;

    @Override
    protected Serializable pkVal() {
        return this.fid;
    }


}