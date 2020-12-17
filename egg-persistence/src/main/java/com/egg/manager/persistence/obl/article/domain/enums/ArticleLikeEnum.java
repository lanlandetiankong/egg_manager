package com.egg.manager.persistence.obl.article.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description:
 * @ClassName: ArticleLikeEnum
 * @Author: zhoucj
 * @Date: 2020/12/2 9:15
 */
@Getter
@AllArgsConstructor
public enum ArticleLikeEnum {
    Like((short) 1, "点赞"),
    Dislike((short) 2, "踩低");

    private short value;
    private String name;

}
