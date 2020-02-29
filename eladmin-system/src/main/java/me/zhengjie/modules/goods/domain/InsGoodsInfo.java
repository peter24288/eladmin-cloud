package me.zhengjie.modules.goods.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author tianying
* @date 2020-01-31
*/
@Entity
@Data
@Table(name="ins_goods_info")
public class InsGoodsInfo implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    /** 保险公司ID */
    @Column(name = "insurance_company_id")
    private String insuranceCompanyId;

    /** 商品类别ID */
    @Column(name = "goods_category_id")
    private String goodsCategoryId;

    /** 商品名称 */
    @Column(name = "goods_name")
    private String goodsName;

    /** 产品代码 */
    @Column(name = "goods_no")
    private String goodsNo;

    /** 销售价格 */
    @Column(name = "sell_price")
    private String sellPrice;

    /** 市场价格 */
    @Column(name = "market_price")
    private String marketPrice;

    /** 成本价格 */
    @Column(name = "cost_price")
    private String costPrice;

    /** 打折 */
    @Column(name = "discount")
    private String discount;

    /** 上架时间 */
    @Column(name = "up_time")
    private Timestamp upTime;

    /** 下架时间 */
    @Column(name = "down_time")
    private Timestamp downTime;

    /** 浏览次数 */
    @Column(name = "visit_count")
    private String visitCount;

    /** 收藏次数 */
    @Column(name = "favorite_count")
    private String favoriteCount;

    /** 评论次数 */
    @Column(name = "comment_count")
    private String commentCount;

    /** 评分总数 */
    @Column(name = "grade")
    private Integer grade;

    /** 销量 */
    @Column(name = "sale")
    private String sale;

    /** 积分 */
    @Column(name = "score")
    private String score;

    /** 商品排序 */
    @Column(name = "sort")
    private Integer sort;

    @Column(name = "img")
    private String img;

    @Column(name = "ad_img")
    private String adImg;

    /** 商品状态:00正常 10已删除 20下架 30申请上架 */
    @Column(name = "goods_status")
    private String goodsStatus;

    /** 栏目类别，用于区分页面显示比一比、看一看等栏目。 */
    @Column(name = "column_type")
    private String columnType;

    /** 库存 */
    @Column(name = "store_nums")
    private Integer storeNums;

    /** 商品描述 */
    @Column(name = "goods_descript")
    private String goodsDescript;

    /** 关键字 */
    @Column(name = "keywords")
    private String keywords;

    /** 产品介绍 */
    @Column(name = "description")
    private String description;

    @Column(name = "search_words")
    private String searchWords;

    /** 创建人 */
    @Column(name = "create_admin")
    private String createAdmin;

    /** 创建时间 */
    @Column(name = "create_time")
    private Timestamp createTime;

    /** 更改时间 */
    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "unit")
    private String unit;

    @Column(name = "brand_id")
    private Integer brandId;

    @Column(name = "spec_array")
    private String specArray;

    @Column(name = "exp")
    private Integer exp;

    /** 共享商品 0不共享 1共享 */
    @Column(name = "is_share")
    private String isShare;

    /** 0不推荐 1推荐 */
    @Column(name = "is_recommend")
    private String isRecommend;

    @Column(name = "label")
    private String label;

    /** 00-网信理财 10-销售销售 */
    @Column(name = "sell_channel")
    private String sellChannel;

    /** 00-租赁 10-销售 */
    @Column(name = "sell_type")
    private String sellType;

    @Column(name = "business_id")
    private String businessId;

    /**  最后修改人 */
    @Column(name = "update_admin")
    private String updateAdmin;

    /** 玩具类型 */
    @Column(name = "Toy_type")
    private String toyType;

    /** 锻炼能力 */
    @Column(name = "exercise_ability")
    private String exerciseAbility;

    /** 适合年龄 */
    @Column(name = "fit_age")
    private String fitAge;

    /** 玩具尺寸 大、中、小 */
    @Column(name = "size")
    private String size;

    @Column(name = "gold_member_price")
    private String goldMemberPrice;

    @Column(name = "member_price")
    private String memberPrice;

    /** 押金 */
    @Column(name = "deposit")
    private String deposit;

    /** 销售次数 */
    @Column(name = "shell_nums")
    private Integer shellNums;

    /** 分类ID */
    @Column(name = "classified_id")
    private String classifiedId;

    /** 重量 */
    @Column(name = "weight")
    private String weight;

    /** 返佣比例 */
    @Column(name = "rebate")
    private String rebate;

    /**  10-实物  20-电子票 */
    @Column(name = "goods_type")
    private String goodsType;

    public void copy(InsGoodsInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}