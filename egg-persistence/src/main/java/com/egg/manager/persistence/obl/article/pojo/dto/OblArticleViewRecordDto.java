package com.egg.manager.persistence.obl.article.pojo.dto;

import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhoucj
 * @description 文章查看记录-Dto
 * @date 2020-12-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblArticleViewRecordDto extends MyBaseMysqlDto {
    private static final long serialVersionUID = -81516071414687793L;

    /**
     * 文章id
     */
    private String articleId;
    /**
     * 查看人id
     */
    private String viewUserId;

}