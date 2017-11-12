package com.app.sifanggou.net.httprunner;

import java.util.HashMap;

import android.text.TextUtils;
import android.util.Log;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.HttpUtils;
import com.app.sifanggou.net.URLUtils;
import com.app.sifanggou.net.bean.CityResponseBean;
import com.app.sifanggou.net.bean.ProvinceResponseBean;
import com.app.sifanggou.utils.CommonUtils;

public class GetProvinceCityZoneHttpRunner extends HttpRunner {

	@Override
	public void onEventRun(Event event) throws Exception {

		// TODO Auto-generated method stub
		String province_code = (String) event.getParamAtIndex(0);
		HashMap<String, String> map = new HashMap<String, String>();		
		if (!TextUtils.isEmpty(province_code)) {
			map.put("province_code", province_code);
		}
		
		String result = HttpUtils.doPost(URLUtils.GETPROVINCECITYZONE, map);
		
		Log.i(AppContext.LOG_NET, result);
		if (CommonUtils.isEmpty(result)) {
			event.setSuccess(false);
			event.setFailException(new Exception("网络错误"));
			return;
		}
		
		if (TextUtils.isEmpty(province_code)) {
			ProvinceResponseBean param = gson.fromJson(result, ProvinceResponseBean.class);
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
		} else {
			CityResponseBean param = gson.fromJson(result, CityResponseBean.class);
			if (param == null) {
				event.setSuccess(false);
				return;
			}
			if (checkParams(event, param)) {
				event.setSuccess(true);
				event.addReturnParam(param);
				event.addReturnParam(province_code);
			} else {
				event.setSuccess(false);
				if (!TextUtils.isEmpty(param.getMessage())) {
					event.setFailException(new Exception(param.getMessage()));
				}
			}
		}
	
	}

}
