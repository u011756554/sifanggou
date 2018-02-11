package com.app.sifanggou.net.httprunner;

import android.text.TextUtils;
import android.util.Log;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.HttpUtils;
import com.app.sifanggou.net.URLUtils;
import com.app.sifanggou.net.bean.BaseResponseBean;
import com.app.sifanggou.utils.CommonUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/1/17.
 */

public class UpdateBusinessInfoHttpRunner extends HttpRunner {

    @Override
    public void onEventRun(Event event) throws Exception {
        String business_code = (String) event.getParamAtIndex(0);
        String name = (String) event.getParamAtIndex(1);
        String province = (String) event.getParamAtIndex(2);
        String province_name = (String) event.getParamAtIndex(3);
        String city = (String) event.getParamAtIndex(4);
        String city_name = (String) event.getParamAtIndex(5);
        String zone = (String) event.getParamAtIndex(6);
        String market_code = (String) event.getParamAtIndex(7);
        String zone_name = (String) event.getParamAtIndex(8);
        String head_pic_url = (String) event.getParamAtIndex(9);
        String shop_number = (String) event.getParamAtIndex(10);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("business_code", business_code);

        if(!TextUtils.isEmpty(name)) {
            map.put("name", name);
        }

        if(!TextUtils.isEmpty(province)) {
            map.put("province", province);
        }

        if(!TextUtils.isEmpty(province_name)) {
            map.put("province_name", province_name);
        }

        if(!TextUtils.isEmpty(city)) {
            map.put("city", city);
        }

        if(!TextUtils.isEmpty(city_name)) {
            map.put("city_name", city_name);
        }

        if(!TextUtils.isEmpty(zone)) {
            map.put("zone", zone);
        }

        if(!TextUtils.isEmpty(market_code)) {
            map.put("market_code", market_code);
        }
        if(!TextUtils.isEmpty(zone_name)) {
            map.put("zone_name", zone_name);
        }
        if(!TextUtils.isEmpty(head_pic_url)) {
            map.put("head_pic_url", head_pic_url);
        }

        if(!TextUtils.isEmpty(shop_number)) {
            map.put("shop_number", shop_number);
        }

        String result = HttpUtils.doPost(URLUtils.UPDATEBUSINESSINFO, map);

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
            event.setSuccess(false);
            if (!TextUtils.isEmpty(param.getMessage())) {
                event.setFailException(new Exception(param.getMessage()));
            }
        }
    }
}
