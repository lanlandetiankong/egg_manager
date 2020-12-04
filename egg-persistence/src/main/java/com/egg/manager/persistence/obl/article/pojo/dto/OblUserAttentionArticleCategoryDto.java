package com.egg.manager.persistence.obl.article.pojo.dto;

import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description 用户关注的文章收藏类别-Dto
 * @date 2020-12-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblUserAttentionArticleCategoryDto extends MyBaseMysqlDto {
    private static final long serialVersionUID = -2254019070549606883L;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 文章收藏类别id
     */
    private String articleCategoryId;
    /**
     * 权重值
     */
    private Integer weights;


}