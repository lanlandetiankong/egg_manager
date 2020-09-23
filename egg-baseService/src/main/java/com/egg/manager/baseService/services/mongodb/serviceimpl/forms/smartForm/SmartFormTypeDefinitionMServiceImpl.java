package com.egg.manager.baseService.services.mongodb.serviceimpl.forms.smartForm;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.mongodb.mservices.service.forms.smartForm.SmartFormTypeDefinitionMService;
import com.egg.manager.api.servicesimpl.mongodb.serviceimpl.MyBaseMongoServiceImpl;
import com.egg.manager.common.base.beans.front.FrontEntitySelectBean;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMO;
import com.egg.manager.persistence.db.mongo.repository.forms.SmartFormTypeDefinitionRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * MongoDb-FormTypeDefinitionMO 表单类型定义-MService
 */
@Service(interfaceClass = SmartFormTypeDefinitionMService.class)
public class SmartFormTypeDefinitionMServiceImpl extends MyBaseMongoServiceImpl<SmartFormTypeDefinitionRepository, SmartFormTypeDefinitionMO, String>
        implements SmartFormTypeDefinitionMService {

    @Override
    public MyCommonResult<SmartFormTypeDefinitionMO> dealResultListToEnums(MyCommonResult<SmartFormTypeDefinitionMO> result, List<SmartFormTypeDefinitionMO> list) {
        List<FrontEntitySelectBean> enumList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(list)) {
            for (SmartFormTypeDefinitionMO typeDefinitionMO : list) {
                enumList.add(new FrontEntitySelectBean(typeDefinitionMO.getFid(), typeDefinitionMO.getName()));
            }
        }
        result.setEnumList(enumList);
        return result;
    }
}
