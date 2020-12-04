package com.egg.manager.persistence.obl.article.pojo.vo;

import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhoucj
 * @description 文章查看记录-Vo
 * @date 2020-12-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblArticleViewRecordVo extends MyBaseMysqlVo {
    private static final long serialVersionUID = 815060559449155295L;

    /**
     * 文章id
     */
    private String articleId;
    /**
     * 查看人id
     */
    private String viewUserId;



}