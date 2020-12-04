package com.egg.manager.persistence.obl.article.domain.enums;

/**
 * @Description:
 * @ClassName: ArticleLikeEnum
 * @Author: zhoucj
 * @Date: 2020/12/2 9:15
 */
public enum ArticleLikeEnum {
    Like((short) 1, "点赞"),
    Dislike((short) 2, "踩低");

    ArticleLikeEnum(short value, String name) {
        this.value = value;
        this.name = name;
    }

    private short value;
    private String name;


    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
