package com.egg.manager.persistence.obl.article.pojo.mapstruct.conversion;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.conversion.MyBaseMysqlConversion;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description 用户关注的文章收藏类别-Conversion
 * @date 2020-12-03
 */
@Component
@Named("oblUserAttentionArticleCategoryConversion")
public class OblUserAttentionArticleCategoryConversion extends MyBaseMysqlConversion {

}