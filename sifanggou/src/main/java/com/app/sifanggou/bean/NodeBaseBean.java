package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/10/27.
 */

public class NodeBaseBean extends BaseBean {
    protected String parent_category_code;
    protected String category_code;
    protected String name;

    public String getParent_category_code() {
        return parent_category_code;
    }

    public void setParent_category_code(String parent_category_code) {
        this.parent_category_code = parent_category_code;
    }

    public String getCategory_code() {
        return category_code;
    }

    public void setCategory_code(String category_code) {
        this.category_code = category_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
