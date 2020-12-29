package com.egg.manager.baseservice.serviceimpl.em.forms.mongo;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.facade.api.exchange.servicesimpl.mongodb.MyBaseMgoServiceImpl;
import com.egg.manager.facade.api.services.em.forms.mongo.smartform.SmartFormTypeDefinitionMgoService;
import com.egg.manager.facade.persistence.commons.base.beans.front.FrontSelectBean;
import com.egg.manager.facade.persistence.commons.base.beans.helper.WebResult;
import com.egg.manager.facade.persistence.em.forms.db.mongo.mo.SmartFormTypeDefinitionMgo;
import com.egg.manager.facade.persistence.em.forms.db.mongo.repository.SmartFormTypeDefinitionRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description表单类型定义
 * @date 2020/10/20
 */
@Slf4j
@Service(interfaceClass = SmartFormTypeDefinitionMgoService.class)
public class SmartFormTypeDefinitionMgoServiceImpl extends MyBaseMgoServiceImpl<SmartFormTypeDefinitionRepository, SmartFormTypeDefinitionMgo, String>
        implements SmartFormTypeDefinitionMgoService {

    @Override
    public WebResult dealResultListToEnums(WebResult result, List<SmartFormTypeDefinitionMgo> list) {
        List<FrontSelectBean> enumList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(list)) {
            for (SmartFormTypeDefinitionMgo typeDefinitionMgo : list) {
                enumList.add(new FrontSelectBean<String>(typeDefinitionMgo.getFid(), typeDefinitionMgo.getName()));
            }
        }
        result.putEnumData(enumList);
        return result;
    }
}
