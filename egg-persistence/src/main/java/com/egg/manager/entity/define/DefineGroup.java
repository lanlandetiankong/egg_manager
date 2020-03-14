package com.egg.manager.entity.define;

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
@TableName("em_define_group")
public class DefineGroup extends Model<DefineGroup> {

    @TableId
    private String fid ;

    private String name ;
    private String pid ;
    @TableField(value="is_inherit")
    private Integer isInherit ; //是否成员可继承组权限
    private String type;

    private String remark;
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
