package com.egg.manager.persistence.em.announcement.pojo.vo;

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
public class EmAnnouncementTagVo extends MyBaseMysqlVo {
    private static final long serialVersionUID = 94547176178179533L;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 权重值
     */
    private Integer weights;


}
