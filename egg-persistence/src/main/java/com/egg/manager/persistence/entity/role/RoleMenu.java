package com.egg.manager.persistence.entity.role;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.entity.user.UserAccount;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("em_role_menu")
public class RoleMenu extends Model<RoleMenu> {
    @TableId
    private String fid ;


    @TableField(value = "define_role_id")
    private  String defineRoleId ;
    @TableField(value = "define_menu_id")
    private  String defineMenuId ;
    private Integer type;

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


    /**
     * 返回一个通用的 entity实例
     * @param defineRoleId
     * @param defineMenuId
     * @param loginUser 当前登录用户
     * @return
     */
    public static RoleMenu generateSimpleInsertEntity( String defineRoleId,String defineMenuId, UserAccount loginUser){

        return RoleMenu.generateSimpleInsertEntity(defineRoleId,defineMenuId,BaseStateEnum.ENABLED.getValue(),loginUser) ;
    }
    /**
     * 返回一个通用的 entity实例
     * @param defineRoleId
     * @param defineMenuId
     * @param loginUser 当前登录用户
     * @return
     */
    public static RoleMenu generateSimpleInsertEntity( String defineRoleId,String defineMenuId,Integer stateVal,UserAccount loginUser){
        RoleMenu userRole = new RoleMenu() ;
        Date now = new Date() ;
        userRole.setFid(MyUUIDUtil.renderSimpleUUID());
        userRole.setDefineRoleId(defineRoleId);
        userRole.setDefineMenuId(defineMenuId);
        userRole.setType(1);
        userRole.setState(stateVal);
        userRole.setCreateTime(now);
        userRole.setUpdateTime(now);
        if(loginUser != null){
            userRole.setCreateUserId(loginUser.getFid());
            userRole.setLastModifyerId(loginUser.getFid());
        }
        return userRole ;
    }
}
