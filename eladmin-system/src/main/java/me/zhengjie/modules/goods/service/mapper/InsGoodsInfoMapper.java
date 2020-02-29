package me.zhengjie.modules.goods.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.modules.goods.domain.InsGoodsInfo;
import me.zhengjie.modules.goods.service.dto.InsGoodsInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author tianying
* @date 2020-01-31
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InsGoodsInfoMapper extends BaseMapper<InsGoodsInfoDto, InsGoodsInfo> {

}