package com.egg.manager.persistence.obl.article.pojo.vo;

import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description 文章评论表-Vo
 * @date 2020-11-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblArticleCommentVo extends MyBaseMysqlVo {
    private static final long serialVersionUID = 4479959868420767495L;
    /**
     * 上级id
     */
    private String pid;
    /**
     * 文章id
     */
    private String articleId;
    /**
     * 评论发起人
     */
    private String replyFromUserId;
    /**
     * 评论指定人@
     */
    private String replyToUserId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * ip地址
     */
    private String ipAddr;


}