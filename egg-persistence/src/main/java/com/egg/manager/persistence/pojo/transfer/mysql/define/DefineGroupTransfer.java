package com.egg.manager.persistence.pojo.transfer.mysql.define;


import com.egg.manager.persistence.db.mysql.entity.define.DefineGroup;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineGroupVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@Named("DefineGroupTransfer")
public class DefineGroupTransfer extends MyBaseMysqlTransfer {

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
