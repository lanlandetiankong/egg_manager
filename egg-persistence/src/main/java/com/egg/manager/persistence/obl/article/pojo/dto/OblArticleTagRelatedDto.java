package com.egg.manager.persistence.obl.article.pojo.dto;

import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description 文章与标签关联表-Dto
 * @date 2020-11-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblArticleTagRelatedDto extends MyBaseMysqlDto {
    private static final long serialVersionUID = -8084678645142971471L;
    /**
     * 文章id
     */
    private String articleId;
    /**
     * 标签id
     */
    private String tagId;

}