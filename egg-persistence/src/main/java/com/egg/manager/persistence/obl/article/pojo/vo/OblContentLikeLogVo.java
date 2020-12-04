package com.egg.manager.persistence.obl.article.pojo.vo;

import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description 评论点赞表-Vo
 * @date 2020-12-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblContentLikeLogVo extends MyBaseMysqlVo {
    private static final long serialVersionUID = -298985667691447856L;
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