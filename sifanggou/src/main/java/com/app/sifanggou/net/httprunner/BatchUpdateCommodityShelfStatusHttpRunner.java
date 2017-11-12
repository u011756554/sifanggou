package com.app.sifanggou.net.httprunner;

import android.text.TextUtils;
import android.util.Log;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.MyApplication;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.HttpUtils;
import com.app.sifanggou.net.URLUtils;
import com.app.sifanggou.net.bean.BaseResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/10/7.
 */

public class BatchUpdateCommodityShelfStatusHttpRunner extends HttpRunner {
    @Override
    public void onEventRun(Event event) throws Exception {
        String business_code = (String) event.getParamAtIndex(0);
        String user_name = (String) event.getParamAtIndex(1);
        ArrayList<Integer> commodityIdList = (ArrayList<Integer>) event.getParamAtIndex(2);
        String enable = (String) event.getParamAtIndex(3);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("business_code", business_code);
        map.put("user_name", user_name);
        String trans_no = System.currentTimeMillis() + "";
        map.put("trans_no", trans_no);
        map.put("sign", CommonUtils.getSign(business_code,user_name,trans_no, PreManager.getString(MyApplication.instance.getApplicationContext(),AppContext.USER_PWD)));
        map.put("commodity_id_list", commodityIdList);
        map.put("enable", enable);

        String result = HttpUtils.doPostObject(URLUtils.BATCHUPDATECOMMODITYSHELFSTATUS, map);

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
            event.addReturnParam(enable);
        } else {
            event.setSuccess(false);
            if (!TextUtils.isEmpty(param.getMessage())) {
                event.setFailException(new Exception(param.getMessage()));
            }
        }
    }
}
