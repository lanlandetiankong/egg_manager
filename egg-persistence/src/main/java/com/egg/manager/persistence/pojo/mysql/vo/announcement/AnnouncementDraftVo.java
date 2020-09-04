package com.egg.manager.persistence.pojo.mysql.vo.announcement;

import com.egg.manager.persistence.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/21
 * \* Time: 13:42
 * \* Description:
 * \
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDraftVo extends MyBaseMysqlVo {

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
    private List<String> tagIds;
    /**
     * 附件
     */
    private String accessory;
    /**
     * 是否已提交
     */
    private short isPublished;

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
