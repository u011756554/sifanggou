package com.app.sifanggou.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sifanggou.R;
import com.app.sifanggou.view.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/21.
 */

public class ZhiWeiDialog extends BaseDialog {

    private TextView tvCancel;
    private TextView tvConfirm;

    private WheelView wvZhiWei;

    private List<String> zhiWeiList = new ArrayList<String>();

    public ZhiWeiDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_zhiwei);
        initView();
    }

    private void initView() {
        addClickCancel();
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        wvZhiWei = (WheelView) findViewById(R.id.wv_zhiwei);

        tvCancel.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dismiss();
            }
        });

        tvConfirm.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dismiss();
            }
        });

        if (zhiWeiList != null) {
            refreshView(0);
        }
    }

    private void refreshView(int index) {
        if (wvZhiWei != null && zhiWeiList != null && zhiWeiList.size() > 0) {
            wvZhiWei.setItems(zhiWeiList, index);
            wvZhiWei.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(int selectedIndex, String item) {
                    Toast.makeText(mContext, "Item:"+item+" index:"+selectedIndex, Toast.LENGTH_LONG).show();
                    if (mListener != null) {
                        mListener.update(item);
                    }
                }
            });
            if (mListener != null) {
                mListener.update(zhiWeiList.get(index));
            }
        }
    }

    public void setData(List<String> list,int index) {
        this.zhiWeiList = list;
        if (wvZhiWei != null) {
            refreshView(index);
        }
    }
}
