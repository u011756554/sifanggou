package com.app.sifanggou.activity;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginActivity extends BaseActivity {

	@ViewInject(R.id.btn_login)
	private Button btnLogin;
	@ViewInject(R.id.rl_register)
	private RelativeLayout rlRegister;
	@ViewInject(R.id.tv_forget)
	private TextView tvForget;
	@ViewInject(R.id.edt_phone)
	private EditText edtPhone;
	@ViewInject(R.id.edt_pwd)
	private EditText edtPwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		initListener();
	}

	private void initView() {
		String phone = PreManager.getString(getApplicationContext(),AppContext.USER_ACCOUNT);
		if(!TextUtils.isEmpty(phone)) {
			edtPhone.setText(phone);
		}
	}
	
	private void initListener() {
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(TextUtils.isEmpty(edtPhone.getText().toString())) {
					CommonUtils.showToast("输入手机号");
					return;
				}
				if(TextUtils.isEmpty(edtPwd.getText().toString())) {
					CommonUtils.showToast("输入密码");
					return;
				}
				try {
					pushEventBlock(EventCode.HTTP_BUSINESSLOGIN,edtPhone.getText().toString(),CommonUtils.EncoderByMd5(edtPwd.getText().toString()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		rlRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
		
		tvForget.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,ForGetPwdActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onEventRunEnd(Event event) {
		super.onEventRunEnd(event);
		if (event.getEventCode() == EventCode.HTTP_BUSINESSLOGIN) {
			if (event.isSuccess()) {
				PreManager.putString(getApplicationContext(),AppContext.USER_ACCOUNT,edtPhone.getText().toString());
				try {
					PreManager.putString(getApplicationContext(),AppContext.USER_PWD,CommonUtils.EncoderByMd5(edtPwd.getText().toString()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				CommonUtils.showToast("登录成功");
				LoginResponseBean loginBean = (LoginResponseBean) event.getReturnParamAtIndex(0);
				PreManager.put(getApplicationContext(), AppContext.USER_LOGIN,loginBean);

				Intent intent = new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
			} else {
				CommonUtils.showToast(event.getFailMessage());
			}
		}
	}
}
