package com.app.sifanggou.activity;

import android.os.Bundle;

import com.app.sifanggou.R;

/**
 * Created by Administrator on 2018/1/6.
 */

public class ShouCangEdActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        initListener();
    }

    private void initView() {
        setTitle("我被收藏");
        addBack(R.id.rl_back);
    }

    private void initData() {

    }

    private void initListener() {

    }
}
