package com.egg.manager.persistence.pojo.vo.announcement;

import com.egg.manager.persistence.pojo.vo.MyBaseVo;
import com.egg.manager.persistence.pojo.vo.user.UserAccountVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementVo extends MyBaseVo {

    private String fid;

    private String title;
    private String keyWord;    //关键字
    private String publishDepartment;  //发布部门
    private String content;
    private String shortContent;   //概要的 公告内容
    private List<String> tagIds; //公告标签 集合
    private List<String> tagNames; //公告标签 集合
    private String tagNameOfStr; //公告标签 集合转字符串
    private String accessory;      //附件

    private String remark;
    private Short state;
    private Date createTime;
    private Date updateTime;
    private String createUserId;
    private String lastModifyerId;
    private UserAccountVo createUser;
    private UserAccountVo lastModifyer;


}
