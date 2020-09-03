package com.egg.manager.persistence.pojo.mapstruct.mysql.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.util.str.MyStringUtil;
import com.egg.manager.persistence.pojo.dto.mysql.MyBaseMysqlDto;
import com.egg.manager.persistence.pojo.vo.mysql.MyBaseMysqlVo;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserAccountVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.ArrayList;
import java.util.List;
/**
 * 可在该接口写公用的转化方法,定义的方法请勿修改方法名！
 *
 *
 * Notes:
 * @Context : 该注解用于添加额外参数，不会对其进行判断null等操作
 */
@MapperConfig(disableSubMappingMethodsGeneration=true)
public interface MyBaseMysqlVoMapstruct<E,V extends MyBaseMysqlVo,D extends MyBaseMysqlDto> {





    default boolean handleSwitchStateGetBoolean(Short value){
        return SwitchStateEnum.Open.getValue() == value ;
    }

    default Short handleSwitchStateGetShort(Boolean value){
        return (value == true) ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue() ;
    }



    /**
     * tagIds 转 json字符串
     * @param tagIds
     * @return
     */
    default String handleTagIdListToJsonString(List<String> tagIds){
        tagIds = tagIds != null ? tagIds : new ArrayList<>() ;
        return JSON.toJSONString(tagIds);
    }

    default List<String>  handleTagIdJsonStringToList(String tagIds){
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


    default String handleHtmlDomToText(String content,String defaultValue){
        return (StringUtils.isBlank(content)) ? defaultValue : MyStringUtil.htmlDomToText(content, null);
    }

    default UserAccountVo getNullUserAccountVo(){
        return null ;
    }


   /* @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer",ignore = true)
    })
    V defaultConfigEntityToVo(E e);


    @Mappings({})
    V defaultConfigDtoToVo(D d);


    @Mappings({})
    D defaultConfigVoToDto(V v);


    @Mappings({
            @Mapping(target = "createUser", ignore = true),
            @Mapping(target = "lastModifyer",   ignore = true)
    })
    D defaultConfigVoToEntity(V v);*/

}
