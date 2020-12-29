package com.egg.manager.facade.persistence.em.user.pojo.dto;

import com.egg.manager.facade.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description app用户关联表-Dto
 * @date 2020-12-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmUserAppRelatedDto extends MyBaseMysqlDto {
    private static final long serialVersionUID = -79323995309389478L;

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