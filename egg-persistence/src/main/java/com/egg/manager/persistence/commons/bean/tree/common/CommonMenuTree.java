package com.egg.manager.persistence.commons.bean.tree.common;

import com.egg.manager.common.base.enums.module.DefineMenuUrlJumpTypeEnum;
import com.egg.manager.persistence.commons.bean.tree.MyBaseTree;
import com.egg.manager.persistence.em.define.db.mysql.entity.DefineMenu;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonMenuTree extends MyBaseTree {
    /**
     * 菜单id
     */
    private Long id;
    /**
     * vue的key
     */
    private Long key;
    /**
     * 上级id
     */
    private Long pid;
    /**
     * 模块id
     */
    private String moduleId;
    /**
     * 菜单名
     */
    private String name;
    /**
     * 路由跳转地址
     */
    private String routerUrl;
    /**
     * 路径跳转地址
     */
    private String hrefUrl;
    /**
     * 路由跳转方式,参考 DefineMenuUrlJumpTypeEnum
     */
    private Integer urlJumpType;
    /**
     * 展示内容
     */
    private String label;
    /**
     * 图标名称
     */
    private String iconName;
    /**
     * 是否已选
     */
    private boolean selected;
    /**
     * 子菜单
     */
    private List<CommonMenuTree> children;

    /**
     * 定义的菜单=>菜单树
     * @param menu
     * @param tree
     * @return
     */
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

    /**
     * 定义的菜单=>菜单树
     * @param treeList
     * @param urlMap
     * @return
     */
    public static Map<String, CommonMenuTree> dealTreeListToUrlMap(List<CommonMenuTree> treeList, Map<String, CommonMenuTree> urlMap) {
        urlMap = urlMap != null ? urlMap : Maps.newHashMap();
        if (treeList != null && treeList.isEmpty() == false) {
            for (CommonMenuTree commonMenuTree : treeList) {
                String routerUrl = commonMenuTree.getRouterUrl();
                if (routerUrl != null && DefineMenuUrlJumpTypeEnum.RouterUrlJump.getValue().equals(commonMenuTree.getUrlJumpType())) {
                    //可跳转的Router 路由
                    urlMap.put(routerUrl, commonMenuTree);
                }
                dealTreeListToUrlMap(commonMenuTree.children, urlMap);
            }
        }
        return urlMap;
    }


}
