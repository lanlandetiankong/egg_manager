package com.egg.manager.persistence.pojo.mapstruct.mysql.vo;

import com.alibaba.fastjson.JSON;
import com.egg.manager.persistence.pojo.dto.mysql.MyBaseMysqlDto;
import com.egg.manager.persistence.pojo.vo.mysql.MyBaseMysqlVo;

import java.util.List;
/**
 * 可在该接口写公用的转化方法
 */
public interface MyBaseMysqlVoMapstruct<E,V extends MyBaseMysqlVo,D extends MyBaseMysqlDto> {




    /**
     * tagIds 转 json字符串
     * @param tagIds
     * @return
     */
    default String tagIdListToJsonString(List<String> tagIds){
        return JSON.toJSONString(tagIds);
    }
}
