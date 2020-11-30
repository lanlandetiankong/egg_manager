package com.egg.manager.persistence.obl.article.pojo.dto;

import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.*;

import java.util.Date;

/**
 * @author zhoucj
 * @description 文章分类定义表-Dto
 * @date 2020-11-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblArticleCategoryDto extends MyBaseMysqlDto {
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


}