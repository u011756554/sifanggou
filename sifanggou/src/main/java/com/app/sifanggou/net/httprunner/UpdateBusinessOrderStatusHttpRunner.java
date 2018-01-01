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
 * Created by Administrator on 2017/12/24.
 */

public class UpdateBusinessOrderStatusHttpRunner extends HttpRunner {

    @Override
    public void onEventRun(Event event) throws Exception {
        String business_code = (String) event.getParamAtIndex(0);
        String user_name = (String) event.getParamAtIndex(1);
        String trans_no = (String) event.getParamAtIndex(2);
        String sign = (String) event.getParamAtIndex(3);
        String order_no = (String) event.getParamAtIndex(4);
        String sub_order_no = (String) event.getParamAtIndex(5);
        String order_status = (String) event.getParamAtIndex(6);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("business_code", business_code);
        map.put("user_name", user_name);
        map.put("trans_no", trans_no);
        map.put("sign", sign);
        map.put("order_no", order_no);
        map.put("sub_order_no", sub_order_no);
        map.put("order_status", order_status);

        String result = HttpUtils.doPost(URLUtils.UPDATEBUSINESSORDERSTATUS, map);

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
