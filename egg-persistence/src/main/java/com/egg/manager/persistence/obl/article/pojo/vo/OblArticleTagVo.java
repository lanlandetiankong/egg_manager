package com.egg.manager.persistence.obl.article.pojo.vo;

import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.*;

import java.util.Date;

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
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;

}