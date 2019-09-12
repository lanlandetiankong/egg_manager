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
@TableName("define_menu")
public class DefineMenu extends BaseEntity {
    private String defineModuleId ;
    private String menuName ;




}
