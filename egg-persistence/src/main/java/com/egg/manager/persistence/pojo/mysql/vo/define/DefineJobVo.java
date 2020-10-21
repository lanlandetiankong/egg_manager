package com.egg.manager.persistence.pojo.mysql.vo.define;

import com.egg.manager.persistence.pojo.mysql.vo.MyBaseMysqlVo;
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
public class DefineJobVo extends MyBaseMysqlVo {
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 类型_名称
     */
    private String typeStr;


}
