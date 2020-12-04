package com.egg.manager.persistence.obl.article.pojo.vo;

import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description 文章点赞表-Vo
 * @date 2020-12-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblArticleLikeLogVo extends MyBaseMysqlVo {
    private static final long serialVersionUID = -3419597215946638830L;
    /**
     * 文章id
     */
    private String articleId;
    /**
     * 操作用户id
     */
    private String operateUserId;
    /**
     * 态度(点赞或踩低)
     */
    private Short attitude;


}