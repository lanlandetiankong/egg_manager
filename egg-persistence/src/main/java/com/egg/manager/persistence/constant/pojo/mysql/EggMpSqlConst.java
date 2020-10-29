package com.egg.manager.persistence.constant.pojo.mysql;

/**
 * @Description:
 * @ClassName: EggMpSqlConst
 * @Author: zhoucj
 * @Date: 2020/10/28 9:30
 */
public interface EggMpSqlConst {
    /**
     * 当前登录用户
     */
    String LOGIN_USER = "loginUser" ;

    /**
     * 字段-最后修改人id
     */
    String COLUMN_LAST_MODIFYER_ID = "last_modifyer_id" ;
    /**
     * 字段-更新时间
     */
    String COLUMN_UPDATE_TIME = "update_time" ;
    /**
     * 数据被删除时间
     */
    String COLUMN_DELETE_TIME = "deleted_time" ;
    /**
     * 字段-fid
     */
    String COLUMN_FID = "fid" ;

    /**
     * MYSQL-时间函数
     */
    String MYSQL_DATE_FUNC_NOW ="now()";
    /**
     * SQLSERVER-时间函数
     */
    String SQLSERVER_DATE_FUNC_NOW ="GETDATE()";

}