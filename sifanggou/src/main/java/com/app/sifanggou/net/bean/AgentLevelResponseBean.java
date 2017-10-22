package com.app.sifanggou.net.bean;

import com.app.sifanggou.bean.AgentLevelBean;
import com.app.sifanggou.bean.CityDataBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/14 0014.
 */

public class AgentLevelResponseBean extends BaseResponseBean {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean {
        private List<AgentLevelBean> agent_level_list;

        public List<AgentLevelBean> getAgent_level_list() {
            return agent_level_list;
        }

        public void setAgent_level_list(List<AgentLevelBean> agent_level_list) {
            this.agent_level_list = agent_level_list;
        }
    }
}
