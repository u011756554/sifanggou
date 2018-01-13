package com.app.sifanggou.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.app.sifanggou.R;

import java.util.Locale;

import io.rong.imlib.model.Conversation;

/**
 * Created by Administrator on 2018/1/14.
 */

public class ChatActivity extends BaseActivity {

    private String targetId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    private void initView() {
        addBack(R.id.rl_back);
        targetId = getIntent().getData().getQueryParameter("targetId");//id

        String nickName = getIntent().getData().getQueryParameter("title");//昵称
        if (!TextUtils.isEmpty(nickName)) {
            setTitle(nickName);
        }
    }

    private void initListener() {

    }
}
