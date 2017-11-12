package com.app.sifanggou.view.tree;

import com.app.sifanggou.bean.CommodityTypeBean;
import com.app.sifanggou.bean.FirstChildNodeTypeBean;
import com.app.sifanggou.bean.NodeBaseBean;
import com.app.sifanggou.bean.SecondChildNodeTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 */

public class TreeUtils {

    public static List<NodeResource> commodityTypeBeanToNodeResource(List<CommodityTypeBean> dataList) {
        List<NodeResource> resultList = new ArrayList<NodeResource>();
        for(CommodityTypeBean bean : dataList) {
            NodeResource node = new NodeResource();
            node.setValue(bean.getName());
            node.setCurId(bean.getCategory_code());
            node.setParentId(bean.getParent_category_code());
            resultList.add(node);
            if (bean.getChild_node_list() != null && bean.getChild_node_list().size() > 0) {
                resultList.addAll(firstChildNodeTypeBeanToNodeResource(bean.getChild_node_list()));
            }
        }
        return resultList;
    }

    public static List<NodeResource> firstChildNodeTypeBeanToNodeResource(List<FirstChildNodeTypeBean> dataList) {
        List<NodeResource> resultList = new ArrayList<NodeResource>();
        for(FirstChildNodeTypeBean bean : dataList) {
            NodeResource node = new NodeResource();
            node.setValue(bean.getName());
            node.setCurId(bean.getCategory_code());
            node.setParentId(bean.getParent_category_code());
            resultList.add(node);
            if (bean.getChild_node_list() != null && bean.getChild_node_list().size() > 0) {
                resultList.addAll(secondChildNodeTypeBeanToNodeResource(bean.getChild_node_list()));
            }
        }
        return resultList;
    }

    public static List<NodeResource> secondChildNodeTypeBeanToNodeResource(List<SecondChildNodeTypeBean> dataList) {
        List<NodeResource> resultList = new ArrayList<NodeResource>();
        for(SecondChildNodeTypeBean bean : dataList) {
            NodeResource node = new NodeResource();
            node.setValue(bean.getName());
            node.setCurId(bean.getCategory_code());
            node.setParentId(bean.getParent_category_code());
            resultList.add(node);
        }
        return resultList;
    }
}
