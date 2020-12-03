package com.egg.manager.persistence.obl.user.pojo.dto;

import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhoucj
 * @description 用户的关注人关联-Dto
 * @date 2020-12-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblUserAttentionPersonDto extends MyBaseMysqlDto {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 被关注用户id
     */
    private String followedUserId;

}