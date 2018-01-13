package com.app.sifanggou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.app.sifanggou.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2018/1/8.
 */

public class UpdatePwdActivity extends BaseActivity {
    @ViewInject(R.id.rl_old_pwd)
    private RelativeLayout rlOldPwd;
    @ViewInject(R.id.rl_forget_pwd)
    private RelativeLayout rlForgetPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    private void initView() {
        setTitle("修改登录密码");
        addBack(R.id.rl_back);
    }

    private void initListener() {
        rlOldPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdatePwdActivity.this,ComfirmPwdActivity.class);
                startActivity(intent);
            }
        });

        rlForgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdatePwdActivity.this,ForGetPwdActivity.class);
                startActivity(intent);
            }
        });
    }
}
