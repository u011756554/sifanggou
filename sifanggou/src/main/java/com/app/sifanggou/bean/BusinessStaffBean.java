package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2018/1/8.
 */

public class BusinessStaffBean extends BaseBean {
    private String valid_end_date;
    private String role;
    private String gender;
    private String user_name;
    private String enable;
    private String name;
    private String business_code;
    private String valid_start_date;

    public String getValid_end_date() {
        return valid_end_date;
    }

    public void setValid_end_date(String valid_end_date) {
        this.valid_end_date = valid_end_date;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusiness_code() {
        return business_code;
    }

    public void setBusiness_code(String business_code) {
        this.business_code = business_code;
    }

    public String getValid_start_date() {
        return valid_start_date;
    }

    public void setValid_start_date(String valid_start_date) {
        this.valid_start_date = valid_start_date;
    }
}
