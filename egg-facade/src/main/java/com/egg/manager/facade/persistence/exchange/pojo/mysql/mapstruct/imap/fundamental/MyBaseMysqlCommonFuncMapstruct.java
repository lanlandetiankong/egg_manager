package com.egg.manager.facade.persistence.exchange.pojo.mysql.mapstruct.imap.fundamental;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.egg.manager.facade.persistence.commons.base.constant.basic.BaseRstMsgConstant;
import com.egg.manager.facade.persistence.commons.base.exception.MyRuntimeBusinessException;
import com.egg.manager.facade.persistence.commons.util.data.str.MyStringUtil;
import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineTenantEntity;
import com.egg.manager.facade.persistence.em.define.pojo.dto.EmDefineDepartmentDto;
import com.egg.manager.facade.persistence.em.define.pojo.dto.EmDefineTenantDto;
import com.egg.manager.facade.persistence.em.define.pojo.mapstruct.imap.EmDefineDepartmentMapstruct;
import com.egg.manager.facade.persistence.em.define.pojo.mapstruct.imap.EmDefineTenantMapstruct;
import com.egg.manager.facade.persistence.em.define.pojo.vo.EmDefineDepartmentVo;
import com.egg.manager.facade.persistence.em.define.pojo.vo.EmDefineTenantVo;
import com.egg.manager.facade.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import com.egg.manager.facade.persistence.em.user.pojo.mapstruct.imap.EmUserAccountMapstruct;
import com.egg.manager.facade.persistence.em.user.pojo.vo.EmUserAccountVo;
import com.egg.manager.facade.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import com.egg.manager.facade.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.MapperConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhoucj
 * @description 可在该接口写公用的转化方法, 定义的方法请勿修改方法名、参数、返回值等！
 * @date 2020/10/20
 * @Context : 该注解用于添加额外参数，不会对其进行判断null等操作
 */
@MapperConfig(disableSubMappingMethodsGeneration = true)
public interface MyBaseMysqlCommonFuncMapstruct<E, V extends MyBaseMysqlVo, D extends MyBaseMysqlDto> {

    EmUserAccountMapstruct USER_ACCOUNT_MAPSTRUCT = EmUserAccountMapstruct.INSTANCE;
    EmDefineTenantMapstruct DEFINE_TENANT_MAPSTRUCT = EmDefineTenantMapstruct.INSTANCE;
    EmDefineDepartmentMapstruct DEFINE_DEPARTMENT_MAPSTRUCT = EmDefineDepartmentMapstruct.INSTANCE;


    /**
     * tagIds 转 json字符串
     * @param tagIds
     * @return
     */
    default String handleTagIdListToJsonString(List<String> tagIds) {
        tagIds = tagIds != null ? tagIds : new ArrayList<>();
        return JSON.toJSONString(tagIds);
    }

    /**
     * json字符串 转 id集合
     * @param tagIds
     * @return
     */
    default List<String> handleTagIdJsonStringToList(String tagIds) {
        List<String> tagList = null;
        if (StringUtils.isNotBlank(tagIds)) {
            try {
                tagList = JSONArray.parseArray(tagIds, String.class);
                if (CollectionUtil.isNotEmpty(tagList)) {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return tagList;
    }

    /**
     * 取得 登录用户id
     * @param emUserAccountEntity
     * @param required          当userAccount为null时是否抛出异常
     * @return
     */
    default String handleGetLoginUserId(EmUserAccountEntity emUserAccountEntity, boolean required) {
        if (emUserAccountEntity == null) {
            if (required) {
                throw new MyRuntimeBusinessException(BaseRstMsgConstant.ErrorMsg.shiroUnauthorized());
            }
            return null;
        } else {
            return emUserAccountEntity.getFid();
        }
    }

    /**
     * html转简要文本
     * @param content
     * @param defaultValue
     * @return
     */
    default String handleHtmlDomToText(String content, String defaultValue) {
        return (StringUtils.isBlank(content)) ? defaultValue : MyStringUtil.htmlDomToText(content, null);
    }

    /**
     * 取得当前时间
     * @return
     */
    default Date handleGetNowDate() {
        return new Date();
    }

    /**
     * 创建用户 entity转vo
     * @param entity
     * @return
     */
    default EmUserAccountVo translateCreateUserEntityToVo(EmUserAccountEntity entity) {
        if (entity == null) {
            return null;
        }
        EmUserAccountVo vo = USER_ACCOUNT_MAPSTRUCT.transferEntityToVo(entity);
        return vo;
    }

    /**
     * 更新用户 entity转vo
     * @param entity
     * @return
     */
    default EmUserAccountVo translateUpdateUserEntityToVo(EmUserAccountEntity entity) {
        if (entity == null) {
            return null;
        }
        EmUserAccountVo vo = USER_ACCOUNT_MAPSTRUCT.transferEntityToVo(entity);
        return vo;
    }

    /**
     * 租户 entity转为vo
     * @param entity
     * @return
     */
    default EmDefineTenantVo commonTranslateDefineTenantEntityToVo(EmDefineTenantEntity entity) {
        return DEFINE_TENANT_MAPSTRUCT.transferEntityToVo(entity);
    }

    /**
     * 租户 dto转为vo
     * @param dto
     * @return
     */
    default EmDefineTenantVo commonTranslateDefineTenantDtoToVo(EmDefineTenantDto dto) {
        return DEFINE_TENANT_MAPSTRUCT.transferDtoToVo(dto);
    }

    /**
     * 部门 dto转为vo
     * @param dto
     * @return
     */
    default EmDefineDepartmentVo commonTranslateDefineDepartmentDtoToVo(EmDefineDepartmentDto dto) {
        return DEFINE_DEPARTMENT_MAPSTRUCT.transferDtoToVo(dto);
    }
}
