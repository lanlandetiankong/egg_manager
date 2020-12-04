package com.egg.manager.persistence.obl.article.pojo.mapstruct.conversion;

import com.egg.manager.persistence.exchange.pojo.mysql.mapstruct.conversion.MyBaseMysqlConversion;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description 文章点赞表-Conversion
 * @date 2020-12-02
 */
@Component
@Named("oblArticleLikeRecordConversion")
public class OblArticleLikeRecordConversion extends MyBaseMysqlConversion {

}