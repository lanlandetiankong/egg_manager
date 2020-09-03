package com.egg.manager.persistence.pojo.mapstruct.mysql.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.common.util.str.MyStringUtil;
import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.dto.mysql.MyBaseMysqlDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.organization.DefineTenantVoMapstruct;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.user.UserAccountVoMapstruct;
import com.egg.manager.persistence.pojo.vo.mysql.MyBaseMysqlVo;
import com.egg.manager.persistence.pojo.vo.mysql.organization.DefineTenantVo;
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
 * 可在该接口写公用的转化方法,定义的方法请勿修改方法名、参数、返回值等！
 *
 *
 * Notes:
 * @Context : 该注解用于添加额外参数，不会对其进行判断null等操作
 */
@MapperConfig(disableSubMappingMethodsGeneration=true)
public interface MyBaseMysqlVoMapstruct<E,V extends MyBaseMysqlVo,D extends MyBaseMysqlDto> {

     UserAccountVoMapstruct userAccountVoMapstruct = UserAccountVoMapstruct.INSTANCE ;
     DefineTenantVoMapstruct defineTenantVoMapstruct = DefineTenantVoMapstruct.INSTANCE ;

    /**
     * 判断值 是否为 启用
     * @param value
     * @return
     */
    default boolean handleSwitchStateGetBoolean(Short value){
        return SwitchStateEnum.Open.getValue() == value ;
    }

    /**
     * 开关式枚举 取得值
     * @param value
     * @return
     */
    default Short handleSwitchStateGetShort(Boolean value){
        return (value == true) ? SwitchStateEnum.Open.getValue() : SwitchStateEnum.Close.getValue() ;
    }

    default String handleSwitchStateGetName(Short value){
        return SwitchStateEnum.dealGetNameByVal(value);
    }

    /**
     * 取得[用户类型]名称
     * @param value
     * @return
     */
    default String handleUserTypeGetStr(Integer value){
        UserAccountBaseTypeEnum userAccountBaseTypeEnums = UserAccountBaseTypeEnum.doGetEnumByValue(value);
        if (userAccountBaseTypeEnums != null) {
            return userAccountBaseTypeEnums.getLabel();
        }
        return "" ;
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
    /**
     * json字符串 转 id集合
     * @param tagIds
     * @return
     */
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

    /**
     * html转简要文本
     * @param content
     * @param defaultValue
     * @return
     */
    default String handleHtmlDomToText(String content,String defaultValue){
        return (StringUtils.isBlank(content)) ? defaultValue : MyStringUtil.htmlDomToText(content, null);
    }

    /**
     * 创建用户 entity转vo
     * @param entity
     * @return
     */
    default UserAccountVo translateCreateUserEntityToVo(UserAccount entity){
        if (entity == null) {
            return null;
        }
        UserAccountVo vo = userAccountVoMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * 更新用户 entity转vo
     * @param entity
     * @return
     */
    default UserAccountVo translateUpdateUserEntityToVo(UserAccount entity){
        if (entity == null) {
            return null;
        }
        UserAccountVo vo = userAccountVoMapstruct.transferEntityToVo(entity);
        return vo;
    }

    default DefineTenantVo commonTranslateDefineTenantEntityToVo(DefineTenant entity){
        return defineTenantVoMapstruct.transferEntityToVo(entity);
    }

}
