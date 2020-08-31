package com.egg.manager.persistence.pojo.vo.define;

import com.egg.manager.persistence.pojo.vo.MyBaseVo;
import com.egg.manager.persistence.pojo.vo.user.UserAccountVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefineGroupVo extends MyBaseVo {
    private String fid;
    /**
     * 名称
     */
    private String name;
    /**
     * 上级id
     */
    private String pid;
    /**
     * 是否成员可继承组权限
     */
    private Integer isInherit;
    /**
     * 类型
     */
    private String type;

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
     * 创建用户id
     */
    private String createUserId;
    /**
     * 最后修改用户id
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
