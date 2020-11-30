package com.egg.manager.persistence.obl.article.pojo.dto;

import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.*;

import java.util.Date;

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
    /**
     * 文章id
     */
    private String articleId;
    /**
     * 标签id
     */
    private String tagId;

}