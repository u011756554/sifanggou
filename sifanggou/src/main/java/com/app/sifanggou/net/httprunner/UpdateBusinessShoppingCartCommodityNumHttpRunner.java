package com.app.sifanggou.net.httprunner;

import android.text.TextUtils;
import android.util.Log;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.HttpUtils;
import com.app.sifanggou.net.URLUtils;
import com.app.sifanggou.net.bean.BaseResponseBean;
import com.app.sifanggou.net.bean.SerachBusinessOnNameResponseBean;
import com.app.sifanggou.utils.CommonUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/19.
 */

public class UpdateBusinessShoppingCartCommodityNumHttpRunner extends HttpRunner {
    @Override
    public void onEventRun(Event event) throws Exception {
        String business_code = (String) event.getParamAtIndex(0);
        String commodity_id = (String) event.getParamAtIndex(1);
        String commodity_num = (String) event.getParamAtIndex(2);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("business_code", business_code);
        map.put("commodity_id", commodity_id);
        map.put("commodity_num", commodity_num);

        String result = HttpUtils.doPost(URLUtils.UPDATEBUSINESSSHOPPINGCARTCOMMODITYNUM, map);

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
