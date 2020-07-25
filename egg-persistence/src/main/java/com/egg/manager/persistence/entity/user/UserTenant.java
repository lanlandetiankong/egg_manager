package com.egg.manager.persistence.entity.user;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("em_user_tenant")
public class UserTenant extends Model<UserTenant> {
    @TableId
    private String fid ;

    @TableField(value = "user_account_id")
    private String userAccountId ;
    @TableField(value = "define_tenant_id")
    private  String defineTenantId ;
    private Integer type;
    @TableField(value = "is_manager")
    private Short isManager;

    private String remark;
    private Short state ;
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
     * @param userAccountId
     * @param defineTenantId
     * @param loginUser 当前登录用户
     * @return
     */
    public static UserTenant generateSimpleInsertEntity(String userAccountId, String defineTenantId, UserAccount loginUser){
        UserTenant userTenant = new UserTenant() ;
        Date now = new Date() ;
        userTenant.setFid(MyUUIDUtil.renderSimpleUUID());
        userTenant.setUserAccountId(userAccountId);
        userTenant.setDefineTenantId(defineTenantId);
        userTenant.setIsManager(SwitchStateEnum.Close.getValue());
        userTenant.setType(1);
        userTenant.setState(BaseStateEnum.ENABLED.getValue());
        userTenant.setCreateTime(now);
        userTenant.setUpdateTime(now);
        if(loginUser != null){
            userTenant.setCreateUserId(loginUser.getFid());
            userTenant.setLastModifyerId(loginUser.getFid());
        }
        return userTenant ;
    }
}
