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
public class EmDefineModuleDto extends MyBaseMysqlDto {

    private static final long serialVersionUID = 7597601436931527211L;
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
    /**
     * 类型_名称
     */
    private String typeStr;


}
