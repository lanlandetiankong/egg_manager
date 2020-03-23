package com.egg.manager.persistence.entity.organization;

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
@TableName("em_define_tenant")
public class DefineTenant extends Model<DefineTenant> {

    @TableId
    private String fid ;

    private String name ;
    private String code ;
    @TableField("db_code")
    private String dbCode ;

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
    ;

    @Override
    protected Serializable pkVal() {
        return this.fid;
    }
}
