package com.egg.manager.persistence.obl.article.pojo.vo;

import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description 文章分类关联表-Vo
 * @date 2020-11-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblArticleCategoryRelatedVo extends MyBaseMysqlVo {
    private static final long serialVersionUID = -7130564289998493789L;
    /**
     * 文章id
     */
    private String articleId;
    /**
     * 类别id
     */
    private String categoryId;
    /**
     * 级别
     */
    private Integer level;
    /**
     * 权重值
     */
    private Integer weights;
}