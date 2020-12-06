package com.egg.manager.persistence.commons.base.beans.helper;

import com.egg.manager.persistence.commons.base.beans.file.AntdFileUploadBean;
import com.egg.manager.persistence.commons.base.beans.file.FileResBean;
import com.egg.manager.persistence.commons.base.beans.front.FrontSelectBean;
import com.egg.manager.persistence.commons.base.query.pagination.antdv.AntdvPage;
import com.egg.manager.persistence.em.user.pojo.bean.UserAccountToken;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @Description:
 * @ClassName: AbstractResult
 * @Author: zhoucj
 * @Date: 2020/11/12 16:12
 */
public abstract class AbstractResult extends HashMap implements BaseResultConstant {
    public <T> T getTheVal(String key, Class<T> clazz) {
        return getTheVal(key, clazz, null);
    }

    public <T> T getTheVal(String key, Class<T> clazz, T ifNullObj) {
        if (StringUtils.isBlank(key)) {
            return ifNullObj;
        }
        Object o = this.get(key);
        if (o == null) {
            return ifNullObj;
        }
        return (T) o;
    }

    /**
     * setter
     * @param val
     */

    public void putBean(Object val) {
        this.put(BEAN, val);
    }


    public void putSuccess(Boolean val) {
        this.put(SUCCESS, val);
    }


    public void putHasWarning(Boolean val) {
        this.put(WARNING, val);
    }


    public void putCount(Long val) {
        this.put(COUNT, val);
    }

    public void putCount(Integer val) {
        if (val == null) {
            this.put(COUNT, null);
        } else {
            this.put(COUNT, Long.valueOf(val));
        }
    }


    public void putCode(Integer val) {
        this.put(CODE, val);
    }

    public void putErrorActionType(String val) {
        this.put(ERROR_ACTION_TYPE, val);
    }

    public void putErrorMsg(String val) {
        this.put(ERROR_MSG, val);
    }

    public void putMsg(String val) {
        this.put(MSG, val);
    }

    public void putGridList(List val) {
        this.put(GRID_LIST, val);
    }

    public void putAuthorization(String val) {
        this.put(AUTHORIZATION, val);
    }

    public void putDataMap(Map val) {
        this.put(DATA_MAP, val);
    }

    public void putEnumData(List<FrontSelectBean> val) {
        val = val == null ? val : new ArrayList();
        this.put(ENUM_DATA, EnumRstBean.<FrontSelectBean,String>builder().list(val).build());
    }

    public void putEnumData(List val, List checkeds) {
        val = val == null ? val : new ArrayList();
        checkeds = checkeds == null ? checkeds : new ArrayList();
        this.put(ENUM_DATA, EnumRstBean.builder().list(val).checkeds(checkeds).build());
    }


    public void putPermissionSet(Collection collection) {
        this.put(KEY_PERMISSION_SET, collection);
    }

    public void putRouterUrlSet(Collection collection) {
        this.put(KEY_ROUTER_URL_SET, collection);
    }

    public void putAccountToken(UserAccountToken token) {
        this.put(KEY_ACCOUNTTOKEN, token);
    }

    public void putFileUploaderBeanList(List<AntdFileUploadBean> uploadBeanList) {
        this.put(KEY_FILEUPLOAD_BEANLIST, uploadBeanList);
    }

    public void putFileResBean(FileResBean fileResBean) {
        this.put(KEY_FILERES_BEAN, fileResBean);
    }


    /**
     * 设置分页信息
     * @param vpage
     * @param total
     * @param
     */
    public void settingPage(AntdvPage vpage, Long total) {
        if (vpage != null) {
            vpage.setTotal(total);
        }
        this.put(PAGINATION_BEAN, vpage);
    }

    /**
     * getter
     * @return
     */
    public boolean isSuccess() {
        return (Boolean) this.get(SUCCESS);
    }

    public String getMsg() {
        return (String) this.get(MSG);
    }

    public String getErrorMsg() {
        return (String) this.get(ERROR_MSG);
    }

    public List getGridList() {
        return (List) this.get(GRID_LIST);
    }


}