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
public class CommonMenuTree {
    private String id  ;
    private String key  ;
    private String pid ;
    private String moduleId;
    private String name ;

    private String routerUrl ;
    private String hrefUrl ;
    private Integer urlJumpType ;   //路由跳转方式,参考 DefineMenuUrlJumpTypeEnum

    private String label ;
    private String iconName ;
    private boolean  selected ;
    private List<CommonMenuTree> children;




}
