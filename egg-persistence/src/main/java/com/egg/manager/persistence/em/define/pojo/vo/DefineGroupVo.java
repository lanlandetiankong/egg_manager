package com.egg.manager.persistence.em.define.pojo.vo;

import com.egg.manager.persistence.enhance.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefineGroupVo extends MyBaseMysqlVo {
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
