package com.egg.manager.vo.define;


import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DefineRoleVo {
    private String fid ;
    private String name ;
    private String code ;
    private String type;
    private Integer version ;
    private Integer state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUser ;
    private String lastModifyer;

}
