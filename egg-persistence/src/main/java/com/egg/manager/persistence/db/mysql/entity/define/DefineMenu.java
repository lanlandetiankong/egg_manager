package com.egg.manager.persistence.db.mysql.entity.define;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.egg.manager.common.base.enums.module.DefineMenuUrlJumpTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单定义-entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("em_define_menu")
public class DefineMenu extends Model<DefineMenu> {
    @TableId
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
     *
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
    private String label;
    /**
     * 层级
     */
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
    private String remark;
    /**
     * 状态
     */
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
