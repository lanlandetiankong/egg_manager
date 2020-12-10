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
public class EmDefineDepartmentDto extends MyBaseMysqlDto {
    private static final long serialVersionUID = -2075610223604632131L;
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
    private String pid;
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
     * 上级部门-com.egg.manager.persistence.obl.article.pojo.vo
     */
    private EmDefineDepartmentDto parentDepartment;


}