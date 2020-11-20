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
public class DefineModuleVo extends MyBaseMysqlVo {
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 图标名
     */
    private String icon;
    /**
     * 样式json
     */
    private String style;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 类型_名称
     */
    private String typeStr;

    /**
     * 图标值
     */
    private String iconVal;
    /**
     * 样式值
     */
    private String styleVal;
    /**
     * 类型值
     */
    private Integer typeVal;

}
