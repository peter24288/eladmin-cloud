package me.zhengjie.modules.goods.service;

import me.zhengjie.modules.goods.domain.InsGoodsInfo;
import me.zhengjie.modules.goods.service.dto.InsGoodsInfoDto;
import me.zhengjie.modules.goods.service.dto.InsGoodsInfoQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author tianying
* @date 2020-01-31
*/
public interface InsGoodsInfoService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(InsGoodsInfoQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<InsGoodsInfoDto>
    */
    List<InsGoodsInfoDto> queryAll(InsGoodsInfoQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return InsGoodsInfoDto
     */
    InsGoodsInfoDto findById(String id);

    /**
    * 创建
    * @param resources /
    * @return InsGoodsInfoDto
    */
    InsGoodsInfoDto create(InsGoodsInfo resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(InsGoodsInfo resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(String[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<InsGoodsInfoDto> all, HttpServletResponse response) throws IOException;
}