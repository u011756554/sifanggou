package com.app.sifanggou.net.httprunner;

import android.text.TextUtils;
import android.util.Log;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.HttpUtils;
import com.app.sifanggou.net.URLUtils;
import com.app.sifanggou.net.bean.BaseResponseBean;
import com.app.sifanggou.utils.CommonUtils;

import org.apache.http.protocol.HTTP;

import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/9/14 0014.
 */

public class BusinessRegistHttpRunner extends HttpRunner {
    @Override
    public void onEventRun(Event event) throws Exception {
        // TODO Auto-generated method stub
        // name,province,province_name,city,city_name,lon,lat,market_code,head_pic_url,business_license,legal_person_id,agent_level,
        //highest_agency_contract_pic_url,invite_code,mobile,verify_code,password,registration_id,device_type
        String name = (String) event.getParamAtIndex(0);
        String province = (String) event.getParamAtIndex(1);
        String province_name = (String) event.getParamAtIndex(2);
        String city = (String) event.getParamAtIndex(3);
        String city_name = (String) event.getParamAtIndex(4);
        String lon = (String) event.getParamAtIndex(5);
        String lat = (String) event.getParamAtIndex(6);
        String market_code = (String) event.getParamAtIndex(7);
        String head_pic_url = (String) event.getParamAtIndex(8);
        String business_license = (String) event.getParamAtIndex(9);
        String legal_person_id = (String) event.getParamAtIndex(10);
        String agent_level = (String) event.getParamAtIndex(11);
        String highest_agency_contract_pic_url = (String) event.getParamAtIndex(12);
        String invite_code = (String) event.getParamAtIndex(13);
        String mobile = (String) event.getParamAtIndex(14);
        String verify_code = (String) event.getParamAtIndex(15);
        String password = (String) event.getParamAtIndex(16);
        String registration_id = (String) event.getParamAtIndex(17);
        String device_type = (String) event.getParamAtIndex(18);
        String integrate_distribute_type = (String) event.getParamAtIndex(19);
        String shop_number = (String) event.getParamAtIndex(20);
        String scope = (String) event.getParamAtIndex(21);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", name);
        map.put("province", province);
        map.put("province_name", province_name);
        map.put("city", city);
        map.put("city_name", city_name);
        map.put("lon", lon);
        map.put("lat", lat);
        map.put("market_code", market_code);
        map.put("head_pic_url", head_pic_url);
        map.put("business_license", business_license);
        map.put("legal_person_id", legal_person_id);
        map.put("agent_level", agent_level);
        map.put("highest_agency_contract_pic_url", highest_agency_contract_pic_url);
        map.put("invite_code", invite_code);
        map.put("mobile", mobile);
        map.put("verify_code", verify_code);
        map.put("password", password);
        map.put("registration_id", registration_id);
        map.put("device_type", device_type);
        map.put("integrate_distribute_type",integrate_distribute_type);
        map.put("shop_number",shop_number);
        map.put("scope",scope);

        String result = HttpUtils.doPost(URLUtils.BUSINESSREGIST, map);

        Log.i(AppContext.LOG_NET, result);
        if (CommonUtils.isEmpty(result)) {
            event.setSuccess(false);
            event.setFailException(new Exception("网络错误"));
            return;
        }

        BaseResponseBean param = gson.fromJson(result, BaseResponseBean.class);
        if (param == null) {
            event.setSuccess(false);
            return;
        }
        if (checkParams(event, param)) {
            event.setSuccess(true);
            event.addReturnParam(param);
        } else {
            if (!TextUtils.isEmpty(param.getMessage())) {
                event.setFailException(new Exception(param.getMessage()));
            }
            event.setSuccess(false);
        }
    }
}
