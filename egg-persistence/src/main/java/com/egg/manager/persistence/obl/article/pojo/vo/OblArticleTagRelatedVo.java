package com.egg.manager.persistence.obl.article.pojo.vo;

import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.*;

import java.util.Date;

/**
 * @author zhoucj
 * @description 文章与标签关联表-Vo
 * @date 2020-11-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblArticleTagRelatedVo extends MyBaseMysqlVo {
    /**
     * 文章id
     */
    private String articleId;
    /**
     * 标签id
     */
    private String tagId;

}