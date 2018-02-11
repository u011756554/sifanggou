package com.app.sifanggou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.app.sifanggou.R;
import com.app.sifanggou.bean.CommodityInfoBean;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.view.ChangeDateDialog;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/10/24.
 */

public class ChuCangActivity extends BaseActivity{

    @ViewInject(R.id.edt_price_chuhuo)
    private EditText edtChuHuo;
    @ViewInject(R.id.edt_count_chuhuo)
    private EditText edtCountChuHuo;
    @ViewInject(R.id.rl_deadline)
    private RelativeLayout rlDeadLine;
    @ViewInject(R.id.edt_deadline)
    private EditText edtDeadLine;
    @ViewInject(R.id.edt_price_tongji)
    private EditText edtPriceTongJi;
    @ViewInject(R.id.edt_price_xiaji)
    private EditText edtPriceXiaJi;
    @ViewInject(R.id.edt_kucun)
    private EditText edtKuCun;
    @ViewInject(R.id.edt_chandi)
    private EditText edtChanDi;
    @ViewInject(R.id.edt_guige)
    private EditText edtGuiGe;
    @ViewInject(R.id.edt_dengji)
    private EditText edtDengJi;
    @ViewInject(R.id.rl_fenlei)
    private RelativeLayout rlFenLei;
    @ViewInject(R.id.edt_fenlei)
    private EditText edtFenLei;
    @ViewInject(R.id.btn_chucang)
    private Button btnChuCang;

    public static final String KEY_CHUCANG = "key_ChuCangActivity_data";
    private CommodityInfoBean infoData;
    private ChangeDateDialog dateDialog;
    private String dateLine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        initListener();
    }

    private void initView() {
        addBack(R.id.rl_back);
        setTitle("急需出仓");
        edtDeadLine.setEnabled(false);
        edtFenLei.setEnabled(false);
        edtPriceTongJi.setEnabled(false);
        edtPriceXiaJi.setEnabled(false);
        edtKuCun.setEnabled(false);
        edtChanDi.setEnabled(false);
        edtGuiGe.setEnabled(false);
        edtDengJi.setEnabled(false);
        edtFenLei.setEnabled(false);

        if (infoData != null) {
            if (!TextUtils.isEmpty(infoData.getA_price())) {
                float aPrice = Float.valueOf(infoData.getA_price()) / 100;
                edtPriceTongJi.setText(aPrice + "");
            }

            if (!TextUtils.isEmpty(infoData.getB_price())) {
                float bPrice = Float.valueOf(infoData.getB_price()) / 100;
                edtPriceXiaJi.setText(bPrice+"");
            }

            edtKuCun.setText(infoData.getCollection_num());
            edtChanDi.setText(infoData.getProduction_place());
            edtGuiGe.setText(infoData.getSpecification());
            edtDengJi.setText(infoData.getQuality_grade());
            edtFenLei.setText(infoData.getFirst_level_category_name()+" "+infoData.getSecond_level_category_name()+" "+infoData.getThird_level_category_name());
        }
    }

    private void initData() {
        infoData = (CommodityInfoBean) getIntent().getSerializableExtra(KEY_CHUCANG);
    }

    private void initListener() {
        rlDeadLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateDialog == null) {
                    dateDialog = new ChangeDateDialog(ChuCangActivity.this);
                }
                dateDialog.setOnBirthClickListener(new ChangeDateDialog.OnBirthClick() {
                    @Override
                    public void onClick(String year, String month, String day) {
                        edtDeadLine.setText(year+"年-"+month+"月-"+day+"日");
                        dateLine = year+"-"+month+"-"+day;
                    }
                });
                dateDialog.show();
            }
        });

        btnChuCang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (infoData != null) {
                    String commodityId = infoData.getCommodity_id();
                    String sellPrice = edtChuHuo.getText().toString();
                    String num = edtCountChuHuo.getText().toString();

                    if (TextUtils.isEmpty(commodityId) || TextUtils.isEmpty(sellPrice)|| TextUtils.isEmpty(num)|| TextUtils.isEmpty(dateLine)) {
                        CommonUtils.showToast("信息输入不完整");
                        return;
                    }
                    sellPrice = (int)(Float.valueOf(sellPrice) * 100) + "";
                    pushEventBlock(EventCode.HTTP_ADDBUSINESSURGENTSELLCOMMODITY,commodityId,sellPrice,num,dateLine);
                }

            }
        });
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (EventCode.HTTP_ADDBUSINESSURGENTSELLCOMMODITY == event.getEventCode()) {
            if (event.isSuccess()) {
                finish();
                Intent intent = new Intent(ChuCangActivity.this,ChuCangHistoryActivity.class);
                startActivity(intent);
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
    }
}
