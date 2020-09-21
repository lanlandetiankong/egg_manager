package com.egg.manager.persistence.pojo.mongo.mapstruct.imap.baseExtend;

import com.egg.manager.persistence.db.mongo.mo.MyBaseModelMO;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormRecordMO;
import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMO;
import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.forms.SmartFormRecordMapstruct;
import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.forms.SmartFormTypeDefinitionMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.MyBaseModelMVO;
import com.egg.manager.persistence.pojo.mongo.mvo.forms.SmartFormRecordMVO;
import com.egg.manager.persistence.pojo.mongo.mvo.forms.SmartFormTypeDefinitionMVO;
import org.mapstruct.MapperConfig;

/**
 * 可在该接口写公用的转化方法,定义的方法请勿修改方法名、参数、返回值等！
 * <p>
 * <p>
 * Notes:
 *
 * @Context : 该注解用于添加额外参数，不会对其进行判断null等操作
 */
@MapperConfig(disableSubMappingMethodsGeneration = true)
public interface MyBaseMongoCommonFuncMapstruct<E extends MyBaseModelMO, V extends MyBaseModelMVO> {

    SmartFormTypeDefinitionMapstruct smartFormTypeDefinitionMapstruct = SmartFormTypeDefinitionMapstruct.INSTANCE;
    SmartFormRecordMapstruct smartFormRecordMapstruct = SmartFormRecordMapstruct.INSTANCE;

    /**
     * 表单类型定义 mvo转mo
     *
     * @param mvo
     * @return
     */
    default SmartFormTypeDefinitionMO commonTranslateSmartFormTypeDefinitionMVOToMO(SmartFormTypeDefinitionMVO mvo) {
        return smartFormTypeDefinitionMapstruct.translateMvoToMo(mvo);
    }

    /**
     * 表单记录 mvo转mo
     *
     * @param mvo
     * @return
     */
    default SmartFormRecordMO commonTranslateSmartFormRecordMVOToMO(SmartFormRecordMVO mvo) {
        return smartFormRecordMapstruct.translateMvoToMo(mvo);
    }


}
