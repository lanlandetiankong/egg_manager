package com.egg.manager.vo.define;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DefineModuleVo {
    private String fid ;
    private String moduleId ;
    private String parentId ;
    private String menuName ;
    private String type;
    private Integer version ;
    private Integer state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUser ;
    private String lastModifyer;


}
