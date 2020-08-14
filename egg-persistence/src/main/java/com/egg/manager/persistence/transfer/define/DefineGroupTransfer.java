package com.egg.manager.persistence.transfer.define;


import com.egg.manager.persistence.entity.define.DefineGroup;
import com.egg.manager.persistence.transfer.MyBaseTransfer;
import com.egg.manager.persistence.vo.define.DefineGroupVo;

public class DefineGroupTransfer extends MyBaseTransfer {

    public static DefineGroupVo transferEntityToVo(DefineGroup defineTenant) {
        if (defineTenant == null) {
            return null;
        }
        DefineGroupVo defineTenantVo = new DefineGroupVo();
        defineTenantVo.setFid(defineTenant.getFid());
        defineTenantVo.setName(defineTenant.getName());
        defineTenantVo.setPid(defineTenant.getPid());
        defineTenantVo.setIsInherit(defineTenant.getIsInherit());
        defineTenantVo.setType(defineTenant.getType());
        defineTenantVo.setRemark(defineTenant.getRemark());
        defineTenantVo.setState(defineTenant.getState());
        defineTenantVo.setCreateTime(defineTenant.getCreateTime());
        defineTenantVo.setUpdateTime(defineTenant.getUpdateTime());
        defineTenantVo.setCreateUserId(defineTenant.getCreateUserId());
        defineTenantVo.setLastModifyerId(defineTenant.getLastModifyerId());
        return defineTenantVo;
    }


}
