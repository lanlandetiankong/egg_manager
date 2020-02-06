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
public class CommonTree {
    private String id  ;
    private String key  ;
    private String pid ;
    private String moduleId;
    private String name ;
    private String path ;
    private String label ;
    private String iconName ;
    private boolean  selected ;
    private String componentsName ;
    private List<CommonTree> children;




}
