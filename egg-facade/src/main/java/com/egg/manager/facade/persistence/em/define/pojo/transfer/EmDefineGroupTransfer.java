package com.egg.manager.facade.persistence.em.define.pojo.transfer;


import com.egg.manager.facade.persistence.em.define.db.mysql.entity.EmDefineGroupEntity;
import com.egg.manager.facade.persistence.em.define.pojo.mapstruct.imap.EmDefineGroupMapstruct;
import com.egg.manager.facade.persistence.em.define.pojo.vo.EmDefineGroupVo;
import com.egg.manager.facade.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Component
@Named("defineGroupTransfer")
public class EmDefineGroupTransfer extends BaseMysqlTransfer {

    static EmDefineGroupMapstruct emDefineGroupMapstruct = EmDefineGroupMapstruct.INSTANCE;

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static EmDefineGroupVo transferEntityToVo(EmDefineGroupEntity entity) {
        if (entity == null) {
            return null;
        }
        EmDefineGroupVo vo = emDefineGroupMapstruct.transferEntityToVo(entity);
        return vo;
    }


}
