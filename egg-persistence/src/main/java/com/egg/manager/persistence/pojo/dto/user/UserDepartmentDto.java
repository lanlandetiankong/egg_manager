package com.egg.manager.persistence.pojo.dto.user;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.dto.MyBaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDepartmentDto extends MyBaseDto {
    private String fid;
    /**
     * 用户账号id
     */
    private String userAccountId;
    /**
     * 部门id
     */
    private String defineDepartmentId;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 是否部门管理员
     */
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
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 创建人id
     */
    private String createUserId;
    /**
     * 最后修改人id
     */
    private String lastModifyerId;
    /**
     * 创建人
     */
    private UserAccount createUser;
    /**
     * 最后修改人
     */
    private UserAccount lastModifyer;


}
