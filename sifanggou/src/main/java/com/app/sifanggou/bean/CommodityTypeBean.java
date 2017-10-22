package com.app.sifanggou.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/6.
 */

public class CommodityTypeBean extends BaseBean {
    private String parent_category_code;
    private String category_code;
    private String name;
    private List<FirstChildNodeTypeBean> child_node_list;

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

    public List<FirstChildNodeTypeBean> getChild_node_list() {
        return child_node_list;
    }

    public void setChild_node_list(List<FirstChildNodeTypeBean> child_node_list) {
        this.child_node_list = child_node_list;
    }
}
