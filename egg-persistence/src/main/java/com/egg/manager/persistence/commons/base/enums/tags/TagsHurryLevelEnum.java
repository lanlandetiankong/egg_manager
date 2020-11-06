package com.egg.manager.persistence.commons.base.enums.tags;


import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public enum TagsHurryLevelEnum {
    VERY("Very", "Very", "特别急", 20),
    MORE("More", "More", "较着急", 40),
    LITTLE("Little", "Little", "有点急", 60),
    SIMPLE("Simple", "Simple", "普通", 80),
    GENERAL("General", "General", "一般", 100),
    NO_AT_ALL("NoAtAll", "NoAtAll", "不着急", 120);

    TagsHurryLevelEnum(String key, String value, String label, Integer numVal) {
        this.key = key;
        this.value = value;
        this.label = label;
        this.numVal = numVal;
    }

    private String key;
    private String value;
    private String label;
    private Integer numVal;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public Integer getNumVal() {
        return numVal;
    }

    public void setNumVal(Integer numVal) {
        this.numVal = numVal;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", this.key);
        map.put("value", this.value);
        map.put("label", this.label);
        map.put("numVal", this.numVal);
        return map;
    }

    public static String getEnumShowStr(String value) {
        String showStr = "";
        if (StringUtils.isBlank(value)) {
            return showStr;
        }
        for (TagsHurryLevelEnum tagsEnum : TagsHurryLevelEnum.values()) {
            if (tagsEnum.getValue().equals(value)) {
                showStr = tagsEnum.getLabel();
                break;
            }
        }
        return showStr;
    }

}
