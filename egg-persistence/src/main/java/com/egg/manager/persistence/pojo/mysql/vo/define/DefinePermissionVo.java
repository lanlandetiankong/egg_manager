package com.egg.manager.persistence.pojo.mysql.vo.define;

import com.egg.manager.persistence.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @version V1.0
 * @description:
 * @date 2020/10/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefinePermissionVo extends MyBaseMysqlVo {
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
