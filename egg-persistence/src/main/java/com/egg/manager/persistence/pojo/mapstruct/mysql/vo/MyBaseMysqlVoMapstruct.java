package com.egg.manager.persistence.pojo.mapstruct.mysql.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.egg.manager.common.util.str.MyStringUtil;
import com.egg.manager.persistence.pojo.dto.mysql.MyBaseMysqlDto;
import com.egg.manager.persistence.pojo.vo.mysql.MyBaseMysqlVo;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserAccountVo;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
/**
 * 可在该接口写公用的转化方法,定义的方法请勿修改方法名！
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

    default List<String>  tagIdJsonStringToList(String tagIds){
        List<String> tagList = null ;
        if (StringUtils.isNotBlank(tagIds)) {
            try {
                tagList = JSONArray.parseArray(tagIds, String.class);
                if (tagList != null && tagList.isEmpty() == false) {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return tagList ;
    }


    default String htmlDomToText(String content,String defaultValue){
        return (StringUtils.isBlank(content)) ? defaultValue : MyStringUtil.htmlDomToText(content, null);
    }

    default UserAccountVo getNullUserAccountVo(){
        return null ;
    }
}
