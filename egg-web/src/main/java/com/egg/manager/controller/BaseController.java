package com.egg.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.egg.manager.common.base.constant.pagination.AntdvPaginationBean;
import com.egg.manager.common.util.str.MyStringUtil;
import com.egg.manager.common.web.helper.MyCommonResult;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import scala.Int;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/10/5
 * \* Time: 14:28
 * \* Description:
 * \
 */
public class BaseController {
    public String actionSuccessMsg = "操作成功" ;

    public static boolean checkFieldStrBlank(String... strs) {
        boolean blankFlag = false ;
        if(strs.length > 0){
            for (String str : strs) {
                if(StringUtils.isBlank(str)) {
                    blankFlag = true ;
                    break ;
                }
            }
        }
        return blankFlag ;
    }


    public  void dealCommonErrorCatch(MyCommonResult result,Exception e) {
        dealCommonErrorCatch(result,e,null,true,true) ;
    }



    /**
     *
     * @param result
     * @param e
     * @param appendMsg 异常信息
     * @param isAppend appendMsg是否追加到 errorMsg 后面
     */
    public  void dealCommonErrorCatch(MyCommonResult result,Exception e,String appendMsg,boolean isAppend,boolean isPrintStackTrace) {
        result.setHasError(true);
        String errmsg = null ;
        if(isAppend){
            errmsg = e.getMessage() + (StringUtils.isBlank(appendMsg) ? "" : appendMsg) ;
        }   else {
            errmsg = appendMsg ;
        }
        if(isPrintStackTrace){
            e.printStackTrace();
        }
        result.setErrorMsg(errmsg);
    }

    public  void dealCommonSuccessCatch(MyCommonResult result,String info) {
        result.setInfo(info);
    }


    /**
     * 解析 搜索条件 map
     * @param queryJson
     * @return
     */
    public Map<String,Object> parseQueryJsonToMap(String queryJson) {
        Map<String,Object> map = new HashMap<>() ;
        if(StringUtils.isNotBlank(queryJson) && queryJson != "{}"){
            JSONObject jsonObject = JSONObject.parseObject(queryJson);
            if(jsonObject != null && jsonObject.isEmpty() == false){
                for (String jsonKey : jsonObject.keySet()){
                    //驼峰参数 转 下划线 参数 风格
                    String jsonStr = MyStringUtil.camelToUnderline(jsonKey,false);
                    String jsonVal = jsonObject.getString(jsonKey) ;
                    if(StringUtils.isNotBlank(jsonVal)){
                        map.put(jsonStr,jsonVal) ;
                    }
                }
            }
        }
        return map ;
    }


    public JSONObject parseQueryJsonToObject(String queryJson) {
        JSONObject jsonObject = null ;
        if(StringUtils.isNotBlank(queryJson) && queryJson != "{}"){
            jsonObject = JSONObject.parseObject(queryJson);
        }
        jsonObject = jsonObject != null ? jsonObject : new JSONObject() ;
        return jsonObject ;
    }

    /**
     * 取得分页 bean
     * @param paginationJson
     * @return
     */
    public AntdvPaginationBean parsePaginationJsonToBean(String paginationJson) {
        AntdvPaginationBean paginationBean = null ;
        if(StringUtils.isNotBlank(paginationJson)){
            paginationBean = JSONObject.parseObject(paginationJson, AntdvPaginationBean.class) ;
        }
        return paginationBean ;
    }

    /**
     * 取得分页 配置 -> mybatis-plus
     * @param paginationBean
     * @return
     */
    public RowBounds parsePaginationToRowBounds(AntdvPaginationBean paginationBean) {
        if(paginationBean != null){
            Integer current = paginationBean.getCurrent();
            Integer pageSize = paginationBean.getPageSize();
            current = current != null ? current : 1;
            pageSize = pageSize != null ? pageSize : 0;
            int offset = (current - 1) * pageSize ;
            return new RowBounds(offset,pageSize) ;
        }   else {
            return new RowBounds() ;
        }
    }


    public void dealSetConditionsMapToEntityWrapper(EntityWrapper entityWrapper, Map<String,Object> queryMap){
        if(queryMap != null && queryMap.isEmpty() == false){
            Iterator<String> queryKeyIter = queryMap.keySet().iterator();
            while (queryKeyIter.hasNext()){
                String queryKey = queryKeyIter.next() ;
                entityWrapper.eq(queryKey,queryMap.get(queryKey)) ;
            }
        }
    }
}
