package com.egg.manager.persistence.obl.article.pojo.vo;

import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description 文章表-Vo
 * @date 2020-11-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblArticleVo extends MyBaseMysqlVo {
    /**
     * 作者id
     */
    private String authorId;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 概要
     */
    private String summary;
    /**
     * 文章浏览数
     */
    private Integer viewCount;
    /**
     * 文章评论数
     */
    private Integer commentCount;
    /**
     * 文章点赞数
     */
    private Integer likeCount;

}