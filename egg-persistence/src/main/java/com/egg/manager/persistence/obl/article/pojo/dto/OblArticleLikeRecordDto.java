package com.egg.manager.persistence.obl.article.pojo.dto;

import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description 文章点赞表-Dto
 * @date 2020-12-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblArticleLikeRecordDto extends MyBaseMysqlDto {

    private static final long serialVersionUID = 5604523563758154875L;
    /**
     * 文章id
     */
    private String articleId;
    /**
     * 操作用户id
     */
    private String operateUserId;
    /**
     * 态度(点赞或踩低)
     */
    private Short attitude;

}