package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/11/22.
 */

public class SearchTypeBean extends BaseBean {

    private BusinessInfoBean businessInfoBean;

    private CommodityInfoBean commodityInfoBean;
    public BusinessInfoBean getBusinessInfoBean() {
        return businessInfoBean;
    }

    public void setBusinessInfoBean(BusinessInfoBean businessInfoBean) {
        this.businessInfoBean = businessInfoBean;
    }

    public CommodityInfoBean getCommodityInfoBean() {
        return commodityInfoBean;
    }

    public void setCommodityInfoBean(CommodityInfoBean commodityInfoBean) {
        this.commodityInfoBean = commodityInfoBean;
    }
}
