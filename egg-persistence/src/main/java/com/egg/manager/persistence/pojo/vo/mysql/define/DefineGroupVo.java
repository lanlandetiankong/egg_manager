package com.egg.manager.persistence.pojo.vo.mysql.define;

import com.egg.manager.persistence.pojo.vo.mysql.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
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
