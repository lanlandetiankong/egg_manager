package com.egg.manager.persistence.obl.article.pojo.vo;

import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description 文章标签定义表-Vo
 * @date 2020-11-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblArticleTagVo extends MyBaseMysqlVo {
    private static final long serialVersionUID = 8264369870497325809L;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 权重值
     */
    private Integer weights;
}