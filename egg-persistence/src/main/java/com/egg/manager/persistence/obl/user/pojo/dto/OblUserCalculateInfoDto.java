package com.egg.manager.persistence.obl.user.pojo.dto;

import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhoucj
 * @description 用户的计算信息-Dto
 * @date 2020-12-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblUserCalculateInfoDto extends MyBaseMysqlDto {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 文章总数
     */
    private Integer totalArticle;
    /**
     * 已审核文章总数
     */
    private String totalAuditedArticle;
    /**
     * 文章被收藏总次数
     */
    private Integer totalArticleBeCollect;
    /**
     * 文章上推荐总次数
     */
    private Integer totalArticleRecommendTimes;
    /**
     * 文章被点赞总次数
     */
    private Integer totalArticleBeLike;
    /**
     * 文章被踩低总次数
     */
    private Integer totalArticleBeDislike;
    /**
     * 文章总被评论数
     */
    private Integer totalArticleComment;
    /**
     * 文章原创数量
     */
    private Integer totalArticleOriginalType;
    /**
     * 文章转载数量
     */
    private Integer totalArticleReprintType;

}