package com.egg.manager.common.base.enums.task;


import java.util.HashMap;
import java.util.Map;

public enum TaskHurryLevelEnum {
    VERY("ImportanceAndHurry","ImportanceAndHurry","很重要-很急",20,"danger","el-icon-alarm-clock"),
    MORE("ImportanceAndUnHurry","ImportanceAndUnHurry","重要-不紧急",40,"primary","el-icon-watch"),
    LITTLE("UnImportanceAndHurry","UnImportanceAndHurry","不重要-紧急",60,"warning","el-icon-watch-1"),
    SIMPLE("UnImportanceAndUnHurry","UnImportanceAndUnHurry","不重要不紧急",80,"info","el-icon-timer"),
    ;

    TaskHurryLevelEnum(String key, String value, String label, Integer numVal, String type, String icon) {
        this.key = key;
        this.value = value;
        this.label = label;
        this.numVal = numVal;
        this.type = type;
        this.icon = icon;
    }

    private String key ;
    private String value ;
    private String label ;
    private Integer numVal ;
    private String type;
    private String icon ;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

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

    public Map<String,Object> toMap() {
        Map<String,Object> map = new HashMap<String,Object>() ;
        map.put("key",this.key);
        map.put("value",this.value);
        map.put("label",this.label);
        map.put("numVal",this.numVal);
        map.put("type",this.type);
        map.put("icon",this.icon);
        return map ;
    }

}
