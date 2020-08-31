package com.egg.manager.persistence.db.mysql.entity.user;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户&部门 关联-entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("em_user_department")
public class UserDepartment extends Model<UserDepartment> {
    @TableId
    private String fid;
    /**
     * 用户账号id
     */
    @TableField(value = "user_account_id")
    private String userAccountId;
    /**
     * 部门id
     */
    @TableField(value = "define_department_id")
    private String defineDepartmentId;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 是否部门管理员
     */
    @TableField(value = "is_manager")
    private Short isManager;




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
     * 创建人id
     */
    @TableField(value = "create_user_id")
    private String createUserId;
    /**
     * 最后修改人id
     */
    @TableField(value = "last_modifyer_id")
    private String lastModifyerId;


    @Override
    protected Serializable pkVal() {
        return this.fid;
    }


    /**
     * 返回一个通用的 entity实例
     *
     * @param userAccountId
     * @param defineDepartmentId
     * @param loginUser      当前登录用户
     * @return
     */
    public static UserDepartment generateSimpleInsertEntity(String userAccountId, String defineDepartmentId, UserAccount loginUser) {
        UserDepartment userDepartment = new UserDepartment();
        Date now = new Date();
        userDepartment.setFid(MyUUIDUtil.renderSimpleUUID());
        userDepartment.setUserAccountId(userAccountId);
        userDepartment.setDefineDepartmentId(defineDepartmentId);
        userDepartment.setIsManager(SwitchStateEnum.Close.getValue());
        userDepartment.setType(1);
        userDepartment.setState(BaseStateEnum.ENABLED.getValue());
        userDepartment.setCreateTime(now);
        userDepartment.setUpdateTime(now);
        if (loginUser != null) {
            userDepartment.setCreateUserId(loginUser.getFid());
            userDepartment.setLastModifyerId(loginUser.getFid());
        }
        return userDepartment;
    }
}
