package com.egg.manager.persistence.obl.blconf.pojo.dto;

import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.*;

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
    /**
     * 标题
     */
    private String title;
    private String content;
}