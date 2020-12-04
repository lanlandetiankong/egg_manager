package com.egg.manager.persistence.obl.blconf.pojo.dto;

import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description 博客通知表-Dto
 * @date 2020-11-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblBlogNoticeDto extends MyBaseMysqlDto {
    private static final long serialVersionUID = -9212254473788874564L;
    /**
     * 标题
     */
    private String title;
    private String content;
}