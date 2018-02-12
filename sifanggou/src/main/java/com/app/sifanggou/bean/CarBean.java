package com.app.sifanggou.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/24.
 */

public class CarBean extends BaseBean {

    private String business_name;
    private BusinessInfoBean business_info;
    private String total_amount;
    private String total_num;
    private List<CarItemBean> commodity_info_list;
    private String business_code;
    private boolean isSelect = true;
    private boolean isShow = true;

    public BusinessInfoBean getBusiness_info() {
        return business_info;
    }

    public void setBusiness_info(BusinessInfoBean business_info) {
        this.business_info = business_info;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getTotal_num() {
        return total_num;
    }

    public void setTotal_num(String total_num) {
        this.total_num = total_num;
    }

    public List<CarItemBean> getCommodity_info_list() {
        return commodity_info_list;
    }

    public void setCommodity_info_list(List<CarItemBean> commodity_info_list) {
        this.commodity_info_list = commodity_info_list;
    }

    public String getBusiness_code() {
        return business_code;
    }

    public void setBusiness_code(String business_code) {
        this.business_code = business_code;
    }
}
