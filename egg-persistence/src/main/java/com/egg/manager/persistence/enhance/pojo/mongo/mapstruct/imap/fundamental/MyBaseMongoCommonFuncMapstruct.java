package com.egg.manager.persistence.enhance.pojo.mongo.mapstruct.imap.fundamental;

import com.egg.manager.persistence.enhance.db.mongo.mo.MyBaseModelMgo;
import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormRecordMgo;
import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormTypeDefinitionMgo;
import com.egg.manager.persistence.em.forms.pojo.mapstruct.imap.SmartFormRecordMapstruct;
import com.egg.manager.persistence.em.forms.pojo.mapstruct.imap.SmartFormTypeDefinitionMapstruct;
import com.egg.manager.persistence.enhance.pojo.mongo.mvo.BaseModelMgvo;
import com.egg.manager.persistence.em.forms.pojo.mvo.SmartFormRecordMgvo;
import com.egg.manager.persistence.em.forms.pojo.mvo.SmartFormTypeDefinitionMgvo;
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
