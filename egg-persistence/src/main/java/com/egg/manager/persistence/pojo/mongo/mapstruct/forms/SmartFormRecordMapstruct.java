package com.egg.manager.persistence.pojo.mongo.mapstruct.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormRecordMO;
import com.egg.manager.persistence.pojo.mongo.mapstruct.baseExtend.MyBaseMongoMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.forms.SmartFormRecordMVO;
import com.egg.manager.persistence.pojo.mongo.transfer.forms.SmartFormRecordTransfer;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * \* note:表单项
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:58
 * \* Description:
 * \
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {SmartFormRecordTransfer.class})
public interface SmartFormRecordMapstruct  extends MyBaseMongoMapstruct<SmartFormRecordMO,SmartFormRecordMVO> {

    SmartFormRecordMapstruct INSTANCE = Mappers.getMapper(SmartFormRecordMapstruct.class);

    @Mappings({
    })
    SmartFormRecordMO translateMvoToMo(SmartFormRecordMVO mvo);

}
