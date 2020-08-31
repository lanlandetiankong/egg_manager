package com.egg.manager.persistence.pojo.dto.mysql.announcement;

import com.egg.manager.persistence.pojo.dto.mysql.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementMysqlDto extends MyBaseMysqlDto {

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
