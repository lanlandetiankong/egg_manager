package com.egg.manager.entity.define;

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
@TableName("em_define_menu")
public class DefineMenu extends Model<DefineMenu> {
    @TableId
    private String fid ;

    @TableField(value="parent_id")
    private String parentId ;
    @TableField(value="menu_name")
    private String menuName ;
    @TableField(value="url_jump_type")
    private Integer urlJumpType;
    @TableField(value="icon_name")
    private String iconName ;
    @TableField(value="router_url")
    private String routerUrl ;
    @TableField(value="href_url")
    private String hrefUrl ;
    private String label ;
    private Integer level ;
    @TableField("order_num")
    private Integer orderNum ;


    private Integer state ;
    private String remark ;
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
