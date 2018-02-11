package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/9/14 0014.
 */

public enum AgentLevelType {
    FACTORY_DIRECT(1,"factory_direct","厂家直营"),NATIONAL_GENERAL_AGENT(2,"national_general_agent","全国总代理"),PROVINCE_GENERAL_AGENT(3,"province_general_agent","省级总代理"),CITY_GENERAL_AGENT(4,"city_general_agent","市级总代理"),
    ZONE_GENERAL_AGENT(5,"zone_general_agent","区域代理"),OTHER(0,"other","其他"),SINGLE_PRODUCT_LARGE_USER (6,"single_product_large_user","单品大户"),ORIGIN_DIRECT_SALE(7,"origin_direct_sale"," 产地直销"),
    INTEGRATED_HANDLING(8,"integrated_handling","综合加工"),INTEGRATED_DISTRIBUTION(9,"integrated_distribution","综合配送");

    private String type;
    private int code;
    private String name;
    AgentLevelType(int code,String type,String name) {
        this.code = code;
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public String getCode() {
        return this.code + "";
    }

    public String getName() {
        return this.name;
    }
}
