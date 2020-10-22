package com.egg.manager.persistence.pojo.mongo.mapstruct.imap.fundamental;

import com.egg.manager.persistence.db.mongo.mo.MyBaseModelMgo;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormRecordMgo;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMgo;
import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.forms.SmartFormRecordMapstruct;
import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.forms.SmartFormTypeDefinitionMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.BaseModelMgvo;
import com.egg.manager.persistence.pojo.mongo.mvo.forms.SmartFormRecordMgvo;
import com.egg.manager.persistence.pojo.mongo.mvo.forms.SmartFormTypeDefinitionMgvo;
import org.mapstruct.MapperConfig;

/**
 * @author zhoucj
 * @description:可在该接口写公用的转化方法,定义的方法请勿修改方法名、参数、返回值等！
 * @date 2020/10/20
 */
@MapperConfig(disableSubMappingMethodsGeneration = true)
public interface MyBaseMongoCommonFuncMapstruct<E extends MyBaseModelMgo, V extends BaseModelMgvo> {

    SmartFormTypeDefinitionMapstruct smartFormTypeDefinitionMapstruct = SmartFormTypeDefinitionMapstruct.INSTANCE;
    SmartFormRecordMapstruct smartFormRecordMapstruct = SmartFormRecordMapstruct.INSTANCE;

    /**
     * 表单类型定义 mvo转mo
     * @param mgvo
     * @return
     */
    default SmartFormTypeDefinitionMgo commonTranslateSmartFormTypeDefinitionMgvoToMo(SmartFormTypeDefinitionMgvo mgvo) {
        return smartFormTypeDefinitionMapstruct.translateMgvoToMgo(mgvo);
    }

    /**
     * 表单记录 mvo转mo
     * @param mgvo
     * @return
     */
    default SmartFormRecordMgo commonTranslateSmartFormRecordMgvoToMgo(SmartFormRecordMgvo mgvo) {
        return smartFormRecordMapstruct.translateMgvoToMgo(mgvo);
    }


}
