package com.egg.manager.persistence.pojo.mysql.dto.user;

import com.egg.manager.persistence.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @version V1.0
 * @description:
 * @date 2020/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJobDto extends MyBaseMysqlDto {
    /**
     * 账号id
     */
    private String userAccountId;
    /**
     * 职务id
     */
    private String defineJobId;

}
