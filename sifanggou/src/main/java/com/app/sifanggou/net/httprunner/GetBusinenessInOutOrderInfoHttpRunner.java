package com.app.sifanggou.net.httprunner;

import android.text.TextUtils;
import android.util.Log;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.HttpUtils;
import com.app.sifanggou.net.URLUtils;
import com.app.sifanggou.net.bean.BaseResponseBean;
import com.app.sifanggou.net.bean.GetBusinenessInOutOrderInfoResponseBean;
import com.app.sifanggou.utils.CommonUtils;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Administrator on 2017/12/6.
 */

public class GetBusinenessInOutOrderInfoHttpRunner extends HttpRunner {

    @Override
    public void onEventRun(Event event) throws Exception {
        String business_code = (String) event.getParamAtIndex(0);
        String type = (String) event.getParamAtIndex(1);
        String item_num = (String) event.getParamAtIndex(2);
        String page_no = (String) event.getParamAtIndex(3);
        String tag = (String) event.getParamAtIndex(4);
        String year = (String) event.getParamAtIndex(5);
        String month = (String) event.getParamAtIndex(6);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("business_code", business_code);
        map.put("type", type);
        map.put("item_num", item_num);
        map.put("page_no", page_no);
        if (!TextUtils.isEmpty(year) && !TextUtils.isEmpty(month)) {
            map.put("year", Integer.valueOf(year));
            map.put("month", Integer.valueOf(month));
        }

        String result = HttpUtils.doPostObject(URLUtils.GETBUSINENESSINOUTORDERINFO, map);

        Log.i(AppContext.LOG_NET, result);
        if (CommonUtils.isEmpty(result)) {
            event.setSuccess(false);
            event.setFailException(new Exception("网络错误"));
            return;
        }

        GetBusinenessInOutOrderInfoResponseBean param = gson.fromJson(result, GetBusinenessInOutOrderInfoResponseBean.class);
        if (param == null) {
            event.setSuccess(false);
            return;
        }
        if (checkParams(event, param)) {
            event.setSuccess(true);
            event.addReturnParam(param);
            event.addReturnParam(tag);
        } else {
            event.setSuccess(false);
            if (!TextUtils.isEmpty(param.getMessage())) {
                event.setFailException(new Exception(param.getMessage()));
            }
        }
    }
}
