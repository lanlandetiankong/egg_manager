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
public class EmDefinePermissionVo extends MyBaseMysqlVo {
    private static final long serialVersionUID = 134237472567892030L;
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
    private Boolean ensure;
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
