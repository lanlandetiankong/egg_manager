package com.egg.manager.persistence.helper;

import com.egg.manager.common.base.beans.file.AntdFileUploadBean;
import com.egg.manager.common.base.beans.file.FileResBean;
import com.egg.manager.common.base.props.upload.UploadProps;
import com.egg.manager.common.base.pagination.AntdvPaginationBean;
import com.egg.manager.persistence.webvo.session.UserAccountToken;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyCommonResult<T> implements Serializable {

    private String token ;
    private String authorization ;
    //提示信息、错误信息等，用于展示
    private String info ;
    //传递到前端的 信息，用于使用
    private String msg ;
    private boolean hasError = false;
    private boolean hasWarning = false ;
    private boolean actionFlag = true ;
    //操作成功 的数量
    private long count ;
    //存储: 表格总数量
    private long total ;

    //域
    private String field;
    //跳转url
    private String backUrl ;
    //存储 表格数据
    private List rows ;
    //存储 树集合
    private List resultList ;
    //存储 一些自定义属性的map集合
    private Map resultMap ;
    //存储 不重复的集合
    private Set resultSet ;
    //枚举 列表
    private List enumList;
    private List enumDefaultCheckList;

    //前端接收到异常后的操作标识，需与前端一致(axios拦截器设置必须hasError为true才会处理到这个
    private String errorActionType ;

    private T bean;
    private UserAccountToken accountToken ;
    private String code;
    private Integer status ;
    private String errorMsg;

    private FileResBean fileResBean ;
    private List<AntdFileUploadBean> fileUploaderBeanList ;
    private AntdvPaginationBean paginationBean ;    //分页bean

    private UploadProps uploadProps ;

    private Set<String> routerUrlSet ;
    private Set<String> permissionSet ;


    public void myAntdvPaginationBeanSet(AntdvPaginationBean paginationBean,Integer total){
        if(paginationBean != null){
            paginationBean.setTotal(total);
        }
        this.paginationBean = paginationBean ;
    }
}