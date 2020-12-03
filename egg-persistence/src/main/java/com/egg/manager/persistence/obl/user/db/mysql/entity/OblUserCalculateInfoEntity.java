package com.egg.manager.persistence.obl.user.db.mysql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhoucj
 * @description 用户的计算信息-Entity
 * @date 2020-12-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("obl_user_calculate_info")
public class OblUserCalculateInfoEntity extends Model<OblUserCalculateInfoEntity> {
    @TableId(type = IdType.ASSIGN_ID, value = FieldConst.COL_FID)
    private String fid;
    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;
    /**
     * 文章总数
     */
    @TableField("total_article")
    private Integer totalArticle;
    /**
     * 已审核文章总数
     */
    @TableField("total_audited_article")
    private String totalAuditedArticle;
    /**
     * 文章被收藏总次数
     */
    @TableField("total_article_be_collect")
    private Integer totalArticleBeCollect;
    /**
     * 文章上推荐总次数
     */
    @TableField("total_article_recommend_times")
    private Integer totalArticleRecommendTimes;
    /**
     * 文章被点赞总次数
     */
    @TableField("total_article_be_like")
    private Integer totalArticleBeLike;
    /**
     * 文章被踩低总次数
     */
    @TableField("total_article_be_dislike")
    private Integer totalArticleBeDislike;
    /**
     * 文章总被评论数
     */
    @TableField("total_article_comment")
    private Integer totalArticleComment;
    /**
     * 文章原创数量
     */
    @TableField("total_article_original_type")
    private Integer totalArticleOriginalType;
    /**
     * 文章转载数量
     */
    @TableField("total_article_reprint_type")
    private Integer totalArticleReprintType;
    /**
     * 状态值
     */
    @TableField("state")
    private Short state;
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 版本号
     */
    @TableField("version")
    private Integer version;
    /**
     * 创建人id
     */
    @TableField("create_user_id")
    private String createUserId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 最后修改人id
     */
    @TableField("last_modifyer_id")
    private String lastModifyerId;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 是否已删除?0:否1:是
     */
    @TableField("is_deleted")
    private Short isDeleted;
    /**
     * 数据删除时间
     */
    @TableField("deleted_time")
    private Date deletedTime;


    @Override
    protected Serializable pkVal() {
        return this.fid;
    }
}