package com.app.sifanggou.net.httprunner;

import android.text.TextUtils;
import android.util.Log;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.HttpUtils;
import com.app.sifanggou.net.URLUtils;
import com.app.sifanggou.net.bean.BaseResponseBean;
import com.app.sifanggou.net.bean.GetBusinessPartnerResponseBean;
import com.app.sifanggou.utils.CommonUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/1/6.
 */

public class GetBusinessPartnerHttpRunner extends HttpRunner {

    @Override
    public void onEventRun(Event event) throws Exception {
        String business_code = (String) event.getParamAtIndex(0);
        String item_num = (String) event.getParamAtIndex(1);
        String page_no = (String) event.getParamAtIndex(2);
        String tag = (String) event.getParamAtIndex(3);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("business_code", business_code);
        map.put("item_num", item_num);
        map.put("page_no", page_no);

        String result = HttpUtils.doPost(URLUtils.GETBUSINESSPARTNER, map);

        Log.i(AppContext.LOG_NET, result);
        if (CommonUtils.isEmpty(result)) {
            event.setSuccess(false);
            event.setFailException(new Exception("网络错误"));
            return;
        }

        GetBusinessPartnerResponseBean param = gson.fromJson(result, GetBusinessPartnerResponseBean.class);
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
