package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2018/1/14.
 */

public class AllBusinessUrgentSellCommodityBean extends BaseBean {

    private String valid_deadline;
    private String commodity_id;
    private String sell_price;
    private String sale_num;
    private String num;
    private String business_code;
    private String commodity_pic_url;
    private String collection_num;
    private String type;
    private String add_time;
    private String commodity_name;
    private String original_price;
    private BusinessInfoBean business_info;
    private int selectCount;
    private boolean isSelect = false;

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public BusinessInfoBean getBusiness_info() {
        return business_info;
    }

    public void setBusiness_info(BusinessInfoBean business_info) {
        this.business_info = business_info;
    }

    public int getSelectCount() {
        return selectCount;
    }

    public void setSelectCount(int selectCount) {
        this.selectCount = selectCount;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getValid_deadline() {
        return valid_deadline;
    }

    public void setValid_deadline(String valid_deadline) {
        this.valid_deadline = valid_deadline;
    }

    public String getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(String commodity_id) {
        this.commodity_id = commodity_id;
    }

    public String getSell_price() {
        return sell_price;
    }

    public void setSell_price(String sell_price) {
        this.sell_price = sell_price;
    }

    public String getSale_num() {
        return sale_num;
    }

    public void setSale_num(String sale_num) {
        this.sale_num = sale_num;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getBusiness_code() {
        return business_code;
    }

    public void setBusiness_code(String business_code) {
        this.business_code = business_code;
    }

    public String getCommodity_pic_url() {
        return commodity_pic_url;
    }

    public void setCommodity_pic_url(String commodity_pic_url) {
        this.commodity_pic_url = commodity_pic_url;
    }

    public String getCollection_num() {
        return collection_num;
    }

    public void setCollection_num(String collection_num) {
        this.collection_num = collection_num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getCommodity_name() {
        return commodity_name;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
    }
}
