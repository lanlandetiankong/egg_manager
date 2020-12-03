package com.egg.manager.persistence.obl.user.pojo.vo;

import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description 用户的关注人关联-Vo
 * @date 2020-12-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OblUserAttentionPersonVo extends MyBaseMysqlVo {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 被关注用户id
     */
    private String followedUserId;


}