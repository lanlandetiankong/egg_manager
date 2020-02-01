package com.egg.manager.dto.login;

import lombok.*;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/10/5
 * \* Time: 14:21
 * \* Description:
 * \
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LoginAccountDTO {

    private String account;
    private String password ;
}
