package com.egg.manager.persistence.dto.announcement;

import com.egg.manager.persistence.entity.user.UserAccount;
import lombok.*;

import java.util.Date;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/21
 * \* Time: 13:42
 * \* Description:
 * \
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnnouncementTagDto {


    private String fid ;
    private String name ;
    private String description ;
    private Integer ordering ;

    private Short state ;
    private String remark;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccount createUser ;
    private UserAccount lastModifyer;

}
