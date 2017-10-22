package com.app.sifanggou.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.bean.HuoJiaType;
import com.app.sifanggou.bean.ProductType;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.BaseResponseBean;
import com.app.sifanggou.net.bean.GetBusinessCanAllocateShelfNumResponseBean;
import com.app.sifanggou.net.bean.GetCommodityTypeListResponseBean;
import com.app.sifanggou.net.bean.GetShelfAmountResponseBean;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.PreManager;
import com.app.sifanggou.view.BaseDialog;
import com.app.sifanggou.view.YearDialog;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/6.
 */

public class AddProductActivity extends BaseActivity {

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
    @ViewInject(R.id.sv_putong)
    private ScrollView svPutong;
    @ViewInject(R.id.sv_daili)
    private ScrollView svDaili;

    private LoginResponseBean loginBean;
    private HuoJiaType huoJiaType = HuoJiaType.PUTONG;

    //普通商品属性
    private List<String> puTongUrlList = new ArrayList<String>();
    @ViewInject(R.id.edt_dec)
    private EditText edtDec;
    @ViewInject(R.id.edt_name)
    private EditText edtName;
    @ViewInject(R.id.edt_pinpai)
    private EditText edtPinPai;
    @ViewInject(R.id.edt_price_tongji)
    private EditText edtTongJiPrice;
    @ViewInject(R.id.edt_price_xiaji)
    private EditText edtXiaJiPrice;
    @ViewInject(R.id.edt_kucun)
    private EditText edtKuCun;
    @ViewInject(R.id.edt_chandi)
    private EditText edtChanDi;
    @ViewInject(R.id.edt_guige)
    private EditText edtGuiGe;
    @ViewInject(R.id.edt_dengji)
    private EditText edtZhiLiangDengJi;
    @ViewInject(R.id.edt_fenlei)
    private EditText edtFenLei;

    //代理商品属性
    private List<String> daiLiUrlList = new ArrayList<String>();
    private List<String> daiLiHeTongList = new ArrayList<String>();
    @ViewInject(R.id.edt_dec_daili)
    private EditText edtDecDaiLi;
    @ViewInject(R.id.edt_name_daili)
    private EditText edtNameDaiLi;
    @ViewInject(R.id.edt_pinpai_daili)
    private EditText edtPinPaiDaiLi;
    @ViewInject(R.id.edt_price_tongji_daili)
    private EditText edtTongJiPriceDaiLi;
    @ViewInject(R.id.edt_price_xiaji_daili)
    private EditText edtXiaJiPriceDaiLi;
    @ViewInject(R.id.edt_kucun_daili)
    private EditText edtKuCunDaiLi;
    @ViewInject(R.id.edt_chandi_daili)
    private EditText edtChanDiDaiLi;
    @ViewInject(R.id.edt_guige_daili)
    private EditText edtGuiGeDaiLi;
    @ViewInject(R.id.edt_dengji_daili)
    private EditText edtZhiLiangDengJiDaiLi;
    @ViewInject(R.id.edt_fenlei_daili)
    private EditText edtFenLeiDaiLi;
    @ViewInject(R.id.edt_jiebie_daili)
    private EditText edtJieBieDaiLi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        addBack(R.id.rl_back);
        setTitle("添加商品");

