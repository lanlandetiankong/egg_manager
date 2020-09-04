package com.egg.manager.persistence.pojo.mysql.vo.define;


import com.egg.manager.persistence.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefineRoleVo extends MyBaseMysqlVo {
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 类型名称
     */
    private String typeStr;




}
