package com.egg.manager.entity.module;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("em_define_module")
public class DefineModule extends Model<DefineModule> {
    @TableId
    private String fid ;
    private String name ;
    private String code ;
    private String icon ;
    private String style ;
    private Integer type;

    private String remark ;
    private Integer state ;
    @TableField("create_time")
    private Date createTime ;
    @TableField("update_time")
    private Date updateTime ;
    @TableField(value = "create_user_id")
    private String createUserId ;
    @TableField(value = "last_modifyer_id")
    private String lastModifyerId;

    @Override
    protected Serializable pkVal() {
        return this.fid;
    }


}
