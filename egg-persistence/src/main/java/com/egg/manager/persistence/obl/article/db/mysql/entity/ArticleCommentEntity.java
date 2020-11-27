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
 * 文章评论
 * @author liuyanzhao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("obl_article_comment")
public class ArticleCommentEntity extends Model<ArticleCommentEntity> {

    private static final long serialVersionUID = -1038897351672911219L;

    @TableId(type = IdType.ASSIGN_ID, value = FieldConst.COL_FID)
    private String fid;

    /**
     * 上级评论id
     */
    @TableField("pid")
    private String pid;
    /**
     * 文章id
     */
    @TableField("article_id")
    private String articleId;
    /**
     * 评论人
     */
    @TableField("reply_from_user_id")
    private String replyFromUserId;

    /**
     * 指定被评论人 -> @
     */
    @TableField("reply_to_user_id")
    private String replyToUserId;

    /**
     * 评论内容
     */
    @TableField("content")
    private String content;
    /**
     * ip地址
     */
    @TableField("ip_addr")
    private String ipAddr ;

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