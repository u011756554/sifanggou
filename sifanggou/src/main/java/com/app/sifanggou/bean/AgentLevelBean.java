package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/9/14 0014.
 */

public class AgentLevelBean extends BaseBean {
    private String level_name;
    private String level_num;
    private String need_agent_contact;

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public String getLevel_num() {
        return level_num;
    }

    public void setLevel_num(String level_num) {
        this.level_num = level_num;
    }

    public String getNeed_agent_contact() {
        return need_agent_contact;
    }

    public void setNeed_agent_contact(String need_agent_contact) {
        this.need_agent_contact = need_agent_contact;
    }
}
