package com.egg.manager.entity.organization;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
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

    private Integer state ;
    @TableField("create_time")
    private Date createTime ;
    @TableField("update_time")
    private Date updateTime ;

    @TableField(value = "create_user")
    private String createUser ;
    @TableField(value = "last_modifyer")
    private String lastModifyer;
    private String remark ;

    @Override
    protected Serializable pkVal() {
        return this.fid;
    }
}
