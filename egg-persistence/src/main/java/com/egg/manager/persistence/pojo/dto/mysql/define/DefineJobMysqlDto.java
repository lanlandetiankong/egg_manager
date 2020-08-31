package com.egg.manager.persistence.pojo.dto.mysql.define;

import com.egg.manager.persistence.pojo.dto.mysql.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefineJobMysqlDto extends MyBaseMysqlDto {
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
