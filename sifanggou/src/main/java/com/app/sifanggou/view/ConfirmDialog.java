package com.app.sifanggou.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.app.sifanggou.R;

/**
 * Created by Administrator on 2017/11/24.
 */

public class ConfirmDialog extends BaseDialog {

    private TextView tvConfirm;
    private TextView tvContent;

    private String content;
    public ConfirmDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);
        initView();
    }

    private void initView() {
        addClickCancel();
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        tvContent = (TextView) findViewById(R.id.tv_content);

        if (!TextUtils.isEmpty(content)) {
            tvContent.setText(content);
        }

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.update("ok");
                }
                dismiss();
            }
        });
    }

    public void setContent(String content) {
        this.content = content;
        if (tvContent != null) {
            tvContent.setText(content);
        }
    }
}
