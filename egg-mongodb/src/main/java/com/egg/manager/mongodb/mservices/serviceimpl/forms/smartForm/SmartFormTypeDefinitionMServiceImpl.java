package com.egg.manager.mongodb.mservices.serviceimpl.forms.smartForm;

import com.egg.manager.common.base.beans.FrontEntitySelectBean;
import com.egg.manager.mongodb.mservices.service.forms.smartForm.SmartFormTypeDefinitionMService;
import com.egg.manager.mongodb.mservices.serviceimpl.MyBaseMongoServiceImpl;
import com.egg.manager.persistence.helper.MyCommonResult;
import com.egg.manager.persistence.mongo.dao.forms.SmartFormTypeDefinitionRepository;
import com.egg.manager.persistence.mongo.mo.forms.SmartFormTypeDefinitionMO;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * MongoDb-FormTypeDefinitionMO 表单类型定义-MService
 */
@Service
public class SmartFormTypeDefinitionMServiceImpl  extends MyBaseMongoServiceImpl<SmartFormTypeDefinitionRepository, SmartFormTypeDefinitionMO,String>
        implements SmartFormTypeDefinitionMService {

    @Override
    public void dealResultListSetToEntitySelect(MyCommonResult<SmartFormTypeDefinitionMO> result,List<SmartFormTypeDefinitionMO> list){
        List<FrontEntitySelectBean> enumList = new ArrayList<>();
        if(list != null && list.isEmpty() == false){
            for(SmartFormTypeDefinitionMO typeDefinitionMO : list){
                enumList.add(new FrontEntitySelectBean(typeDefinitionMO.getFid(),typeDefinitionMO.getName())) ;
            }
        }
        result.setEnumList(enumList);
    }
}
