package com.egg.manager.persistence.obl.blconf.db.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.egg.manager.persistence.em.define.domain.enums.DefineMenuUrlJumpTypeEnum;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("obl_blog_menu_conf")
public class OblBlogMenuConfEntity extends Model<OblBlogMenuConfEntity> {
    private static final long serialVersionUID = 489914127235951698L;

    @TableId(type = IdType.ASSIGN_ID, value = FieldConst.COL_FID)
    private String fid;
    /**
     * 菜单名
     */
    @TableField("name")
    private String name;
    /**
     * 级别
     */
    @TableField("level")
    private Integer level;
    /**
     * 路径跳转方式
     * @see DefineMenuUrlJumpTypeEnum
     */
    @TableField(value = "url_jump_type")
    private Integer urlJumpType;
    /**
     * 图标名称
     */
    @TableField(value = "icon_name")
    private String iconName;
    /**
     * 路由跳转
     */
    @TableField(value = "router_url")
    private String routerUrl;
    /**
     * 外部跳转路径
     */
    @TableField(value = "href_url")
    private String hrefUrl;
    /**
     * 标签名
     */
    @TableField("label")
    private String label;


    /**
     * 备注
     */
    @TableField(FieldConst.COL_REMARK)
    private String remark;
    /**
     * 状态
     */
    @TableField(value = FieldConst.COL_STATE, fill = FieldFill.INSERT)
    private Short state;
    /**
     * 创建时间
     */
    @TableField(value = FieldConst.COL_CREATE_TIME, fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(value = FieldConst.COL_UPDATE_TIME, fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 创建用户id
     */
    @TableField(value = FieldConst.COL_CREATE_USER_ID)
    private String createUserId;
    /**
     * 最后修改用户id
     */
    @TableField(value = FieldConst.COL_LAST_MODIFYER_ID)
    private String lastModifyerId;
    /**
     * 版本号
     */
    @Version
    @TableField(value = FieldConst.COL_VERSION)
    private Integer version;
    /**
     * 是否已经删除，0:否 1:是
     */
    @TableLogic
    @TableField(value = FieldConst.COL_IS_DELETED)
    private short isDeleted;
    /**
     * 数据删除时间
     */
    @TableField(value = "deleted_time")
    private Date deletedTime;

    @Override
    protected Serializable pkVal() {
        return this.fid;
    }

}