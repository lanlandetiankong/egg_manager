package com.egg.manager.persistence.commons.base.beans.front;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrontEntitySelectBean<K> implements Serializable {
    private static final long serialVersionUID = 9043447081130667508L;
    /**
     * Select的值
     */
    private K value;
    /**
     * Select展示的名称
     */
    private String label;


}
