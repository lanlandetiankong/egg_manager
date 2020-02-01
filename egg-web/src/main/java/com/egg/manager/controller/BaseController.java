package com.egg.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.egg.manager.common.util.str.MyStringUtil;
import com.egg.manager.common.web.helper.MyCommonResult;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
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
        dealCommonErrorCatch(result,e,null) ;
    }
    public  void dealCommonErrorCatch(MyCommonResult result,Exception e,String appendMsg) {
        result.setHasError(true);
        String errmsg = e.getMessage() + (StringUtils.isBlank(appendMsg) ? "" : appendMsg) ;
        result.setErrorMsg(errmsg);
    }

    public Map<String,Object> parseQueryJsonToMap(String queryJson) {
        Map<String,Object> map = new HashMap<>() ;
        if(StringUtils.isNotBlank(queryJson) && queryJson != "{}"){
            JSONObject jsonObject = JSONObject.parseObject(queryJson);
            if(jsonObject != null && jsonObject.isEmpty() == false){
                for (String jsonKey : jsonObject.keySet()){
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
}
