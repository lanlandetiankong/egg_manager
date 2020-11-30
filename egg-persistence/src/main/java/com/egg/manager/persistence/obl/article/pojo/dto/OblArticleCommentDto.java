package com.egg.manager.persistence.obl.article.pojo.dto;

import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.*;

import java.util.Date;

/**
 * @author zhoucj
 * @description 文章评论表-Dto
 * @date 2020-11-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblArticleCommentDto extends MyBaseMysqlDto {
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