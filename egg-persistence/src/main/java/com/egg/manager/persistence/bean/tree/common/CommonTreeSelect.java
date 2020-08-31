package com.egg.manager.persistence.bean.tree.common;

import com.egg.manager.persistence.bean.tree.MyBaseTree;
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
public class CommonTreeSelect extends MyBaseTree {
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
