package com.egg.manager.common.base.beans;

import lombok.Data;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/2/4
 * \* Time: 22:35
 * \* Description:
 * \
 */
@Data
public class FrontSelectBean {
    private Integer value ;
    private String label ;



    public FrontSelectBean(Integer value,String label){
        this.value = value ;
        this.label = label ;
    }

}
