package me.zhengjie.modules.goods.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author tianying
* @date 2020-01-31
*/
@Data
public class InsGoodsInfoDto implements Serializable {

    private String id;

    /** 保险公司ID */
    private String insuranceCompanyId;

    /** 商品类别ID */
    private String goodsCategoryId;

    /** 商品名称 */
    private String goodsName;

    /** 产品代码 */
    private String goodsNo;

    /** 销售价格 */
    private String sellPrice;

    /** 市场价格 */
    private String marketPrice;

    /** 成本价格 */
    private String costPrice;

    /** 打折 */
    private String discount;

    /** 上架时间 */
    private Timestamp upTime;

    /** 下架时间 */
    private Timestamp downTime;

    /** 浏览次数 */
    private String visitCount;

    /** 收藏次数 */
    private String favoriteCount;

    /** 评论次数 */
    private String commentCount;

    /** 评分总数 */
    private Integer grade;

    /** 销量 */
    private String sale;

    /** 积分 */
    private String score;

    /** 商品排序 */
    private Integer sort;

    private String img;

    private String adImg;

    /** 商品状态:00正常 10已删除 20下架 30申请上架 */
    private String goodsStatus;

    /** 栏目类别，用于区分页面显示比一比、看一看等栏目。 */
    private String columnType;

    /** 库存 */
    private Integer storeNums;

    /** 商品描述 */
    private String goodsDescript;

    /** 关键字 */
    private String keywords;

    /** 产品介绍 */
    private String description;

    private String searchWords;

    /** 创建人 */
    private String createAdmin;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更改时间 */
    private Timestamp updateTime;

    private String unit;

    private Integer brandId;

    private String specArray;

    private Integer exp;

    /** 共享商品 0不共享 1共享 */
    private String isShare;

    /** 0不推荐 1推荐 */
    private String isRecommend;

    private String label;

    /** 00-网信理财 10-销售销售 */
    private String sellChannel;

    /** 00-租赁 10-销售 */
    private String sellType;

    private String businessId;

    /**  最后修改人 */
    private String updateAdmin;

    /** 玩具类型 */
    private String toyType;

    /** 锻炼能力 */
    private String exerciseAbility;

    /** 适合年龄 */
    private String fitAge;

    /** 玩具尺寸 大、中、小 */
    private String size;

    private String goldMemberPrice;

    private String memberPrice;

    /** 押金 */
    private String deposit;

    /** 销售次数 */
    private Integer shellNums;

    /** 分类ID */
    private String classifiedId;

    /** 重量 */
    private String weight;

    /** 返佣比例 */
    private String rebate;

    /**  10-实物  20-电子票 */
    private String goodsType;
}