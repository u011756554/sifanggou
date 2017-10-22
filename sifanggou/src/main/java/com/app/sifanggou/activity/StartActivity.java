package com.app.sifanggou.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.WindowManager;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.app.sifanggou.AppContext;
import com.app.sifanggou.MyApplication;
import com.app.sifanggou.bean.AliyunTokenBean;
import com.app.sifanggou.bean.CityMarketBean;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.CityMarketResponseBean;
import com.app.sifanggou.net.bean.GetAliyunstsTokenResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;

public class StartActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
	                WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		super.onCreate(savedInstanceState);

		//获取token
		pushEventNoProgress(EventCode.HTTP_GETALIYUNSTSTOKEN);
	}

	@Override
	public void onEventRunEnd(Event event) {
		super.onEventRunEnd(event);
		if (event.getEventCode() == EventCode.HTTP_GETALIYUNSTSTOKEN) {
//			GetAliyunstsTokenResponseBean tokenBean = (GetAliyunstsTokenResponseBean) event.getReturnParamAtIndex(0);
//			if (tokenBean != null && tokenBean.getData() != null
//					&& tokenBean.getData().getAliyun_sts_token() != null && tokenBean.getData().getAliyun_sts_token().getCredentials() != null
//					&& !TextUtils.isEmpty(tokenBean.getData().getAliyun_sts_token().getCredentials().getAccessKeyId())
//					&& !TextUtils.isEmpty(tokenBean.getData().getAliyun_sts_token().getCredentials().getAccessKeySecret())
//					&& !TextUtils.isEmpty(tokenBean.getData().getAliyun_sts_token().getCredentials().getSecurityToken())) {
//				System.out.print("OSS 数据获取成功");
//				PreManager.putString(getApplicationContext(), AppContext.OSS_ACCESSKEYID,tokenBean.getData().getAliyun_sts_token().getCredentials().getAccessKeyId());
//				PreManager.putString(getApplicationContext(), AppContext.OSS_SECRETKEYID,tokenBean.getData().getAliyun_sts_token().getCredentials().getAccessKeySecret());
//				PreManager.putString(getApplicationContext(), AppContext.OSS_SECURITYTOKEN,tokenBean.getData().getAliyun_sts_token().getCredentials().getSecurityToken());
//			}
			AliyunTokenBean tokenBean = (AliyunTokenBean) event.getReturnParamAtIndex(0);
			if(tokenBean != null && tokenBean.getCredentials() != null
					&& !TextUtils.isEmpty(tokenBean.getCredentials().getAccessKeyId())
					&& !TextUtils.isEmpty(tokenBean.getCredentials().getAccessKeySecret())
					&& !TextUtils.isEmpty(tokenBean.getCredentials().getSecurityToken())) {
				System.out.print("OSS 数据获取成功");
				PreManager.putString(getApplicationContext(), AppContext.OSS_ACCESSKEYID,tokenBean.getCredentials().getAccessKeyId());
				PreManager.putString(getApplicationContext(), AppContext.OSS_SECRETKEYID,tokenBean.getCredentials().getAccessKeySecret());
				PreManager.putString(getApplicationContext(), AppContext.OSS_SECURITYTOKEN,tokenBean.getCredentials().getSecurityToken());
			}

		} else {
			CommonUtils.showToast(event.getFailMessage());
		}

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
					// 没有权限，申请权限。
					ActivityCompat.requestPermissions(StartActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONCODE);
				}else{
					// 有权限了，去放肆吧。
					Intent intent = new Intent(StartActivity.this,LoginActivity.class);
					startActivity(intent);
					finish();
				}
			}
		}, 3000);
	}

	private static final int PERMISSIONCODE = 0x123;

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch(requestCode) {
			case PERMISSIONCODE:
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					// 权限被用户同意，可以去放肆了。
					Intent intent = new Intent(StartActivity.this,LoginActivity.class);
					startActivity(intent);
					finish();
				} else {
					// 权限被用户拒绝了，洗洗睡吧。
					MyApplication.instance.exit();
				}
				break;
		}
	}
}
