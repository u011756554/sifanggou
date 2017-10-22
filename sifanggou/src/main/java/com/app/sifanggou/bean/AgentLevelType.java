package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/9/14 0014.
 */

public enum AgentLevelType {
    FACTORY_DIRECT(1,"factory_direct"),NATIONAL_GENERAL_AGENT(2,"national_general_agent"),PROVINCE_GENERAL_AGENT(3,"province_general_agent"),CITY_GENERAL_AGENT(4,"city_general_agent"),
    ZONE_GENERAL_AGENT(5,"zone_general_agent"),OTHER(0,"other"),SINGLE_PRODUCT_LARGE_USER (6,"single_product_large_user"),ORIGIN_DIRECT_SALE(7,"origin_direct_sale"),
    INTEGRATED_HANDLING(8,"integrated_handling"),INTEGRATED_DISTRIBUTION(9,"integrated_distribution");

    private String type;
    private int code;
    AgentLevelType(int code,String type) {
        this.code = code;
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public String getCode() {
        return this.code + "";
    }
}
