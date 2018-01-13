package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2018/1/6.
 */

public class BusinessPartnerBean extends BaseBean {
    private String business_code;
    private String collect_time;
    private String partner_business_code;
    private BusinessInfoBean partner_business_info;

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

    public String getPartner_business_code() {
        return partner_business_code;
    }

    public void setPartner_business_code(String partner_business_code) {
        this.partner_business_code = partner_business_code;
    }

    public BusinessInfoBean getPartner_business_info() {
        return partner_business_info;
    }

    public void setPartner_business_info(BusinessInfoBean partner_business_info) {
        this.partner_business_info = partner_business_info;
    }
}
