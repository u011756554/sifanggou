package com.app.sifanggou.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/6.
 */

public class FirstChildNodeTypeBean extends NodeBaseBean {
    private List<SecondChildNodeTypeBean> child_node_list;

    public List<SecondChildNodeTypeBean> getChild_node_list() {
        return child_node_list;
    }

    public void setChild_node_list(List<SecondChildNodeTypeBean> child_node_list) {
        this.child_node_list = child_node_list;
    }
}
