package com.egg.manager.persistence.constant.pojo.mysql;

import com.egg.manager.persistence.utils.reflex.config.EggPojoReflexFieldConfig;

import java.util.Date;


/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
public interface MyBaseMysqlEntityFieldConstant {
    /**
     * fid，即id
     */
    EggPojoReflexFieldConfig<String> FID = new EggPojoReflexFieldConfig("fid", String.class, "fid");
    /**
     * 备注
     */
    EggPojoReflexFieldConfig<String> REMARK = new EggPojoReflexFieldConfig("remark", String.class, "remark");
    /**
     * 状态值
     */
    EggPojoReflexFieldConfig<Short> STATE = new EggPojoReflexFieldConfig("state", Short.class, "state");
    /**
     * 创建时间
     */
    EggPojoReflexFieldConfig<Date> CREATE_TIME = new EggPojoReflexFieldConfig("createTime", Date.class, "create_time");
    /**
     * 更新时间
     */
    EggPojoReflexFieldConfig<Date> UPDATE_TIME = new EggPojoReflexFieldConfig("updateTime", Date.class, "update_time");
    /**
     * 创建人id
     */
    EggPojoReflexFieldConfig<String> CREATE_USER_ID = new EggPojoReflexFieldConfig("createUserId", String.class, "create_user_id");
    /**
     * 最后更新人id
     */
    EggPojoReflexFieldConfig<String> LAST_MODIFYER_ID = new EggPojoReflexFieldConfig("lastModifyerId", String.class, "last_modifyer_id");
    /**
     * 版本号
     */
    EggPojoReflexFieldConfig<Integer> VERSION = new EggPojoReflexFieldConfig("version", Integer.class, "version");


}
