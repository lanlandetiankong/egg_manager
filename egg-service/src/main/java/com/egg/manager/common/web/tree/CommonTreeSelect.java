package com.egg.manager.common.web.tree;

import lombok.*;

import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/19
 * \* Time: 19:44
 * \* Description:
 * \
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommonTreeSelect {
    private String title  ;
    private String value  ;
    private String key ;
    private List<CommonTreeSelect> children;


}
