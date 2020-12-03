package com.egg.manager.persistence.obl.article.db.mysql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhoucj
 * @description 用户定义的收藏类别-Entity
 * @date 2020-12-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("obl_user_def_collect_category")
public class OblUserDefCollectCategoryEntity extends Model<OblUserDefCollectCategoryEntity> {
    @TableId(type = IdType.ASSIGN_ID, value = FieldConst.COL_FID)
    private String fid;
    /**
     * 名称
     */
    @TableField("name")
    private String name;
    /**
     * 描述
     */
    @TableField("description")
    private String description;
    /**
     * 权重值
     */
    @TableField("weights")
    private Integer weights;
    /**
     * 状态值
     */
    @TableField("state")
    private Short state;
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 版本号
     */
    @TableField("version")
    private Integer version;
    /**
     * 创建人id
     */
    @TableField("create_user_id")
    private String createUserId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 最后修改人id
     */
    @TableField("last_modifyer_id")
    private String lastModifyerId;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 是否已删除?0:否1:是
     */
    @TableField("is_deleted")
    private Short isDeleted;
    /**
     * 数据删除时间
     */
    @TableField("deleted_time")
    private Date deletedTime;


    @Override
    protected Serializable pkVal() {
        return this.fid;
    }
}