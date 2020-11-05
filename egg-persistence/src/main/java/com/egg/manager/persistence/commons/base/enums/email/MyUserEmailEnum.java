package com.egg.manager.persistence.commons.base.enums.email;

import java.util.concurrent.TimeUnit;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public enum MyUserEmailEnum {
    Default(1, "default", "MarsWilldo验证码通知", "您刚才在MarsWilldo注册的验证码为 %s ,请勿将验证码交给他人，验证码半小时内有效!", "", 30, TimeUnit.HOURS);
    private Integer value;
    private String type;
    private String title;
    private String content;
    private String params;
    private Integer limitTime;
    private TimeUnit timeUnit;

    MyUserEmailEnum(Integer value, String type, String title, String content, String params, Integer limitTime, TimeUnit timeUnit) {
        this.value = value;
        this.type = type;
        this.title = title;
        this.content = content;
        this.params = params;
        this.limitTime = limitTime;
        this.timeUnit = timeUnit;
    }

    public Integer getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Integer limitTime) {
        this.limitTime = limitTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
