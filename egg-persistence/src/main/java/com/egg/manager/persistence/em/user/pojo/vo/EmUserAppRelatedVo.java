package com.egg.manager.persistence.em.user.pojo.vo;

import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description app用户关联表-Vo
 * @date 2020-12-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmUserAppRelatedVo extends MyBaseMysqlVo {
    private static final long serialVersionUID = 448687647913818275L;

    /**
     * 来源用户id
     */
    private String fromUserId;
    /**
     * 来源平台
     */
    private String fromPlatform;
    /**
     * 关联用户id
     */
    private String relatedUserId;
    /**
     * 关联平台
     */
    private String relatedPlatform;


}