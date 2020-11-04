package com.egg.manager.persistence.commons.constant.pojo.mysql;

/**
 * @Description:
 * @ClassName: EggMpSqlConst
 * @Author: zhoucj
 * @Date: 2020/10/28 9:30
 */
public interface EggMpSqlConst {
    /**
     * 参数KEY：当前登录用户
     */
    String PARAMOF_LOGIN_USER = "loginUser" ;
    /**
     * 参数KEY：排序
     */
    String PARAMOF_SORT_FIELD_LIST = "sortFieldList" ;
    /**
     * 参数KEY：查询字段
     */
    String PARAMOF_QUERY_FIELD_LIST = "queryFieldList" ;

    /**
     * 参数KEY：id集合
     */
    String PARAMOF_IDS = "ids" ;
    /**
     * 参数KEY：用户id
     */
    String PARAMOF_USER_ACCOUNT_ID = "userAccountId" ;





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