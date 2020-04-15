package com.egg.manager.common.base.enums.base;

import com.egg.manager.common.base.enums.define.DefineJobTypeEnum;
import lombok.Data;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/4/15
 * \* Time: 22:03
 * \* Description:
 * \
 */
public enum  UserSexEnum {
    Man(1,"男","男"),
    Woman(0,"女","女"),
    Unknow(-1,"未知","未知"),
    ;
    private Integer value ;
    private String  name ;
    private String  label ;

    UserSexEnum(Integer value, String name, String label) {
        this.value = value;
        this.name = name;
        this.label = label;
    }

    public static String dealGetNameByVal(Integer value){
        UserSexEnum[] enums = UserSexEnum.values();
        for(UserSexEnum enumObj : enums){
            if(enumObj.getValue().equals(value)){
                return enumObj.getName() ;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
