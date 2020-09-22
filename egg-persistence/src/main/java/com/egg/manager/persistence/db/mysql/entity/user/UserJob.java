package com.egg.manager.persistence.db.mysql.entity.user;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户&职务 关联-entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("em_user_job")
public class UserJob extends Model<UserJob> {
    @TableId
    private String fid;
    /**
     * 账号id
     */
    @TableField(value = "user_account_id")
    private String userAccountId;
    /**
     * 职务id
     */
    @TableField(value = "define_job_id")
    private String defineJobId;


    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 状态
     */
    @TableField("state")
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


    /**
     * 返回一个通用的 entity实例
     *
     * @param userAccountId
     * @param defineJobId
     * @param loginUser     当前登录用户
     * @return
     */
    public static UserJob generateSimpleInsertEntity(String userAccountId, String defineJobId, UserAccount loginUser) {
        UserJob userJob = new UserJob();
        Date now = new Date();
        userJob.setFid(MyUUIDUtil.renderSimpleUUID());
        userJob.setUserAccountId(userAccountId);
        userJob.setDefineJobId(defineJobId);
        userJob.setState(BaseStateEnum.ENABLED.getValue());
        userJob.setCreateTime(now);
        userJob.setUpdateTime(now);
        if (loginUser != null) {
            userJob.setCreateUserId(loginUser.getFid());
            userJob.setLastModifyerId(loginUser.getFid());
        }
        return userJob;
    }
}
