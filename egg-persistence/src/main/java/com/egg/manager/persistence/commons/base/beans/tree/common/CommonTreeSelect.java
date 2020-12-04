package com.egg.manager.persistence.commons.base.beans.tree.common;

import com.egg.manager.persistence.commons.base.beans.tree.MyBaseTree;
import lombok.*;

import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommonTreeSelect extends MyBaseTree {
    private static final long serialVersionUID = -1164332140821476333L;
    /**
     * 标题
     */
    private String title;
    /**
     * 值
     */
    private String value;
    /**
     * vue-key
     */
    private String key;
    /**
     * 上级id
     */
    private String parentId;
    /**
     * 子项
     */
    private List<CommonTreeSelect> children;


}
