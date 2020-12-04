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
public class DefineGroupVo extends MyBaseMysqlVo {
    private static final long serialVersionUID = -6538806739341558532L;
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


}
