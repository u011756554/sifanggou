package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2017/9/17 0017.
 */

public class LoginUserDataBean extends BaseBean {

    private String valid_end_date;
    private String role;
    private String gender;
    private String name;
    private String registration_id;
    private String device_type;
    private String valid_start_date;

    private BusinessInfoBean business_info;

    public BusinessInfoBean getBusiness_info() {
        return business_info;
    }

    public void setBusiness_info(BusinessInfoBean business_info) {
        this.business_info = business_info;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(String registration_id) {
        this.registration_id = registration_id;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getValid_start_date() {
        return valid_start_date;
    }

    public void setValid_start_date(String valid_start_date) {
        this.valid_start_date = valid_start_date;
    }
}
