package com.egg.manager.persistence.pojo.mysql.vo.announcement;

import com.egg.manager.persistence.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/21
 * \* Time: 13:42
 * \* Description:
 * \
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementTagVo extends MyBaseMysqlVo {
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 排序值
     */
    private Integer ordering;


}
