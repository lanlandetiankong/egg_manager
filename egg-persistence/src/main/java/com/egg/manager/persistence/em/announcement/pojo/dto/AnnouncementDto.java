package com.egg.manager.persistence.em.announcement.pojo.dto;

import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.*;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AnnouncementDto extends MyBaseMysqlDto {

    /**
     * 标题
     */
    private String title;
    /**
     * 关键字
     */
    private String keyWord;
    /**
     * 发布部门
     */
    private String publishDepartment;
    /**
     * 内容
     */
    private String content;
    /**
     * 公告标签 集合
     */
    private String tagIds;
    /**
     * 附件
     */
    private String accessory;
    /**
     * 概要的 公告内容
     */
    private String shortContent;
    /**
     * 公告标签 集合
     */
    private List<String> tagNames;
    /**
     * 公告标签 集合转字符串
     */
    private String tagNameOfStr;

}
