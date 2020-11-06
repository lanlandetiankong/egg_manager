package com.egg.manager.persistence.em.define.pojo.transfer;


import com.egg.manager.persistence.em.define.db.mysql.entity.DefineGroup;
import com.egg.manager.persistence.em.define.pojo.mapstruct.imap.DefineGroupMapstruct;
import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineGroupVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Component
@Named("defineGroupTransfer")
public class DefineGroupTransfer extends BaseMysqlTransfer {

    static DefineGroupMapstruct defineGroupMapstruct = DefineGroupMapstruct.INSTANCE;

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static DefineGroupVo transferEntityToVo(DefineGroup entity) {
        if (entity == null) {
            return null;
        }
        DefineGroupVo vo = defineGroupMapstruct.transferEntityToVo(entity);
        return vo;
    }


}
