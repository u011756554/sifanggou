package com.app.sifanggou.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.app.sifanggou.R;
import com.app.sifanggou.utils.CommonUtils;

/**
 * Created by Administrator on 2017/11/12.
 */

public class EditPriceDialog extends BaseDialog {
    private RelativeLayout rlComfirm;
    private RelativeLayout rlCancel;
    private EditText edtTongJiPrice;
    private EditText edtXiaJiPrice;

    private String tongjiPrice;
    private String xiajiPrice;
    public EditPriceDialog(Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_editprice);
        initView();
    }

    private void initView() {
        addClickCancel();
        rlCancel = (RelativeLayout) findViewById(R.id.mrl_dialog_cancel);
        rlComfirm = (RelativeLayout) findViewById(R.id.mrl_dialog_confirm);
        edtXiaJiPrice = (EditText) findViewById(R.id.edt_b_price);
        edtTongJiPrice = (EditText) findViewById(R.id.edt_a_price);

        if (!TextUtils.isEmpty(tongjiPrice)) {
            edtTongJiPrice.setText(Float.valueOf(this.tongjiPrice) / 100+"");
        }
        if (!TextUtils.isEmpty(xiajiPrice)) {
            edtXiaJiPrice.setText(Float.valueOf(this.xiajiPrice) / 100+"");
        }
        rlCancel.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        rlComfirm.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (TextUtils.isEmpty(edtTongJiPrice.getText().toString())) {
                    CommonUtils.showToast("请输入同级发货价");
                    return;
                }
                if (TextUtils.isEmpty(edtXiaJiPrice.getText().toString())) {
                    CommonUtils.showToast("请输入下级发货价");
                    return;
                }
                if (updatePriceListener != null) {
                    int aPrice = (int) (Float.valueOf(edtTongJiPrice.getText().toString()) * 100);
                    int bPrice = (int) (Float.valueOf(edtXiaJiPrice.getText().toString()) * 100);
                    updatePriceListener.update(aPrice + "",bPrice + "");
                    dismiss();
                }
            }
        });
    }

    public void setData(String aPrice,String bPrice) {
        this.tongjiPrice = aPrice;
        this.xiajiPrice = bPrice;
        if (edtXiaJiPrice != null) {
            edtXiaJiPrice.setText(Float.valueOf(this.xiajiPrice) / 100+"");
        }
        if (edtTongJiPrice != null) {
            edtTongJiPrice.setText(Float.valueOf(this.tongjiPrice) / 100+"");
        }
    }

    public interface UpdatePriceListener {
        void update(String aPrice,String bPrice);
    }

    private UpdatePriceListener updatePriceListener;

    public void setUpdatePriceListener(UpdatePriceListener listener) {
        this.updatePriceListener = listener;
    }
}
