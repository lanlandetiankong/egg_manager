package com.egg.manager.persistence.db.mysql.entity.define;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 部门定义-entity
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/3/5
 * \* Time: 19:47
 * \* Description:
 * \
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("em_define_department")
public class DefineDepartment extends Model<DefineDepartment> {
    @TableId(value = "fid")
    private String fid;
    /**
     * 名称
     */
    @TableField("name")
    private String name;
    /**
     * 编码
     */
    @TableField("code")
    private String code;
    /**
     * 上级id
     */
    @TableField("parent_id")
    private String parentId;
    /**
     * 层级
     */
    @TableField("level")
    private Integer level;
    /**
     * 排序值
     */
    @TableField("order_num")
    private Integer orderNum;
    /**
     * 描述
     */
    @TableField("description")
    private String description;


    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 状态
     */
    @TableField("state")
    private Short state;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 创建用户id
     */
    @TableField(value = "create_user_id")
    private String createUserId;
    /**
     * 最后修改用户id
     */
    @TableField(value = "last_modifyer_id")
    private String lastModifyerId;

    @Override
    protected Serializable pkVal() {
        return this.fid;
    }
}
