package com.egg.manager.common.base.enums.module;

public enum DefineMenuUrlJumpTypeEnum {
    RouterUrlJump(1,"Router地址跳转","Router地址跳转"),
    OutUrlJump(2,"在当前页面打开外部链接","在当前页面打开外部链接"),
    OutUrlBlankJump(3,"在新页面打开外部链接","在新页面打开外部链接"),
    ;

    DefineMenuUrlJumpTypeEnum(Integer value, String name, String label) {
        this.value = value;
        this.name = name;
        this.label = label;
    }

    private Integer value ;
    private String name ;
    private boolean isNeedFilter ;
    private String label ;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    public boolean getIsNeedFilter() {
        return isNeedFilter;
    }

    public void setLabel(boolean isNeedFilter) {
        this.isNeedFilter = isNeedFilter;
    }



    public static DefineMenuUrlJumpTypeEnum doGetEnumByValue(Integer value){
        if(value == null){
            return null ;
        }
        DefineMenuUrlJumpTypeEnum[] enums = DefineMenuUrlJumpTypeEnum.values();
        for(DefineMenuUrlJumpTypeEnum enumObj : enums){
            if(enumObj.value.equals(value)){
                return enumObj ;
            }
        }
        return null ;
    }


}
