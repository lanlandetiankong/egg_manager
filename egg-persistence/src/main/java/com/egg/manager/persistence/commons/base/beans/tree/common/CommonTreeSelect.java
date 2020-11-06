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
    /**
     * 标题
     */
    private String title;
    /**
     * 值
     */
    private Long value;
    /**
     * vue-key
     */
    private Long key;
    /**
     * 上级id
     */
    private Long parentId;
    /**
     * 子项
     */
    private List<CommonTreeSelect> children;


}
