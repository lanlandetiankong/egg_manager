package com.egg.manager.persistence.pojo.dto.announcement;

import com.egg.manager.persistence.pojo.dto.MyBaseDto;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDto extends MyBaseDto {

    private String fid;

    private String title;
    private String keyWord;    //关键字
    private String publishDepartment;  //发布部门
    private String content;
    private String tagIds; //公告标签 集合
    private String accessory;      //附件

    private Short state;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private String createUserId;
    private String lastModifyerId;
    private UserAccount createUser;
    private UserAccount lastModifyer;


}
