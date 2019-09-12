package com.egg.manager.entity.define;

import com.baomidou.mybatisplus.annotations.TableName;
import com.egg.manager.entity.BaseEntity;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("define_group")
public class DefineGroup extends BaseEntity {
    private String name ;
    private String pid ;
    private Integer isInherit ; //是否成员可继承组权限

}
