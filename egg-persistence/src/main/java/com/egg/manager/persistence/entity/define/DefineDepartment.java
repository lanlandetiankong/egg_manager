package com.egg.manager.persistence.entity.define;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/3/5
 * \* Time: 19:47
 * \* Description:
 * \
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("em_define_department")
public class DefineDepartment extends Model<DefineDepartment> {
    @TableId
    private String fid ;

    private String name ;
    private String code ;
    @TableField("parent_id")
    private String parentId ;
    private Integer level ;
    @TableField("order_num")
    private Integer orderNum ;
    private String description ;

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
