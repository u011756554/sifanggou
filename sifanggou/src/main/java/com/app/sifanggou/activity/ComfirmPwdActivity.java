package com.app.sifanggou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.app.sifanggou.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2018/1/8.
 */

public class ComfirmPwdActivity extends BaseActivity {
    @ViewInject(R.id.edt_pwd)
    private EditText edtPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    private void initView() {
        setTitle("验证原密码");
        addBack(R.id.rl_back);

    }

    private void initListener() {
        setRightTextClickListener("下一步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComfirmPwdActivity.this,UpdateOldPwdActivity.class);
                startActivity(intent);
            }
        });
    }
}
