package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2018/1/6.
 */

public class CommodityCollectBean extends BaseBean {
    private CommodityInfoBean commodity_info;
    private String commodity_id;
    private String business_code;
    private String collect_time;
    private BusinessInfoBean business_info;
    public CommodityInfoBean getCommodity_info() {
        return commodity_info;
    }

    public BusinessInfoBean getBusiness_info() {
        return business_info;
    }

    public void setBusiness_info(BusinessInfoBean business_info) {
        this.business_info = business_info;
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

    public String getBusiness_code() {
        return business_code;
    }

    public void setBusiness_code(String business_code) {
        this.business_code = business_code;
    }

    public String getCollect_time() {
        return collect_time;
    }

    public void setCollect_time(String collect_time) {
        this.collect_time = collect_time;
    }
}
