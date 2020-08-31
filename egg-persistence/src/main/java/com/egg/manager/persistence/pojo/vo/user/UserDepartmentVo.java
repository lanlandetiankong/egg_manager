package com.egg.manager.persistence.pojo.vo.user;

import com.egg.manager.persistence.pojo.vo.MyBaseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDepartmentVo extends MyBaseVo {
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
     * 创建人-vo
     */
    private UserAccountVo createUser;
    /**
     * 最后更新人-vo
     */
    private UserAccountVo lastModifyer;


}
