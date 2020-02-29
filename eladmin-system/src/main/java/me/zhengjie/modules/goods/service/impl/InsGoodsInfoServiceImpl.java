package me.zhengjie.modules.goods.service.impl;

import me.zhengjie.modules.goods.domain.InsGoodsInfo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.modules.goods.repository.InsGoodsInfoRepository;
import me.zhengjie.modules.goods.service.InsGoodsInfoService;
import me.zhengjie.modules.goods.service.dto.InsGoodsInfoDto;
import me.zhengjie.modules.goods.service.dto.InsGoodsInfoQueryCriteria;
import me.zhengjie.modules.goods.service.mapper.InsGoodsInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.IdUtil;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @author tianying
* @date 2020-01-31
*/
@Service
//@CacheConfig(cacheNames = "insGoodsInfo")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class InsGoodsInfoServiceImpl implements InsGoodsInfoService {

    private final InsGoodsInfoRepository insGoodsInfoRepository;

    private final InsGoodsInfoMapper insGoodsInfoMapper;

    public InsGoodsInfoServiceImpl(InsGoodsInfoRepository insGoodsInfoRepository, InsGoodsInfoMapper insGoodsInfoMapper) {
        this.insGoodsInfoRepository = insGoodsInfoRepository;
        this.insGoodsInfoMapper = insGoodsInfoMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(InsGoodsInfoQueryCriteria criteria, Pageable pageable){
        Page<InsGoodsInfo> page = insGoodsInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(insGoodsInfoMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<InsGoodsInfoDto> queryAll(InsGoodsInfoQueryCriteria criteria){
        return insGoodsInfoMapper.toDto(insGoodsInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public InsGoodsInfoDto findById(String id) {
        InsGoodsInfo insGoodsInfo = insGoodsInfoRepository.findById(id).orElseGet(InsGoodsInfo::new);
        ValidationUtil.isNull(insGoodsInfo.getId(),"InsGoodsInfo","id",id);
        return insGoodsInfoMapper.toDto(insGoodsInfo);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public InsGoodsInfoDto create(InsGoodsInfo resources) {
        resources.setId(IdUtil.simpleUUID()); 
        return insGoodsInfoMapper.toDto(insGoodsInfoRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(InsGoodsInfo resources) {
        InsGoodsInfo insGoodsInfo = insGoodsInfoRepository.findById(resources.getId()).orElseGet(InsGoodsInfo::new);
        ValidationUtil.isNull( insGoodsInfo.getId(),"InsGoodsInfo","id",resources.getId());
        insGoodsInfo.copy(resources);
        insGoodsInfoRepository.save(insGoodsInfo);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            insGoodsInfoRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<InsGoodsInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (InsGoodsInfoDto insGoodsInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("保险公司ID", insGoodsInfo.getInsuranceCompanyId());
            map.put("商品类别ID", insGoodsInfo.getGoodsCategoryId());
            map.put("商品名称", insGoodsInfo.getGoodsName());
            map.put("产品代码", insGoodsInfo.getGoodsNo());
            map.put("销售价格", insGoodsInfo.getSellPrice());
            map.put("市场价格", insGoodsInfo.getMarketPrice());
            map.put("成本价格", insGoodsInfo.getCostPrice());
            map.put("打折", insGoodsInfo.getDiscount());
            map.put("上架时间", insGoodsInfo.getUpTime());
            map.put("下架时间", insGoodsInfo.getDownTime());
            map.put("浏览次数", insGoodsInfo.getVisitCount());
            map.put("收藏次数", insGoodsInfo.getFavoriteCount());
            map.put("评论次数", insGoodsInfo.getCommentCount());
            map.put("评分总数", insGoodsInfo.getGrade());
            map.put("销量", insGoodsInfo.getSale());
            map.put("积分", insGoodsInfo.getScore());
            map.put("商品排序", insGoodsInfo.getSort());
            map.put(" img",  insGoodsInfo.getImg());
            map.put(" adImg",  insGoodsInfo.getAdImg());
            map.put("商品状态:00正常 10已删除 20下架 30申请上架", insGoodsInfo.getGoodsStatus());
            map.put("栏目类别，用于区分页面显示比一比、看一看等栏目。", insGoodsInfo.getColumnType());
            map.put("库存", insGoodsInfo.getStoreNums());
            map.put("商品描述", insGoodsInfo.getGoodsDescript());
            map.put("关键字", insGoodsInfo.getKeywords());
            map.put("产品介绍", insGoodsInfo.getDescription());
            map.put(" searchWords",  insGoodsInfo.getSearchWords());
            map.put("创建人", insGoodsInfo.getCreateAdmin());
            map.put("创建时间", insGoodsInfo.getCreateTime());
            map.put("更改时间", insGoodsInfo.getUpdateTime());
            map.put(" unit",  insGoodsInfo.getUnit());
            map.put(" brandId",  insGoodsInfo.getBrandId());
            map.put(" specArray",  insGoodsInfo.getSpecArray());
            map.put(" exp",  insGoodsInfo.getExp());
            map.put("共享商品 0不共享 1共享", insGoodsInfo.getIsShare());
            map.put("0不推荐 1推荐", insGoodsInfo.getIsRecommend());
            map.put(" label",  insGoodsInfo.getLabel());
            map.put("00-网信理财 10-销售销售", insGoodsInfo.getSellChannel());
            map.put("00-租赁 10-销售", insGoodsInfo.getSellType());
            map.put(" businessId",  insGoodsInfo.getBusinessId());
            map.put(" 最后修改人", insGoodsInfo.getUpdateAdmin());
            map.put("玩具类型", insGoodsInfo.getToyType());
            map.put("锻炼能力", insGoodsInfo.getExerciseAbility());
            map.put("适合年龄", insGoodsInfo.getFitAge());
            map.put("玩具尺寸 大、中、小", insGoodsInfo.getSize());
            map.put(" goldMemberPrice",  insGoodsInfo.getGoldMemberPrice());
            map.put(" memberPrice",  insGoodsInfo.getMemberPrice());
            map.put("押金", insGoodsInfo.getDeposit());
            map.put("销售次数", insGoodsInfo.getShellNums());
            map.put("分类ID", insGoodsInfo.getClassifiedId());
            map.put("重量", insGoodsInfo.getWeight());
            map.put("返佣比例", insGoodsInfo.getRebate());
            map.put(" 10-实物  20-电子票", insGoodsInfo.getGoodsType());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}