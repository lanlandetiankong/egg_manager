package com.egg.manager.entity.user;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.entity.role.RolePermission;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("em_user_role")
public class UserRole extends Model<UserRole> {
    @TableId
    private String fid ;

    @TableField(value = "user_account_id")
    private String userAccountId ;
    @TableField(value = "define_role_id")
    private  String defineRoleId ;
    private Integer type;
    @Version
    private Integer version ;
    private Integer state ;
    @TableField("create_time")
    private Date createTime ;
    @TableField("update_time")
    private Date updateTime ;

    @TableField(value = "create_user")
    private String createUser ;
    @TableField(value = "last_modifyer")
    private String lastModifyer;
    private String remark;

    @Override
    protected Serializable pkVal() {
        return this.fid;
    }


    /**
     * 返回一个通用的 entity实例
     * @param userAccountId
     * @param defineRoleId
     * @param loginUser 当前登录用户
     * @return
     */
    public static UserRole generateSimpleInsertEntity(String userAccountId, String defineRoleId, UserAccount loginUser){
        UserRole userRole = new UserRole() ;
        Date now = new Date() ;
        userRole.setFid(MyUUIDUtil.renderSimpleUUID());
        userRole.setUserAccountId(userAccountId);
        userRole.setDefineRoleId(defineRoleId);
        userRole.setType(1);
        userRole.setVersion(0);
        userRole.setState(BaseStateEnum.ENABLED.getValue());
        userRole.setCreateTime(now);
        userRole.setUpdateTime(now);
        if(loginUser != null){
            userRole.setCreateUser(loginUser.getFid());
            userRole.setLastModifyer(loginUser.getFid());
        }
        return userRole ;
    }
}