        edtFenLei.setEnabled(false);
        edtFenLeiDaiLi.setEnabled(false);
        edtJieBieDaiLi.setEnabled(false);
    }

    private void initData() {
        loginBean = PreManager.get(getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
        refreshHuoJiaType();
        pushEventNoProgress(EventCode.HTTP_GETCOMMODITYTYPELIST);

        puTongUrlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507355751461&di=200277a27339c9ae465b11b985369813&imgtype=0&src=http%3A%2F%2Fwww.114nba.com%2Fuploadfile%2F2013%2F0107%2F20130107044213902.jpg");
        daiLiUrlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507355751459&di=22c6e5e8f213128402a070873d0c6eea&imgtype=0&src=http%3A%2F%2Fwww.114nba.com%2Fuploadfile%2F2013%2F0107%2F20130107044212827.jpg");
        daiLiHeTongList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507355751459&di=466ef52d4c1672841acdfae7b36f4ddb&imgtype=0&src=http%3A%2F%2Fwww.114nba.com%2Fuploadfile%2F2013%2F0107%2F20130107044213926.jpg");
    }

    private void refreshHuoJiaType() {
        if (huoJiaType == HuoJiaType.PUTONG) {
            tvPuTongHuoJia.setSelected(true);
            viewPuTongHuoJia.setSelected(true);
            tvDaiLiHuoJia.setSelected(false);
            viewDaiLiHuoJia.setSelected(false);
            svPutong.setVisibility(View.VISIBLE);
            svDaili.setVisibility(View.GONE);
        } else {
            tvPuTongHuoJia.setSelected(false);
            viewPuTongHuoJia.setSelected(false);
            tvDaiLiHuoJia.setSelected(true);
            viewDaiLiHuoJia.setSelected(true);
            svPutong.setVisibility(View.GONE);
            svDaili.setVisibility(View.VISIBLE);
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

        setRightTextClickListener("完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (huoJiaType == HuoJiaType.PUTONG) {
                    if (TextUtils.isEmpty(edtName.getText().toString())) {
                        CommonUtils.showToast("请输入商品名称");
                        return;
                    }
                    if (puTongUrlList.size() <= 0) {
                        CommonUtils.showToast("请添加商品图片");
                        return;
                    }
                    if (TextUtils.isEmpty(edtDec.getText().toString())) {
                        CommonUtils.showToast("请输入商品描述");
                        return;
                    }
                    if (TextUtils.isEmpty(edtPinPai.getText().toString())) {
                        CommonUtils.showToast("请输入商品品牌");
                        return;
                    }
                    if (TextUtils.isEmpty(edtTongJiPrice.getText().toString())) {
                        CommonUtils.showToast("请输入商品同级价格");
                        return;
                    }
                    if (TextUtils.isEmpty(edtXiaJiPrice.getText().toString())) {
                        CommonUtils.showToast("请输入商品下级价格");
                        return;
                    }
                    if (TextUtils.isEmpty(edtKuCun.getText().toString())) {
                        CommonUtils.showToast("请输入库存");
                        return;
                    }
                    if (TextUtils.isEmpty(edtChanDi.getText().toString())) {
                        CommonUtils.showToast("请输入产地");
                        return;
                    }
                    if (TextUtils.isEmpty(edtGuiGe.getText().toString())) {
                        CommonUtils.showToast("请输入商品规格");
                        return;
                    }
                    if (TextUtils.isEmpty(edtZhiLiangDengJi.getText().toString())) {
                        CommonUtils.showToast("请输入商品质量等级");
                        return;
                    }
//                    if (TextUtils.isEmpty(edtFenLei.getText().toString())) {
//                        CommonUtils.showToast("请选择商品分类");
//                        return;
//                    }

                    String business_code = loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code();
                    String mobile = loginBean.getData().getLogin_info().getBusiness_info().getMobile();
                    String trans_no = System.currentTimeMillis() + "";
                    String sign = CommonUtils.getSign(business_code,mobile,trans_no,PreManager.getString(getApplicationContext(),AppContext.USER_PWD));
                    String commodity_name = edtName.getText().toString();
                    String type = "";
                    if (huoJiaType == HuoJiaType.PUTONG) {
                        type = ProductType.COMMON.getType();
                    } else {
                        type = ProductType.AGENCY.getType();
                    }
                    String brand_name = edtPinPai.getText().toString();
                    String first_level_category_code = "1";
                    String second_level_category_code = "49";
                    String third_level_category_code = "50";
                    StringBuffer sbf = new StringBuffer();
                    for(String url : puTongUrlList) {
                        sbf.append(url).append(",");
                    }
                    String commodity_pic_url = sbf.toString().substring(0,sbf.toString().length() - 1);
                    String quality_grade = edtZhiLiangDengJi.getText().toString();
                    String production_place = edtChanDi.getText().toString();
                    String specification = edtGuiGe.getText().toString();
                    String intro = edtDec.getText().toString();

                    String a_price = (int)(Float.valueOf(edtTongJiPrice.getText().toString()) * 100) + "";
                    String b_price = (int)(Float.valueOf(edtXiaJiPrice.getText().toString()) * 100) + "";

                    pushEventBlock(EventCode.HTTP_ADDBUSINESSCOMMODITY,business_code,mobile,trans_no,sign,commodity_name,type,brand_name,first_level_category_code,second_level_category_code,third_level_category_code,
                            commodity_pic_url,quality_grade,production_place,specification,intro,a_price,b_price);

                } else {
                    if (TextUtils.isEmpty(edtNameDaiLi.getText().toString())) {
                        CommonUtils.showToast("请输入商品名称");
                        return;
                    }
                    if (daiLiUrlList.size() <= 0) {
                        CommonUtils.showToast("请添加商品图片");
                        return;
                    }
                    if (daiLiHeTongList.size() <= 0) {
                        CommonUtils.showToast("请添加合同图片");
                        return;
                    }
                    if (TextUtils.isEmpty(edtDecDaiLi.getText().toString())) {
                        CommonUtils.showToast("请输入商品描述");
                        return;
                    }
                    if (TextUtils.isEmpty(edtPinPaiDaiLi.getText().toString())) {
                        CommonUtils.showToast("请输入商品品牌");
                        return;
                    }
                    if (TextUtils.isEmpty(edtTongJiPriceDaiLi.getText().toString())) {
                        CommonUtils.showToast("请输入商品同级价格");
                        return;
                    }
                    if (TextUtils.isEmpty(edtXiaJiPriceDaiLi.getText().toString())) {
                        CommonUtils.showToast("请输入商品下级价格");
                        return;
                    }
                    if (TextUtils.isEmpty(edtKuCunDaiLi.getText().toString())) {
                        CommonUtils.showToast("请输入库存");
                        return;
                    }
                    if (TextUtils.isEmpty(edtChanDiDaiLi.getText().toString())) {
                        CommonUtils.showToast("请输入产地");
                        return;
                    }
                    if (TextUtils.isEmpty(edtGuiGeDaiLi.getText().toString())) {
                        CommonUtils.showToast("请输入商品规格");
                        return;
                    }
                    if (TextUtils.isEmpty(edtZhiLiangDengJiDaiLi.getText().toString())) {
                        CommonUtils.showToast("请输入商品质量等级");
                        return;
                    }
//                    if (TextUtils.isEmpty(edtFenLeiDaiLi.getText().toString())) {
//                        CommonUtils.showToast("请选择商品分类");
//                        return;
//                    }
//                    if (TextUtils.isEmpty(edtJieBieDaiLi.getText().toString())) {
//                        CommonUtils.showToast("请选择代理级别");
//                        return;
//                    }

                    String business_code = loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code();
                    String mobile = loginBean.getData().getLogin_info().getBusiness_info().getMobile();
                    String trans_no = System.currentTimeMillis() + "";
                    String sign = CommonUtils.getSign(business_code,mobile,trans_no,PreManager.getString(getApplicationContext(),AppContext.USER_PWD));
                    String commodity_name = edtNameDaiLi.getText().toString();
                    String type = "";
                    if (huoJiaType == HuoJiaType.PUTONG) {
                        type = ProductType.COMMON.getType();
                    } else {
                        type = ProductType.AGENCY.getType();
                    }
                    String brand_name = edtPinPaiDaiLi.getText().toString();
                    String first_level_category_code = "1";
                    String second_level_category_code = "49";
                    String third_level_category_code = "50";
                    StringBuffer sbf = new StringBuffer();
                    for(String url : daiLiUrlList) {
                        sbf.append(url).append(",");
                    }
                    String commodity_pic_url = sbf.toString().substring(0,sbf.toString().length() - 1);
                    StringBuffer sbfHetong = new StringBuffer();
                    for(String url : daiLiHeTongList) {
                        sbfHetong.append(url).append(",");
                    }
                    String agency_contract_pic_url = sbfHetong.toString().substring(0,sbfHetong.toString().length() - 1);
                    String agent_level = "factory_direct";
                    String quality_grade = edtZhiLiangDengJiDaiLi.getText().toString();
                    String production_place = edtChanDiDaiLi.getText().toString();
                    String specification = edtGuiGeDaiLi.getText().toString();
                    String intro = edtDecDaiLi.getText().toString();
                    String a_price = (int)(Float.valueOf(edtTongJiPriceDaiLi.getText().toString()) * 100) + "";
                    String b_price = (int)(Float.valueOf(edtXiaJiPriceDaiLi.getText().toString()) * 100) + "";

                    pushEventBlock(EventCode.HTTP_ADDBUSINESSCOMMODITY,business_code,mobile,trans_no,sign,commodity_name,type,brand_name,first_level_category_code,second_level_category_code,third_level_category_code,
                            commodity_pic_url,quality_grade,production_place,specification,intro,a_price,b_price,agency_contract_pic_url,agent_level);
                }
            }
        });
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_GETBUSINESSCANALLOCATESHELFNUM) {
            if (event.isSuccess()) {
                GetBusinessCanAllocateShelfNumResponseBean bean = (GetBusinessCanAllocateShelfNumResponseBean) event.getReturnParamAtIndex(0);
                if (bean != null && bean.getData() != null && !TextUtils.isEmpty(bean.getData().getShelf_num())) {
                    if (huoJiaType == HuoJiaType.PUTONG) {
                        tvPuTongHuoJia.setText("普通商品货架("+bean.getData().getShelf_num()+")");
                    } else {
                        tvDaiLiHuoJia.setText("代理权商品货架("+bean.getData().getShelf_num()+")");
                    }
                }
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }

        if (event.getEventCode() == EventCode.HTTP_GETCOMMODITYTYPELIST) {
            if (event.isSuccess()) {
                GetCommodityTypeListResponseBean bean = (GetCommodityTypeListResponseBean) event.getReturnParamAtIndex(0);
                System.out.print(bean.getData());
                if (bean != null) {

                }
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
        if (event.getEventCode() == EventCode.HTTP_ADDBUSINESSCOMMODITY) {
            if (event.isSuccess()) {
                finish();
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
    }
}
