package com.app.sifanggou.net.httprunner;

import android.text.TextUtils;
import android.util.Log;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.HttpUtils;
import com.app.sifanggou.net.URLUtils;
import com.app.sifanggou.net.bean.BaseResponseBean;
import com.app.sifanggou.net.bean.GetBusinessCommodityInfoResponseBean;
import com.app.sifanggou.net.bean.SearchBusinessCommodityOnNameResponseBean;
import com.app.sifanggou.net.bean.SerachBusinessOnNameResponseBean;
import com.app.sifanggou.utils.CommonUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/19.
 */

public class SearchBusinessCommodityOnNameHttpRunner extends HttpRunner {
    @Override
    public void onEventRun(Event event) throws Exception {
        String commodity_name = (String) event.getParamAtIndex(0);
        String order_type = (String) event.getParamAtIndex(1);
        String business_code = (String) event.getParamAtIndex(2);
        String item_num = (String) event.getParamAtIndex(3);
        String page_no = (String) event.getParamAtIndex(4);
        String tag = (String) event.getParamAtIndex(5);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("commodity_name", commodity_name);
        map.put("order_type", order_type);
        map.put("business_code", business_code);
        map.put("item_num", item_num);
        map.put("page_no", page_no);

        String result = HttpUtils.doPost(URLUtils.SEARCHBUSINESSCOMMODITYONNAME, map);

        Log.i(AppContext.LOG_NET, result);
        if (CommonUtils.isEmpty(result)) {
            event.setSuccess(false);
            event.setFailException(new Exception("网络错误"));
            return;
        }

        SearchBusinessCommodityOnNameResponseBean param = gson.fromJson(result, SearchBusinessCommodityOnNameResponseBean.class);
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
