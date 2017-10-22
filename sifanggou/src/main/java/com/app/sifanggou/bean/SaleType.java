package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/10/7.
 */

public enum SaleType {
    ON_SALE_COMMON("on_sale_common"),ON_SALE_AGENCY("on_sale_agency"),OFF_SALE_COMMON("off_sale_common"),OFF_SALE_AGENCY("off_sale_agency");

    private String type;
    SaleType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
