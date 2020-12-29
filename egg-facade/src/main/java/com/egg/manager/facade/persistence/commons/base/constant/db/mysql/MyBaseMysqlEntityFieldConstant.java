package com.egg.manager.facade.persistence.commons.base.constant.db.mysql;

import com.egg.manager.facade.persistence.commons.base.query.FieldConst;
import com.egg.manager.facade.persistence.commons.util.basic.jvm.reflex.config.EggPojoReflexFieldConfig;

import java.util.Date;


/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
public interface MyBaseMysqlEntityFieldConstant {
    /**
     * fid，即id
     */
    EggPojoReflexFieldConfig<String> FID = new EggPojoReflexFieldConfig(FieldConst.FIELD_FID, String.class, FieldConst.COL_FID);
    /**
     * 备注
     */
    EggPojoReflexFieldConfig<String> REMARK = new EggPojoReflexFieldConfig(FieldConst.FIELD_REMARK, String.class, FieldConst.COL_REMARK);
    /**
     * 状态值
     */
    EggPojoReflexFieldConfig<Short> STATE = new EggPojoReflexFieldConfig(FieldConst.FIELD_STATE, Short.class, FieldConst.COL_STATE);
    /**
     * 状态值
     */
    EggPojoReflexFieldConfig<Short> IS_DELETED = new EggPojoReflexFieldConfig(FieldConst.FIELD_IS_DELETED, Short.class, FieldConst.COL_IS_DELETED);
    /**
     * 创建时间
     */
    EggPojoReflexFieldConfig<Date> CREATE_TIME = new EggPojoReflexFieldConfig(FieldConst.FIELD_CREATE_TIME, Date.class, FieldConst.COL_CREATE_TIME);
    /**
     * 更新时间
     */
    EggPojoReflexFieldConfig<Date> UPDATE_TIME = new EggPojoReflexFieldConfig(FieldConst.FIELD_UPDATE_TIME, Date.class, FieldConst.COL_UPDATE_TIME);
    /**
     * 创建人id
     */
    EggPojoReflexFieldConfig<String> CREATE_USER_ID = new EggPojoReflexFieldConfig(FieldConst.FIELD_CREATE_USER_ID, String.class, FieldConst.COL_CREATE_USER_ID);
    /**
     * 最后更新人id
     */
    EggPojoReflexFieldConfig<String> LAST_MODIFYER_ID = new EggPojoReflexFieldConfig(FieldConst.FIELD_LAST_MODIFYER_ID, String.class, FieldConst.COL_LAST_MODIFYER_ID);
    /**
     * 版本号
     */
    EggPojoReflexFieldConfig<Integer> VERSION = new EggPojoReflexFieldConfig(FieldConst.FIELD_VERSION, Integer.class, FieldConst.COL_VERSION);


}
