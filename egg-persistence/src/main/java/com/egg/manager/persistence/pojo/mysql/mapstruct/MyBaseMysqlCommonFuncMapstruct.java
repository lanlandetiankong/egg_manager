package com.egg.manager.persistence.pojo.mysql.mapstruct;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.egg.manager.common.base.exception.MyRuntimeBusinessException;
import com.egg.manager.common.util.str.MyStringUtil;
import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.mysql.dto.MyBaseMysqlDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.organization.DefineTenantMapstruct;
import com.egg.manager.persistence.pojo.mysql.mapstruct.user.UserAccountMapstruct;
import com.egg.manager.persistence.pojo.mysql.vo.MyBaseMysqlVo;
import com.egg.manager.persistence.pojo.mysql.vo.organization.DefineTenantVo;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserAccountVo;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.MapperConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 可在该接口写公用的转化方法,定义的方法请勿修改方法名、参数、返回值等！
 *
 *
 * Notes:
 * @Context : 该注解用于添加额外参数，不会对其进行判断null等操作
 */
@MapperConfig(disableSubMappingMethodsGeneration=true)
public interface MyBaseMysqlCommonFuncMapstruct<E,V extends MyBaseMysqlVo,D extends MyBaseMysqlDto> {

     UserAccountMapstruct userAccountVoMapstruct = UserAccountMapstruct.INSTANCE ;
     DefineTenantMapstruct defineTenantVoMapstruct = DefineTenantMapstruct.INSTANCE ;



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
     * 取得 登录用户id
     * @param userAccount
     * @param required 当userAccount为null时是否抛出异常
     * @return
     */
    default String handleGetLoginUserId(UserAccount userAccount, boolean required){
        if(userAccount == null){
            if(required){
                throw new MyRuntimeBusinessException("无法取得当前用户信息！");
            }
            return null ;
        }   else {
            return userAccount.getFid() ;
        }
    }

    /**
     * html转简要文本
     * @param content
     * @param defaultValue
     * @return
     */
    default String handleHtmlDomToText(String content, String defaultValue){
        return (StringUtils.isBlank(content)) ? defaultValue : MyStringUtil.htmlDomToText(content, null);
    }

    default Date handleGetNowDate(){
        return new Date();
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
