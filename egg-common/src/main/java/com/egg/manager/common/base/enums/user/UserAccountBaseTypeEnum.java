package com.egg.manager.common.base.enums.user;

public enum UserAccountBaseTypeEnum {
    SimpleUser(0,"普通用户","普通用户"),
    Root(10,"管理员","管理员"),
    SuperRoot(100,"超级管理员","超级管理员"),
    Vip(110,"会员","会员"),
    SuperVip(190,"超级会员","超级会员")
    ;

    UserAccountBaseTypeEnum(Integer value, String name,String label) {
        this.value = value;
        this.name = name;
        this.label = label;

    }

    private Integer value ;
    private String name ;
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



    public static UserAccountBaseTypeEnum doGetEnumByValue(Integer value){
        if(value == null){
            return null ;
        }
        UserAccountBaseTypeEnum[] enums = UserAccountBaseTypeEnum.values();
        for(UserAccountBaseTypeEnum enumObj : enums){
            if(enumObj.value.equals(value)){
                return enumObj ;
            }
        }
        return null ;
    }


}
