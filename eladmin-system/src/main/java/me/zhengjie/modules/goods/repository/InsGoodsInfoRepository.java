package me.zhengjie.modules.goods.repository;

import me.zhengjie.modules.goods.domain.InsGoodsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author tianying
* @date 2020-01-31
*/
public interface InsGoodsInfoRepository extends JpaRepository<InsGoodsInfo, String>, JpaSpecificationExecutor<InsGoodsInfo> {
}