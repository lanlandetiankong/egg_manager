package com.egg.manager.persistence.pojo.mapstruct.mysql;

import com.egg.manager.persistence.pojo.dto.mysql.MyBaseMysqlDto;
import com.egg.manager.persistence.pojo.vo.mysql.MyBaseMysqlVo;
import org.mapstruct.MapperConfig;
/**
 * 可在该接口写公用的转化方法,定义的方法请勿修改方法名、参数、返回值等！
 *
 *
 * Notes:
 * @Context : 该注解用于添加额外参数，不会对其进行判断null等操作
 */
@MapperConfig(disableSubMappingMethodsGeneration=true)
public interface MyBaseMysqlMapstruct<E,V extends MyBaseMysqlVo,D extends MyBaseMysqlDto>
        extends MyBaseMysqlCommonFuncMapstruct<E,V,D>,MyBaseMysqlConstantMapstruct<E,V,D>, MyBaseMysqlEnumMapstruct<E,V,D>{



}
