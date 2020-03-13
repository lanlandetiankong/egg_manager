package com.egg.manager.dto.define;

import com.egg.manager.entity.define.DefineMenu;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.vo.define.DefineMenuVo;
import com.egg.manager.vo.user.UserAccountVo;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DefineMenuDto {
    private String fid ;
    private String parentId ;

    private DefineMenuDto parentMenuDto ;

    private String menuName ;
    private Integer urlJumpType;
    private String iconName ;
    private String routerUrl ;
    private String hrefUrl ;
    private String label ;
    private Integer level ;
    private Integer orderNum ;

    private Integer state ;
    private String remark ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccount createUser ;
    private UserAccount lastModifyer;


}
