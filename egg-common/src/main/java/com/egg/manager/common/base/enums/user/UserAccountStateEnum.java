package com.egg.manager.common.base.enums.user;

public enum UserAccountStateEnum {
    ENABLED(1,"启用","启用"),
    DISABLED(0,"禁用","禁用"),
    DELETE(-1,"删除","用户已被删除"),
    LOCKED(-10,"锁定","用户已被锁定"),
    ;

    UserAccountStateEnum(Integer value, String name, String info) {
        this.value = value;
        this.name = name;
        this.info = info;
    }

    private Integer value ;
    private String name ;
    private String info ;


    public static String doGetEnumInfoByValue(Integer value){
        if(value == null){
            return null ;
        }
        UserAccountStateEnum[] enums = UserAccountStateEnum.values();
        for(UserAccountStateEnum enumObj : enums){
            if(enumObj.value.equals(value)){
                return enumObj.getInfo() ;
            }
        }
        return "" ;
    }



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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
