package com.egg.manager.persistence.obl.blconf.pojo.mapstruct.conversion;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.conversion.MyBaseMysqlConversion;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description 博客通知表-Conversion
 * @date 2020-11-30
 */
@Component
@Named("oblBlogNoticeConversion")
public class OblBlogNoticeConversion extends MyBaseMysqlConversion {

}