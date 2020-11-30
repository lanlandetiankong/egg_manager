package com.egg.manager.persistence.obl.article.pojo.mapstruct.conversion;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.conversion.MyBaseMysqlConversion;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description 文章标签定义表-Conversion
 * @date 2020-11-30
 */
@Component
@Named("oblArticleTagConversion")
public class OblArticleTagConversion extends MyBaseMysqlConversion {

}