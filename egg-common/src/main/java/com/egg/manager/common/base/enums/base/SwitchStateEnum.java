package com.egg.manager.common.base.enums.base;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/10/6
 * \* Time: 0:34
 * \* Description:
 * \
 */
public enum SwitchStateEnum {
    Open(1,"启用","启用"),
    Close(0,"禁用","禁用"),
    ;

    SwitchStateEnum(Integer value, String name, String info) {
        this.value = value;
        this.name = name;
        this.info = info;
    }

    private Integer value ;
    private String name ;
    private String info ;

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


    /**
     * 判断是否属于枚举的有效值
     * @param value
     * @return
     */
    public static boolean checkIsValidVal(Integer value){
        boolean flag = false ;
        if(value != null){
            SwitchStateEnum[] enums = SwitchStateEnum.values();
            for(SwitchStateEnum enumObj : enums){
                if(value.equals(enumObj.getValue())){
                    flag = true ;
                    break;
                }
            }
        }
        return flag ;
    }

    /**
     * 判断是否属于枚举的有效值
     * @param value
     * @return
     */
    public static String dealGetNameByVal(Integer value){
        String name = "" ;
        if(value != null){
            SwitchStateEnum[] enums = SwitchStateEnum.values();
            for(SwitchStateEnum enumObj : enums){
                if(value.equals(enumObj.getValue())){
                    name = enumObj.getName() ;
                    break;
                }
            }
        }
        return name ;
    }

}
