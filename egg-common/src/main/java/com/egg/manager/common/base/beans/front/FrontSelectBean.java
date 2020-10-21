package com.egg.manager.common.base.beans.front;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrontSelectBean implements Serializable {

    /**
     * Select的值
     */
    private Object value;
    /**
     * Select展示的名称
     */
    private String label;
}
