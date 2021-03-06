package com.egg.manager.persistence.em.define.pojo.dto;

import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
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
public class EmDefinePermissionDto extends MyBaseMysqlDto {
    private static final long serialVersionUID = -3303141648139028803L;
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 是否确认发布，发布之后不可修改
     */
    private Short ensure;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 是否确认发布_名称
     */
    private String ensureStr;
    /**
     * 类型_名称
     */
    private String typeStr;


}
