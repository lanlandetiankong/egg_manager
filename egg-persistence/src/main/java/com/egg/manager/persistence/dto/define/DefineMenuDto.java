package com.egg.manager.persistence.dto.define;

import com.egg.manager.persistence.dto.MyBaseDto;
import com.egg.manager.persistence.entity.user.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefineMenuDto extends MyBaseDto {
    private String fid;
    private String parentId;

    private DefineMenuDto parentMenuDto;

    private String menuName;
    private Integer urlJumpType;
    private String iconName;
    private String routerUrl;
    private String hrefUrl;
    private String label;
    private Integer level;
    private Integer orderNum;
    private String excelModelConf;

    private Short state;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private String createUserId;
    private String lastModifyerId;
    private UserAccount createUser;
    private UserAccount lastModifyer;


}
