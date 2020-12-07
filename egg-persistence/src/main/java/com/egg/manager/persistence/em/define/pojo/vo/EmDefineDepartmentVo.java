package com.egg.manager.persistence.em.define.pojo.vo;

import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmDefineDepartmentVo extends MyBaseMysqlVo {
    private static final long serialVersionUID = 5337797319035471293L;
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
     * 上级部门-com.egg.manager.persistence.obl.article.pojo.vo
     */
    private EmDefineDepartmentVo parentDepartment;


}
