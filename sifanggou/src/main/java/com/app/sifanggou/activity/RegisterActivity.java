package com.app.sifanggou.activity;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.utils.CommonUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RegisterActivity extends BaseActivity {

	@ViewInject(R.id.rl_delete)
	private RelativeLayout rlDelete;
	@ViewInject(R.id.tv_getcode)
	private TextView tvGetCode;
	@ViewInject(R.id.edt_phone)
	private EditText edtPhone;
	@ViewInject(R.id.btn_net)
	private Button btnNext;
	@ViewInject(R.id.edt_code)
	private EditText edtCode;
	@ViewInject(R.id.edt_invitecode)
	private EditText edtInviteCode;
	@ViewInject(R.id.edt_pwd)
	private EditText edtPwd;
	@ViewInject(R.id.ll_xieyi)
	private LinearLayout llXieYi;
	
	private int time = 60;
	private boolean isCoding = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		initListener();
	}
	
	private void initView() {
		btnNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(TextUtils.isEmpty(edtPhone.getText().toString())) {
					CommonUtils.showToast("输入手机号");
					return;
				}
				if(TextUtils.isEmpty(edtCode.getText().toString())) {
					CommonUtils.showToast("输入验证码");
					return;
				}
				if(TextUtils.isEmpty(edtPwd.getText().toString())) {
					CommonUtils.showToast("输入密码");
					return;
				}

				Intent intent = new Intent(RegisterActivity.this,UploadCertificateActivity.class);
				intent.putExtra(UploadCertificateActivity.KEY_CODE,edtCode.getText().toString());
				intent.putExtra(UploadCertificateActivity.KEY_PWD,edtPwd.getText().toString());
				intent.putExtra(UploadCertificateActivity.KEY_PHONE,edtPhone.getText().toString());
				intent.putExtra(UploadCertificateActivity.KEY_INVITECODE,edtInviteCode.getText().toString());
				startActivity(intent);
			}
		});
	}
	
	private void initListener() {
		tvGetCode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(edtPhone.getText().toString())) {
					CommonUtils.showToast("请输入完整");
					return;
				}
				time = 60;
				isCoding = true;
				tvGetCode.setEnabled(false);
				pushEventNoProgress(EventCode.HTTP_GETVERIFYCODE,edtPhone.getText().toString());				
			}
		});
		
		rlDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		llXieYi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RegisterActivity.this,UrlWebClientActivity.class);
				intent.putExtra(UrlWebClientActivity.KEY_URL, AppContext.URL_XIEYI);
				intent.putExtra(UrlWebClientActivity.KEY_TITILE, "注册协议");
				startActivity(intent);
			}
		});
	}
	
	@Override
	public void onEventRunEnd(Event event) {
		// TODO Auto-generated method stub
		super.onEventRunEnd(event);
		if (event.getEventCode() == EventCode.HTTP_GETVERIFYCODE) {
			if (event.isSuccess()) {
				CommonUtils.showToast("验证码发送成功");
				refreshCode();
			} else {
				CommonUtils.showToast(event.getFailMessage());
				isCoding = false;
				tvGetCode.setEnabled(true);
				tvGetCode.setText("重新获取");
			}
		}
	}
	
	private void refreshCode() {
		if (isCoding) {
			tvGetCode.setEnabled(false);
			if (time <= 60 && time > 0) {
				time = time - 1;
				tvGetCode.setText(time+"");
				tvGetCode.setEnabled(false);
				isCoding = true;
				mHandler.postDelayed(timeRunnable, 1000);
			}
			if (time == 0) {
				tvGetCode.setText("重新获取");
				tvGetCode.setEnabled(true);
				isCoding = false;
			}
		}
	}	
	
	private Handler mHandler = new Handler();
	
	private Runnable timeRunnable = new Runnable() {

		@Override
		public void run() {
			refreshCode();
		}
		
	};
	
	protected void onDestroy() {
		super.onDestroy();
		if (mHandler != null && timeRunnable != null) {
			mHandler.removeCallbacks(timeRunnable);
		}
	};	
}
