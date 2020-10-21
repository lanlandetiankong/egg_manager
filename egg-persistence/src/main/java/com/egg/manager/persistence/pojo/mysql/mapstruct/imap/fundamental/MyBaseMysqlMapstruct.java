package com.egg.manager.persistence.pojo.mysql.mapstruct.imap.fundamental;

import com.egg.manager.persistence.pojo.mysql.dto.MyBaseMysqlDto;
import com.egg.manager.persistence.pojo.mysql.vo.MyBaseMysqlVo;
import org.mapstruct.MapperConfig;

/**
 * @author zhoucj
 * @description: 可在该接口写公用的转化方法, 定义的方法请勿修改方法名、参数、返回值等！
 * @date 2020/10/20
 */
@MapperConfig(disableSubMappingMethodsGeneration = true)
public interface MyBaseMysqlMapstruct<E, V extends MyBaseMysqlVo, D extends MyBaseMysqlDto>
        extends MyBaseMysqlCommonFuncMapstruct<E, V, D>, MyBaseMysqlConstantMapstruct<E, V, D>, MyBaseMysqlEnumMapstruct<E, V, D> {


}
