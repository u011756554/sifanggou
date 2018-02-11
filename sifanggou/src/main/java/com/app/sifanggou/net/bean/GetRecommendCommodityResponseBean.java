package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.RecommendCommodityBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/17.
 */

public class GetRecommendCommodityResponseBean extends BaseResponseBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private List<RecommendCommodityBean> recommend_commodity;

        public List<RecommendCommodityBean> getRecommend_commodity() {
            return recommend_commodity;
        }

        public void setRecommend_commodity(List<RecommendCommodityBean> recommend_commodity) {
            this.recommend_commodity = recommend_commodity;
        }
    }
}
