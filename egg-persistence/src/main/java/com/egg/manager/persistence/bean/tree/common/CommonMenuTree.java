package com.egg.manager.persistence.bean.tree.common;

import com.egg.manager.common.base.enums.module.DefineMenuUrlJumpTypeEnum;
import com.egg.manager.persistence.db.mysql.entity.define.DefineMenu;
import com.egg.manager.persistence.bean.tree.MyBaseTree;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class CommonMenuTree extends MyBaseTree {
    private String id;
    private String key;
    private String pid;
    private String moduleId;
    private String name;

    private String routerUrl;
    private String hrefUrl;
    private Integer urlJumpType;   //路由跳转方式,参考 DefineMenuUrlJumpTypeEnum

    private String label;
    private String iconName;
    private boolean selected;
    private List<CommonMenuTree> children;


    public static CommonMenuTree dealDefineMenuToTree(DefineMenu menu, CommonMenuTree tree) {
        tree = tree != null ? tree : new CommonMenuTree();
        tree.setId(menu.getFid());
        tree.setPid(menu.getParentId());
        tree.setName(menu.getMenuName());
        tree.setIconName(menu.getIconName());
        tree.setLabel(menu.getLabel());
        tree.setKey(menu.getFid());
        tree.setUrlJumpType(menu.getUrlJumpType());
        tree.setRouterUrl(menu.getRouterUrl());
        tree.setHrefUrl(menu.getHrefUrl());
        return tree;
    }


    public static Map<String, CommonMenuTree> dealTreeListToUrlMap(List<CommonMenuTree> treeList, Map<String, CommonMenuTree> urlMap) {
        urlMap = urlMap != null ? urlMap : new HashMap<>();
        if (treeList != null && treeList.isEmpty() == false) {
            for (CommonMenuTree commonMenuTree : treeList) {
                String routerUrl = commonMenuTree.getRouterUrl();
                if (routerUrl != null && DefineMenuUrlJumpTypeEnum.RouterUrlJump.getValue().equals(commonMenuTree.getUrlJumpType())) {    //可跳转的Router 路由
                    urlMap.put(routerUrl, commonMenuTree);
                }
                dealTreeListToUrlMap(commonMenuTree.children, urlMap);
            }
        }
        return urlMap;
    }


}
