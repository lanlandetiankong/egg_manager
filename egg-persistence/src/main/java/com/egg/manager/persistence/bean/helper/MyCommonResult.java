package com.egg.manager.persistence.bean.helper;

import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.common.base.beans.file.FileResBean;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.props.upload.UploadProps;
import com.egg.manager.persistence.bean.webvo.session.UserAccountToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyCommonResult<T> implements Serializable {
    /**
     * token
     */
    private String token;
    /**
     * 授权信息
     */
    private String authorization;
    /**
     * 提示信息、错误信息等，用于展示
     */
    private String info;
    /**
     * 传递到前端的 信息，用于使用
     */
    private String msg;
    /**
     * 是否有报错信息
     */
    private boolean hasError = false;
    /**
     * 是否有警告信息
     */
    private boolean hasWarning = false;
    /**
     * 操作成功标识
     */
    private boolean actionFlag = true;
    /**
     * 操作成功 的数量
     */
    private long count;
    /**
     * 表格总数量
     */
    private long total;
    /**
     * 域
     */
    private String field;
    /**
     * 跳转url
     */
    private String backUrl;
    /**
     *  表格数据
     */
    private List rows;
    /**
     * 树集合
     */
    private List resultList;
    /**
     * 存储 一些自定义属性的map集合
     */
    private Map resultMap;
    /**
     * 存储 不重复的集合
     */
    private Set resultSet;
    /**
     * 枚举 列表
     */
    private List enumList;
    /**
     * 枚举数据默认勾选-List
     */
    private List enumDefaultCheckList;
    /**
     * 前端接收到异常后的操作标识，需与前端一致(axios拦截器设置必须hasError为true才会处理到这个
     */
    private String errorActionType;
    /**
     * 指定类型的bean
     */
    private T bean;
    /**
     * 账号token
     */
    private UserAccountToken accountToken;
    /**
     * code
     */
    private String code;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 文件信息-bean
     */
    private FileResBean fileResBean;
    /**
     * 文件上传-beanList
     */
    private List<AntdFileUploadBean> fileUploaderBeanList;
    /**
     * 分页bean
     */
    private AntdvPaginationBean paginationBean;
    /**
     * 上传参数
     */
    private UploadProps uploadProps;
    /**
     * 可访问的路由地址-Set集合
     */
    private Set<String> routerUrlSet;
    /**
     * 权限-Set集合
     */
    private Set<String> permissionSet;


    public void myAntdvPaginationBeanSet(AntdvPaginationBean paginationBean, Integer total) {
        if (paginationBean != null) {
            paginationBean.setTotal(total);
        }
        this.paginationBean = paginationBean;
    }
}
