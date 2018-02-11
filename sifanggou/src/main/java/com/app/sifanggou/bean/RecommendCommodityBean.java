package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2018/1/17.
 */

public class RecommendCommodityBean extends BaseBean {
    private CommodityInfoBean commodity_info;
    private String commodity_id;
    private String recommend_time;
    private BusinessInfoBean business_info;

    public CommodityInfoBean getCommodity_info() {
        return commodity_info;
    }

    public void setCommodity_info(CommodityInfoBean commodity_info) {
        this.commodity_info = commodity_info;
    }

    public String getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(String commodity_id) {
        this.commodity_id = commodity_id;
    }

    public String getRecommend_time() {
        return recommend_time;
    }

    public void setRecommend_time(String recommend_time) {
        this.recommend_time = recommend_time;
    }

    public BusinessInfoBean getBusiness_info() {
        return business_info;
    }

    public void setBusiness_info(BusinessInfoBean business_info) {
        this.business_info = business_info;
    }
}
