package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.ConcernedBean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/12.
 */

public class GetBusinessConcernedInfoResponseBean extends BaseResponseBean {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private String total_concerned_num;
        private String today_concerned_num;
        private List<ConcernedBean> concerned_list;

        public String getTotal_concerned_num() {
            return total_concerned_num;
        }

        public void setTotal_concerned_num(String total_concerned_num) {
            this.total_concerned_num = total_concerned_num;
        }

        public String getToday_concerned_num() {
            return today_concerned_num;
        }

        public void setToday_concerned_num(String today_concerned_num) {
            this.today_concerned_num = today_concerned_num;
        }

        public List<ConcernedBean> getConcerned_list() {
            return concerned_list;
        }

        public void setConcerned_list(List<ConcernedBean> concerned_list) {
            this.concerned_list = concerned_list;
        }
    }
}
