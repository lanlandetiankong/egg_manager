package com.egg.manager.persistence.em.define.db.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import com.egg.manager.persistence.em.define.domain.enums.DefineMenuUrlJumpTypeEnum;
import lombok.*;

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
@EqualsAndHashCode(callSuper = true)
@TableName("em_define_menu")
public class EmDefineMenuEntity extends Model<EmDefineMenuEntity> {
    private static final long serialVersionUID = -1395581740368471216L;
    @TableId(type = IdType.ASSIGN_ID, value = FieldConst.COL_FID)
    private String fid;
    /**
     * 上级id
     */
    @TableField(value = "pid")
    private String pid;
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
     * 权重值
     */
    @TableField("weights")
    private Integer weights;
    /**
     * excel配置信息
     */
    @TableField("excel_model_conf")
    private String excelModelConf;


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
    @TableField(value = FieldConst.COL_DELETE_TIME)
    private Date deletedTime;

    @Override
    protected Serializable pkVal() {
        return this.fid;
    }


}
