package com.egg.manager.facade.persistence.em.define.pojo.dto;

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
public class EmDefineTenantDto extends MyBaseMysqlDto {
    private static final long serialVersionUID = 3561931756532206413L;
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
     * 类型_名称
     */
    private String typeStr;
}
