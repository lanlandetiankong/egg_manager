package com.egg.manager.persistence.obl.article.pojo.vo;

import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description 文章分类定义表-Vo
 * @date 2020-11-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblArticleCategoryVo extends MyBaseMysqlVo {
    private static final long serialVersionUID = -3443296501734576823L;
    /**
     * 父级id
     */
    private String pid;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 图标名
     */
    private String iconName;
    /**
     * 级别
     */
    private Integer level;
    /**
     * 权重值
     */
    private Integer weights;
}