package com.egg.manager.persistence.obl.blconf.pojo.mapstruct.conversion;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.conversion.MyBaseMysqlConversion;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description 博客菜单定义表-Conversion
 * @date 2020-11-30
 */
@Component
@Named("oblBlogMenuConfConversion")
public class OblBlogMenuConfConversion extends MyBaseMysqlConversion {

}