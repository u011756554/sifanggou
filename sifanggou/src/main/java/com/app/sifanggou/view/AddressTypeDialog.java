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
 * Created by Administrator on 2017/12/1.
 */

public class AddressTypeDialog extends BaseDialog {

    private TextView tvCancel;
    private TextView tvConfirm;

    private WheelView wvAddress;

    private List<String> addressTypeList = new ArrayList<String>();
    public AddressTypeDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_addresstype);
        initView();
    }

    private void initView() {
        addClickCancel();
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        wvAddress = (WheelView) findViewById(R.id.agentlevel_wv);

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

        if (addressTypeList != null) {
            refreshView(0);
        }
    }

    private void refreshView(int index) {
        if (wvAddress != null && addressTypeList != null && addressTypeList.size() > 0) {
            wvAddress.setItems(addressTypeList, index);
            wvAddress.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(int selectedIndex, String item) {
                    Toast.makeText(mContext, "Item:"+item+" index:"+selectedIndex, Toast.LENGTH_LONG).show();
                    if (mListener != null) {
                        mListener.update(item);
                    }
                }
            });
            if (mListener != null) {
                mListener.update(addressTypeList.get(index));
            }
        }
    }

    public void setData(List<String> list,int index) {
        this.addressTypeList = list;
        if (wvAddress != null) {
            refreshView(index);
        }
    }
}
