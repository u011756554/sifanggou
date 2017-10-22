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
 * Created by Administrator on 2017/10/6.
 */

public class AddBusinessCommodityHttpRunner extends HttpRunner {
    @Override
    public void onEventRun(Event event) throws Exception {
        {
            //business_code,mobile,trans_no,sign,commodity_name,type,brand_name,first_level_category_code,second_level_category_code,third_level_category_code,
            //commodity_pic_url,quality_grade,production_place,specification,intro,a_price,b_price,agency_contract_pic_url,agent_level
            String business_code = (String) event.getParamAtIndex(0);
            String mobile = (String) event.getParamAtIndex(1);
            String trans_no = (String) event.getParamAtIndex(2);
            String sign = (String) event.getParamAtIndex(3);
            String commodity_name = (String) event.getParamAtIndex(4);
            String type = (String) event.getParamAtIndex(5);
            String brand_name = (String) event.getParamAtIndex(6);
            String first_level_category_code = (String) event.getParamAtIndex(7);
            String second_level_category_code = (String) event.getParamAtIndex(8);
            String third_level_category_code = (String) event.getParamAtIndex(9);
            String commodity_pic_url = (String) event.getParamAtIndex(10);
            String quality_grade = (String) event.getParamAtIndex(11);
            String production_place = (String) event.getParamAtIndex(12);
            String specification = (String) event.getParamAtIndex(13);
            String intro = (String) event.getParamAtIndex(14);
            String a_price = (String) event.getParamAtIndex(15);
            String b_price = (String) event.getParamAtIndex(16);
            String agency_contract_pic_url = (String) event.getParamAtIndex(17);
            String agent_level = (String) event.getParamAtIndex(18);

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("business_code", business_code);
            map.put("mobile", mobile);
            map.put("trans_no", trans_no);
            map.put("sign", sign);
            map.put("commodity_name", commodity_name);
            map.put("type", type);
            map.put("brand_name", brand_name);
            map.put("first_level_category_code", first_level_category_code);
            map.put("second_level_category_code", second_level_category_code);
            map.put("third_level_category_code", third_level_category_code);
            map.put("commodity_pic_url", commodity_pic_url);
            map.put("quality_grade", quality_grade);
            map.put("production_place", production_place);
            map.put("specification", specification);
            map.put("intro", intro);
            map.put("a_price", a_price);
            map.put("b_price", b_price);
            if(!TextUtils.isEmpty(agency_contract_pic_url)) {
                map.put("agency_contract_pic_url", agency_contract_pic_url);
            }
            if(!TextUtils.isEmpty(agent_level)) {
                map.put("agent_level", agent_level);
            }
            String result = HttpUtils.doPost(URLUtils.ADDBUSINESSCOMMODITY, map);

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
            }
        }
    }
}
