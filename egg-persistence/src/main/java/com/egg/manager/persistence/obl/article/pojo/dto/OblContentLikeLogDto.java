package com.egg.manager.persistence.obl.article.pojo.dto;

import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description 评论点赞表-Dto
 * @date 2020-12-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblContentLikeLogDto extends MyBaseMysqlDto {

    /**
     * 所属文章id
     */
    private String refArticleId;
    /**
     * 指定的评论id
     */
    private String refCommentId;
    /**
     * 操作用户id
     */
    private String operateUserId;


}