package com.app.sifanggou.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.bean.HuoJiaType;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.BaseResponseBean;
import com.app.sifanggou.net.bean.GetBusinessCanAllocateShelfNumResponseBean;
import com.app.sifanggou.net.bean.GetShelfAmountResponseBean;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.app.sifanggou.view.BaseDialog;
import com.app.sifanggou.view.YearDialog;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/18 0018.
 */

public class AddHuoJiaActivity extends BaseActivity {
    @ViewInject(R.id.ll_putong)
    private LinearLayout llPuTong;
    @ViewInject(R.id.ll_daili)
    private LinearLayout llDaiLi;
    @ViewInject(R.id.tv_putonghuojia)
    private TextView tvPuTongHuoJia;
    @ViewInject(R.id.tv_putonghuojia_line)
    private TextView viewPuTongHuoJia;
    @ViewInject(R.id.tv_dailihuojia)
    private TextView tvDaiLiHuoJia;
    @ViewInject(R.id.tv_dailihuojia_line)
    private TextView viewDaiLiHuoJia;
    @ViewInject(R.id.tv_count)
    private TextView tvCount;
    @ViewInject(R.id.edt_year)
    private EditText edtYear;
    @ViewInject(R.id.edt_count)
    private EditText edtCount;
    @ViewInject(R.id.rl_year)
    private RelativeLayout rlYear;
    @ViewInject(R.id.tv_price)
    private TextView tvPrice;
    @ViewInject(R.id.btn_kaitong)
    private Button btnKaiTong;

    private LoginResponseBean loginBean;
    private List<String> yearList = new ArrayList<String>();
    private HuoJiaType huoJiaType = HuoJiaType.PUTONG;

    private YearDialog yearDialog;

    private String year;
    private String count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        edtYear.setEnabled(false);
        addBack(R.id.rl_back);
        setTitle("新增货架");
        setRightResource(R.drawable.icon_addproduct);
    }

    private void initData() {
        loginBean = PreManager.get(getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
        refreshHuoJiaType();

        for(int i = 1 ; i < 2 ; i++) {
            yearList.add(i+"");
        }
    }

    private void initListener() {
        llPuTong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huoJiaType = HuoJiaType.PUTONG;
                refreshHuoJiaType();
            }
        });

        llDaiLi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huoJiaType = HuoJiaType.DAILI;
                refreshHuoJiaType();
            }
        });

        rlYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtCount.getText().toString())) {
                    CommonUtils.showToast("请输入货架数量");
                    return;
                }
                if (yearDialog == null) {
                    yearDialog = new YearDialog(AddHuoJiaActivity.this);
                }
                yearDialog.setData(yearList,0);
                yearDialog.setListener(new BaseDialog.DialogListener() {
                    @Override
                    public void update(Object object) {
                        year = (String) object;
                        count = edtCount.getText().toString();
                        if (!TextUtils.isEmpty(year) && !TextUtils.isEmpty(year) ) {
                            edtYear.setText(year);
                            pushEventNoProgress(EventCode.HTTP_GETSHELFAMOUNT,count,year);
                        }
                    }
                });
                yearDialog.show();
            }
        });

        btnKaiTong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(year) && !TextUtils.isEmpty(year)
                        && loginBean != null && loginBean.getData() != null && loginBean.getData().getLogin_info() != null && loginBean.getData().getLogin_info().getBusiness_info() != null) {
                    pushEventBlock(EventCode.HTTP_ADDBUSINESSBUYSHELFNUM,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),count,year,huoJiaType.getType());
                }
            }
        });
    }

    private void refreshData() {
        if (loginBean != null && loginBean.getData() != null && loginBean.getData().getLogin_info() != null && loginBean.getData().getLogin_info().getBusiness_info() != null) {
            pushEventNoProgress(EventCode.HTTP_GETBUSINESSCANALLOCATESHELFNUM,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(), huoJiaType.getType());
        }
    }

    private void refreshHuoJiaType() {
        if (huoJiaType == HuoJiaType.PUTONG) {
            tvPuTongHuoJia.setSelected(true);
            viewPuTongHuoJia.setSelected(true);
            tvDaiLiHuoJia.setSelected(false);
            viewDaiLiHuoJia.setSelected(false);
        } else {
            tvPuTongHuoJia.setSelected(false);
            viewPuTongHuoJia.setSelected(false);
            tvDaiLiHuoJia.setSelected(true);
            viewDaiLiHuoJia.setSelected(true);
        }
        refreshData();
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_GETBUSINESSCANALLOCATESHELFNUM) {
            if (event.isSuccess()) {
                GetBusinessCanAllocateShelfNumResponseBean bean = (GetBusinessCanAllocateShelfNumResponseBean) event.getReturnParamAtIndex(0);
                if (bean != null && bean.getData() != null && !TextUtils.isEmpty(bean.getData().getShelf_num())) {
                    tvCount.setText("("+bean.getData().getShelf_num()+")");
                }
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }

        if (event.getEventCode() == EventCode.HTTP_GETSHELFAMOUNT) {
            if (event.isSuccess()) {
                GetShelfAmountResponseBean bean = (GetShelfAmountResponseBean) event.getReturnParamAtIndex(0);
                if (bean != null && bean.getData() != null && !TextUtils.isEmpty(bean.getData().getShelf_amount())) {
                    tvPrice.setText("￥"+bean.getData().getShelf_amount());
                }
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }

        if (event.getEventCode() == EventCode.HTTP_ADDBUSINESSBUYSHELFNUM) {
            if (event.isSuccess()) {
                BaseResponseBean bean = (BaseResponseBean) event.getReturnParamAtIndex(0);
                if (bean != null) {
                    CommonUtils.showToast(bean.getMessage());
                }
                finish();
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
    }
}
