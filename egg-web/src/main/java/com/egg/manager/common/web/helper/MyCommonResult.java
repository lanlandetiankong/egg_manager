package com.egg.manager.common.web.helper;

import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.vo.session.UserAccountToken;
import lombok.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class MyCommonResult<T> implements Serializable {


    private String token ;
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

    private T bean;
    private UserAccountToken accountToken ;
    private String code;
    private Integer status ;
    private String errorMsg;

}
