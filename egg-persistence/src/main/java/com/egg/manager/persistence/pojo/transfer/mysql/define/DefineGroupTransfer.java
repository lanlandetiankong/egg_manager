package com.egg.manager.persistence.pojo.transfer.mysql.define;


import com.egg.manager.persistence.db.mysql.entity.define.DefineGroup;
import com.egg.manager.persistence.pojo.mapstruct.mysql.define.DefineGroupMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineGroupVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@Named("DefineGroupTransfer")
public class DefineGroupTransfer extends MyBaseMysqlTransfer {

    static DefineGroupMapstruct defineGroupVoMapstruct = DefineGroupMapstruct.INSTANCE ;

    public static DefineGroupVo transferEntityToVo(DefineGroup entity) {
        if (entity == null) {
            return null;
        }
        DefineGroupVo vo = defineGroupVoMapstruct.transferEntityToVo(entity);
        return vo;
    }


}
