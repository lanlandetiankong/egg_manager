package com.egg.manager.persistence.pojo.mysql.transfer.define;


import com.egg.manager.persistence.db.mysql.entity.define.DefineGroup;
import com.egg.manager.persistence.pojo.mysql.mapstruct.define.DefineGroupMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineGroupVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@Named("defineGroupTransfer")
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
