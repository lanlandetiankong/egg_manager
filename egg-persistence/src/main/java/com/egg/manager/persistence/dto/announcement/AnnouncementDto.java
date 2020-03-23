package com.egg.manager.persistence.dto.announcement;

import com.egg.manager.persistence.entity.user.UserAccount;
import lombok.*;

import java.util.Date;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnnouncementDto {

    private String fid ;

    private String title ;
    private String keyWord ;    //关键字
    private String publishDepartment ;  //发布部门
    private String content ;
    private String tagIds ; //公告标签 集合
    private String accessory ;      //附件

    private Integer state ;
    private String remark;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccount createUser ;
    private UserAccount lastModifyer;


}
