package com.egg.manager.persistence.obl.article.pojo.mapstruct.conversion;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.conversion.MyBaseMysqlConversion;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description 用户定义的收藏类别-Conversion
 * @date 2020-12-03
 */
@Component
@Named("oblUserDefCollectCategoryConversion")
public class OblUserDefCollectCategoryConversion extends MyBaseMysqlConversion {

}