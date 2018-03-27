package com.app.sifanggou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.MyApplication;
import com.app.sifanggou.R;
import com.app.sifanggou.bean.AgentLevelBean;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.PreManager;
import com.app.sifanggou.view.tree.NodeResource;
import com.lidroid.xutils.view.annotation.ViewInject;

import io.rong.imlib.RongIMClient;

/**
 * Created by Administrator on 2018/1/8.
 */

public class SetActivity extends BaseActivity {
    @ViewInject(R.id.tv_nickname)
    private TextView tvNickName;
    @ViewInject(R.id.tv_phone)
    private TextView tvPhone;
    @ViewInject(R.id.rl_pwd)
    private RelativeLayout rlPwd;
    @ViewInject(R.id.rl_code)
    private RelativeLayout rlCode;
    @ViewInject(R.id.rl_xieyi)
    private RelativeLayout rlXieYi;
    @ViewInject(R.id.rl_nickname)
    private RelativeLayout rlNickName;
    @ViewInject(R.id.btn_exit)
    private Button btnExit;

    private LoginResponseBean loginBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        initListener();
    }

    private void initView() {
        setTitle("设置");
        addBack(R.id.rl_back);
    }

    private void initListener() {
        rlPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetActivity.this,UpdatePwdActivity.class);
                startActivity(intent);
            }
        });

        rlNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetActivity.this,UpdateNickNameActivity.class);
                startActivity(intent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIMClient.getInstance().logout();

                clearData();
                Intent intent = new Intent(SetActivity.this,LoginActivity.class);
                startActivity(intent);
                MyApplication.instance.exit();
            }
        });

        rlXieYi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetActivity.this,UrlWebClientActivity.class);
                intent.putExtra(UrlWebClientActivity.KEY_URL, AppContext.URL_XIEYI);
                intent.putExtra(UrlWebClientActivity.KEY_TITILE, "注册协议");
                startActivity(intent);
            }
        });
    }

    private void clearData() {
        if (loginBean == null || loginBean.getData() == null
                || loginBean.getData().getLogin_info() == null
                || loginBean.getData().getLogin_info().getBusiness_info() == null
                || TextUtils.isEmpty(loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code())) {
            return;
        } else {
            String businessCode = loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code();
            PreManager.putString(getApplication(),AddProductActivity.KEY_DEC + businessCode,"");
            PreManager.putString(getApplication(),AddProductActivity.KEY_NAME + businessCode,"");
            PreManager.putString(getApplication(),AddProductActivity.KEY_XIAJI + businessCode,"");
            PreManager.putString(getApplication(),AddProductActivity.KEY_KUCUN + businessCode,"");
            PreManager.putString(getApplication(),AddProductActivity.KEY_CHANDI + businessCode,"");
            PreManager.putString(getApplication(),AddProductActivity.KEY_GUIGE + businessCode,"");
            PreManager.putString(getApplication(),AddProductActivity.KEY_DENGJI + businessCode,"");
            PreManager.putString(getApplication(),AddProductActivity.KEY_PINPAI + businessCode,"");
            PreManager.putString(getApplication(),AddProductActivity.KEY_TONGJI + businessCode,"");
            PreManager.put(getApplicationContext(),AddProductActivity.KEY_FENLEI + businessCode,null);

            PreManager.putString(getApplication(),AddProductActivity.KEY_DEC_DAILI + businessCode,"");
            PreManager.putString(getApplication(),AddProductActivity.KEY_NAME_DAILI + businessCode,"");
            PreManager.putString(getApplication(),AddProductActivity.KEY_XIAJI_DAILI + businessCode,"");
            PreManager.putString(getApplication(),AddProductActivity.KEY_KUCUN_DAILI + businessCode,"");
            PreManager.putString(getApplication(),AddProductActivity.KEY_CHANDI_DAILI + businessCode,"");
            PreManager.putString(getApplication(),AddProductActivity.KEY_GUIGE_DAILI + businessCode,"");
            PreManager.putString(getApplication(),AddProductActivity.KEY_DENGJI_DAILI + businessCode,"");
            PreManager.putString(getApplication(),AddProductActivity.KEY_PINPAI_DAILI + businessCode,"");
            PreManager.putString(getApplication(),AddProductActivity.KEY_TONGJI_DAILI + businessCode,"");
            PreManager.putString(getApplication(),AddProductActivity.KEY_DEC_DAILI + businessCode,"");
            PreManager.put(getApplicationContext(),AddProductActivity.KEY_FENLEI_DAILI + businessCode,null);
            PreManager.put(getApplicationContext(),AddProductActivity.KEY_JIBIE_DAILI + businessCode,null);
        }
    }

    private void initData() {
        loginBean = PreManager.get(getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
        if (loginBean != null) {
            tvNickName.setText(loginBean.getData().getLogin_info().getName());
            tvPhone.setText(loginBean.getData().getLogin_info().getBusiness_info().getMobile());
        }
    }
}
