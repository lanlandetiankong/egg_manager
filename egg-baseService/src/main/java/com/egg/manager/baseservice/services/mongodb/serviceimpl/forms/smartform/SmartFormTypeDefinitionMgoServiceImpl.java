package com.egg.manager.baseservice.services.mongodb.serviceimpl.forms.smartform;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.mongodb.mservices.service.forms.smartform.SmartFormTypeDefinitionMgoService;
import com.egg.manager.api.servicesimpl.mongodb.serviceimpl.MyBaseMgoServiceImpl;
import com.egg.manager.common.base.beans.front.FrontEntitySelectBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMgo;
import com.egg.manager.persistence.db.mongo.repository.forms.SmartFormTypeDefinitionRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description:表单类型定义
 * @date 2020/10/20
 */
@Slf4j
@Service(interfaceClass = SmartFormTypeDefinitionMgoService.class)
public class SmartFormTypeDefinitionMgoServiceImpl extends MyBaseMgoServiceImpl<SmartFormTypeDefinitionRepository, SmartFormTypeDefinitionMgo, Long>
        implements SmartFormTypeDefinitionMgoService {

    @Override
    public MyCommonResult<SmartFormTypeDefinitionMgo> dealResultListToEnums(MyCommonResult<SmartFormTypeDefinitionMgo> result, List<SmartFormTypeDefinitionMgo> list) {
        List<FrontEntitySelectBean> enumList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(list)) {
            for (SmartFormTypeDefinitionMgo typeDefinitionMgo : list) {
                enumList.add(new FrontEntitySelectBean(typeDefinitionMgo.getFid(), typeDefinitionMgo.getName()));
            }
        }
        result.setEnumList(enumList);
        return result;
    }
}
