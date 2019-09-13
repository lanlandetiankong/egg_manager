package com.egg.manager.entity.define;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.egg.manager.entity.user.UserRole;
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
    private Integer isInherit ; //是否成员可继承组权限


    private String type;
    @Version
    private Integer version ;
    private Integer state ;
    @TableField("create_time")
    private Date createTime ;
    @TableField("update_time")
    private Date updateTime ;



    @Override
    protected Serializable pkVal() {
        return this.fid;
    }

}
