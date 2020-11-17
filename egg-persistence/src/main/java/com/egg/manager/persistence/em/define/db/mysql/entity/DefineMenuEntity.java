package com.egg.manager.persistence.em.define.db.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.egg.manager.persistence.commons.base.enums.module.DefineMenuUrlJumpTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhoucj
 * @description 菜单定义
 * @date 2020/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("em_define_menu")
public class DefineMenuEntity extends Model<DefineMenuEntity> {
    @TableId(type=IdType.ASSIGN_ID,value = "fid")
    private String fid;
    /**
     * 上级id
     */
    @TableField(value = "parent_id")
    private String parentId;
    /**
     * 菜单名称
     */
    @TableField(value = "menu_name")
    private String menuName;
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
     * excel配置信息
     */
    @TableField("excel_model_conf")
    private String excelModelConf;


    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 状态
     */
    @TableField(value = "state", fill = FieldFill.INSERT)
    private Short state;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
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
    /**
     * 版本号
     */
    @Version
    @TableField(value = "version")
    private Integer version;
    /**
     * 是否已经删除，0:否 1:是
     */
    @TableLogic
    @TableField(value = "is_deleted")
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
