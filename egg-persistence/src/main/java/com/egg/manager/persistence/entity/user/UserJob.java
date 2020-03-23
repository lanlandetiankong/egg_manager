package com.egg.manager.persistence.entity.user;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
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

    private String remark ;
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
     * @param userAccountId
     * @param defineJobId
     * @param loginUser 当前登录用户
     * @return
     */
    public static UserJob generateSimpleInsertEntity(String userAccountId, String defineJobId,UserAccount loginUser){
        UserJob userJob = new UserJob() ;
        Date now = new Date() ;
        userJob.setFid(MyUUIDUtil.renderSimpleUUID());
        userJob.setUserAccountId(userAccountId);
        userJob.setDefineJobId(defineJobId);
        userJob.setState(BaseStateEnum.ENABLED.getValue());
        userJob.setCreateTime(now);
        userJob.setUpdateTime(now);
        if(loginUser != null){
            userJob.setCreateUserId(loginUser.getFid());
            userJob.setLastModifyerId(loginUser.getFid());
        }
        return userJob ;
    }
}
