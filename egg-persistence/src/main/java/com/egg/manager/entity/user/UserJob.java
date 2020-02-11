package com.egg.manager.entity.user;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
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
@TableName("em_user_job")
public class UserJob extends Model<UserJob> {
    @TableId
    private String fid ;

    @TableField(value = "user_account_id")
    private String userAccountId ;
    @TableField(value = "define_job_id")
    private  String defineJobId ;
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
     * @param defineJobId
     * @param createUserId
     * @return
     */
    public static UserJob generateSimpleInsertEntity(String userAccountId, String defineJobId, String createUserId){
        UserJob userJob = new UserJob() ;
        Date now = new Date() ;
        userJob.setFid(MyUUIDUtil.renderSimpleUUID());
        userJob.setUserAccountId(userAccountId);
        userJob.setDefineJobId(defineJobId);
        userJob.setVersion(0);
        userJob.setState(BaseStateEnum.ENABLED.getValue());
        userJob.setCreateTime(now);
        userJob.setUpdateTime(now);
        userJob.setCreateUser(createUserId);
        userJob.setLastModifyer(createUserId);
        return userJob ;
    }
}
