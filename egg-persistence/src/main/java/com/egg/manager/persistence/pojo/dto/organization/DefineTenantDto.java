package com.egg.manager.persistence.pojo.dto.organization;

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
public class DefineTenantDto extends MyBaseDto {
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
     * 数据库类型 code
     */
    private String dbCode;

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
     * 类型_名称
     */
    private String typeStr;

    /**
     * 创建人-vo
     */
    private UserAccount createUser;
    /**
     * 最后更新人-vo
     */
    private UserAccount lastModifyer;
}
