package com.egg.manager.persistence.dto.login;

import com.egg.manager.persistence.dto.MyBaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/10/5
 * \* Time: 14:21
 * \* Description:
 * \
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginAccountDTO extends MyBaseDto {

    private String account;
    private String password;
}
