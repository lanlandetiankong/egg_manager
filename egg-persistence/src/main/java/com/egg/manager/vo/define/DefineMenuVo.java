package com.egg.manager.vo.define;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DefineMenuVo {
    private String fid ;
    private String defineModuleId ;
    private String parentId ;
    private String menuName ;
    private String type;
    private String iconName ;
    private String routerUrl ;
    private String label ;
    private Integer level ;
    private Integer version ;
    private Integer state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUser ;
    private String lastModifyer;

}
