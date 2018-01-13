package com.app.sifanggou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.PreManager;
import com.lidroid.xutils.view.annotation.ViewInject;

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
    @ViewInject(R.id.rl_about)
    private RelativeLayout rlAbout;
    @ViewInject(R.id.rl_xieyi)
    private RelativeLayout rlXieYi;
    @ViewInject(R.id.rl_haoping)
    private RelativeLayout rlHaoPing;

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
    }

    private void initData() {
        loginBean = PreManager.get(getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
        if (loginBean != null) {
            tvNickName.setText(loginBean.getData().getLogin_info().getName());
            tvPhone.setText(loginBean.getData().getLogin_info().getBusiness_info().getMobile());
        }
    }
}
