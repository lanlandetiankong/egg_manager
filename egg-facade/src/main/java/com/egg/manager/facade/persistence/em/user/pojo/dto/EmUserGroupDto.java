package com.egg.manager.facade.persistence.em.user.pojo.dto;

import com.egg.manager.facade.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.*;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmUserGroupDto extends MyBaseMysqlDto {
    private static final long serialVersionUID = 8525155092157397687L;
    /**
     * 组织id
     */
    private String defineGroupId;
    /**
     * 账号id
     */
    private String userAccountId;
    /**
     * 类型
     */
    private String type;

}
