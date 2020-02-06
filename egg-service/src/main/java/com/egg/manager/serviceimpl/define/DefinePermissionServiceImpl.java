package com.egg.manager.serviceimpl.define;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.util.str.MyUUIDUtil;
import com.egg.manager.common.web.helper.MyCommonResult;
import com.egg.manager.entity.define.DefinePermission;
import com.egg.manager.entity.define.DefineRole;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.mapper.define.DefinePermissionMapper;
import com.egg.manager.service.define.DefinePermissionService;
import com.egg.manager.service.user.UserAccountService;
import com.egg.manager.vo.define.DefinePermissionVo;
import com.egg.manager.vo.user.UserAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2019/9/14
 * \* Time: 23:41
 * \* Description:
 * \
 */
@Service
public class DefinePermissionServiceImpl extends ServiceImpl<DefinePermissionMapper,DefinePermission> implements DefinePermissionService {

    private int defaultVersion = 0 ;

    @Autowired
    private DefinePermissionMapper definePermissionMapper ;

    /**
     * 根据 DefineRoleBean 取得 所有权限
     * @param defineRoles
     * @return
     */
    @Override
    public List<DefinePermission> dealGetAllPermissionByRoles(List<DefineRole> defineRoles) {
        EntityWrapper<DefinePermission> definePermissionEntityWrapper = new EntityWrapper<DefinePermission>() ;
        definePermissionEntityWrapper.where("state={0}", BaseStateEnum.ENABLED.getValue()) ;
        DefineRole role = new DefineRole() ;
        return null;
    }


    /**
     * 权限定义-新增
     * @param definePermissionVo
     * @throws Exception
     */
    @Override
    public Integer dealAddDefinePermission(DefinePermissionVo definePermissionVo) throws Exception{
        Date now = new Date() ;
        DefinePermission definePermission = DefinePermissionVo.transferVoToEntity(definePermissionVo);
        definePermission.setFid(MyUUIDUtil.renderSimpleUUID());
        definePermission.setVersion(defaultVersion);
        definePermission.setState(BaseStateEnum.ENABLED.getValue());
        definePermission.setCreateTime(now);
        definePermission.setUpdateTime(now);
        Integer addCount = definePermissionMapper.insert(definePermission) ;
        return addCount ;
    }


    /**
     * 权限定义-更新
     * @param definePermissionVo
     * @param updateAll 是否更新所有字段
     * @throws Exception
     */
    @Override
    public Integer dealUpdateDefinePermission(DefinePermissionVo definePermissionVo,boolean updateAll) throws Exception{
        Integer changeCount = 0;
        Date now = new Date() ;
        definePermissionVo.setUpdateTime(now);
        DefinePermission definePermission = DefinePermissionVo.transferVoToEntity(definePermissionVo);
        if(updateAll){  //是否更新所有字段
            changeCount = definePermissionMapper.updateAllColumnById(definePermission) ;
        }   else {
            changeCount = definePermissionMapper.updateById(definePermission) ;
        }
        return changeCount ;
    }

    /**
     * 权限定义-删除
     * @param delIds 要删除的权限id 集合
     * @throws Exception
     */
    @Override
    public Integer dealDelDefinePermissionByArr(String[] delIds) throws Exception{
        Integer delCount = 0 ;
        if(delIds != null && delIds.length > 0) {
            List<String> delIdList = Arrays.asList(delIds) ;
            //批量伪删除
            delCount = definePermissionMapper.batchFakeDelByIds(delIdList);
        }
        return delCount ;
    }

    /**
     * 权限定义-删除
     * @param delId 要删除的权限id
     * @throws Exception
     */
    @Override
    public Integer dealDelDefinePermission(String delId) throws Exception{
        DefinePermission definePermission = DefinePermission.builder().fid(delId).state(BaseStateEnum.DELETE.getValue()).build() ;
        Integer delCount = definePermissionMapper.updateById(definePermission);
        return delCount ;
    }
}
