package com.egg.manager.persistence.pojo.dto.define;

import com.egg.manager.persistence.db.mysql.entity.define.DefineDepartment;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.dto.MyBaseDto;
import com.egg.manager.persistence.pojo.vo.define.DefineDepartmentVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Description:
 * \
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefineDepartmentDto extends MyBaseDto {
    private String fid;
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 上级id
     */
    private String parentId;
    /**
     * 层级
     */
    private Integer level;
    /**
     * 排序值
     */
    private Integer orderNum;
    /**
     * 描述
     */
    private String description;



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
     * 上级部门-vo
     */
    private DefineDepartmentDto parentDepartment;


    /**
     * 创建人-vo
     */
    private UserAccount createUser;
    /**
     * 最后更新人-vo
     */
    private UserAccount lastModifyer;

}
