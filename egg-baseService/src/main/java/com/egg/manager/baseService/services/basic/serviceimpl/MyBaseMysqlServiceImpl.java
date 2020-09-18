package com.egg.manager.baseService.services.basic.serviceimpl;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.api.trait.routine.RoutineCommonFunc;
import com.egg.manager.common.base.beans.front.FrontEntitySelectBean;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.pagination.antdv.AntdvPaginationBean;
import com.egg.manager.common.base.pagination.antdv.AntdvSortBean;
import com.egg.manager.common.base.query.form.QueryFormFieldBean;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.persistence.bean.helper.MyCommonResult;
import com.egg.manager.persistence.constant.pojo.mysql.MyBaseMysqlEntityFieldConstant;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.mysql.transfer.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.MyBaseMysqlVo;
import com.egg.manager.persistence.utils.reflex.EggReflexUtil;
import com.egg.manager.persistence.utils.reflex.config.EggPojoReflexFieldConfig;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @ClassName: MyBaseMysqlServiceImpl
 * @Author: zhoucj
 * @Date: 2020/9/18 9:21
 */
public class MyBaseMysqlServiceImpl<M extends BaseMapper<T>,T extends Model<T>,V extends MyBaseMysqlVo,Trs extends MyBaseMysqlTransfer> extends ServiceImpl<M,T> {
    @Autowired
    private RoutineCommonFunc routineCommonFunc ;

    /**
     * 取得前端传递的分页配置
     * @param result
     * @param queryFormFieldBeanList
     * @param paginationBean
     * @param sortBeans
     * @return
     */
    public EntityWrapper<T> doGetPageQueryWrapper(UserAccount loginUser,MyCommonResult<V> result, List<QueryFormFieldBean> queryFormFieldBeanList, AntdvPaginationBean paginationBean,
                                                  List<AntdvSortBean> sortBeans){
        //解析 搜索条件
        EntityWrapper<T> entityWrapper = new EntityWrapper<T>();

        //取得 分页配置
        RowBounds rowBounds = routineCommonFunc.parsePaginationToRowBounds(paginationBean) ;
        //调用方法将查询条件设置到userAccountEntityWrapper
        this.dealSetConditionsMapToEntityWrapper(entityWrapper,queryFormFieldBeanList) ;
        //添加排序
        if(sortBeans != null && sortBeans.isEmpty() == false){
            for(AntdvSortBean sortBean : sortBeans){
                entityWrapper.orderBy(sortBean.getField(),sortBean.getOrderIsAsc());
            }
        }
        return entityWrapper ;
    }



    public void dealSetConditionsMapToEntityWrapper(EntityWrapper entityWrapper, List<QueryFormFieldBean> queryFieldBeanList){
        if(queryFieldBeanList != null && queryFieldBeanList.isEmpty() == false){
            for(QueryFormFieldBean queryFormFieldBean : queryFieldBeanList){
                Object fieldValue = queryFormFieldBean.getValue();
                if(fieldValue == null){
                    continue;
                }   else {
                    if("equals".equals(queryFormFieldBean.getMatching())){
                        entityWrapper.eq(queryFormFieldBean.getFieldName(),fieldValue) ;
                    }   else if("like".equals(queryFormFieldBean.getMatching())){
                        String fieldValueStr = String.valueOf(fieldValue) ;
                        entityWrapper.like(queryFormFieldBean.getFieldName(),fieldValueStr) ;
                    }   else if("notEquals".equals(queryFormFieldBean.getMatching())){
                        entityWrapper.ne(queryFormFieldBean.getFieldName(),fieldValue) ;
                    }
                }
            }
        }
    }


    /**
     * 更新Entity之前调用
     * @param loginUser
     * @param t
     * @param uuidFlag
     * @return
     */
    public T doBeforeCreate(UserAccount loginUser,T t,boolean uuidFlag){
        Date now = new Date() ;
        if(uuidFlag){
            EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.FID,MyUUIDUtil.renderSimpleUUID());
        }
        EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.STATE,BaseStateEnum.ENABLED.getValue());
        EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.CREATE_TIME,now);
        EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.UPDATE_TIME,now);
        if(loginUser != null){
            EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.CREATE_USER_ID,loginUser.getFid());
            EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.LAST_MODIFYER_ID,loginUser.getFid());
        }
        return t ;
    }

    /**
     * 更新Entity之前调用
     * @param loginUser
     * @param t
     * @return
     */
    public T doBeforeUpdate(UserAccount loginUser,T t){
        Date now = new Date() ;
        EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.STATE,BaseStateEnum.ENABLED.getValue());
        EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.UPDATE_TIME,now);
        if(loginUser != null){
            EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.LAST_MODIFYER_ID,loginUser.getFid());
        }
        return t ;
    }


    /**
     * 根据id删除(删除前调用)
     * @param loginUser
     * @param tClass
     * @param idVal
     * @return
     */
    public T doBeforeDeleteOneById(UserAccount loginUser,Class<T> tClass,String idVal){
        T t = EggReflexUtil.handlePojoGetInstance(tClass);
        EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.FID,idVal);
        if(loginUser != null){
            EggReflexUtil.handlePojoSetFieldValue(t, MyBaseMysqlEntityFieldConstant.LAST_MODIFYER_ID,loginUser.getFid());
        }
        return t ;
    }


    /**
     * 取得的结果 转为 枚举类型
     * @param result
     */
    public  <T,KV,KL> MyCommonResult  dealResultListSetToEntitySelect(MyCommonResult<T> result, EggPojoReflexFieldConfig<KV> valueConf,EggPojoReflexFieldConfig<KL> labelConf){
        List<FrontEntitySelectBean> enumList = new ArrayList<>();
        List<T> resultList = result.getResultList() ;
        if(resultList != null && resultList.isEmpty() == false){
            for(T t : resultList){
                KV kv = EggReflexUtil.handlePojoGetFieldValue(t, valueConf);
                KL kl = EggReflexUtil.handlePojoGetFieldValue(t, labelConf);
                String kvStr = kv != null ? (String) kv : "" ;
                String klStr = kl != null ? (String) kl : "" ;
                enumList.add(new FrontEntitySelectBean(kvStr,klStr)) ;
            }
        }
        result.setEnumList(enumList);
        return result;
    }

}
