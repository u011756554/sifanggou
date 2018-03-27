package com.app.sifanggou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.adapter.GuangGaoPagerAdapter;
import com.app.sifanggou.bean.AgentLevelType;
import com.app.sifanggou.bean.BusinessInfoBean;
import com.app.sifanggou.bean.CommodityInfoBean;
import com.app.sifanggou.bean.ShouCangType;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.GetBusinessRongYunTokenResponseBean;
import com.app.sifanggou.net.bean.GetCommodityInfoByIdResponseBean;
import com.app.sifanggou.net.bean.IsBusinessCollectCommodityResponseBean;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.ImageLoaderUtil;
import com.app.sifanggou.utils.PreManager;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2018/1/1.
 */

public class ProductDetailActivity extends BaseActivity {
    @ViewInject(R.id.tv_name)
    private TextView tvName;
    @ViewInject(R.id.tv_brand)
    private TextView tvBrand;
    @ViewInject(R.id.tv_sale_num)
    private TextView tvSaleNum;
    @ViewInject(R.id.tv_price)
    private TextView tvPrice;
    @ViewInject(R.id.tv_shoucang)
    private TextView tvShouCang;
    @ViewInject(R.id.tv_shoucang_status)
    private TextView tvShouCangStatus;
    @ViewInject(R.id.iv_save)
    private ImageView ivSave;
    @ViewInject(R.id.tv_time)
    private TextView tvTime;
    @ViewInject(R.id.iv_pic)
    private ImageView ivPic;
    @ViewInject(R.id.tv_dianpu)
    private TextView tvDianPu;
    @ViewInject(R.id.tv_dianpu_data)
    private TextView tvDianPuData;
    @ViewInject(R.id.btn_dianpu)
    private Button btnDianPu;
    @ViewInject(R.id.tv_kucun)
    private TextView tvKuCun;
    @ViewInject(R.id.tv_chandi)
    private TextView tvChanDi;
    @ViewInject(R.id.tv_guige)
    private TextView tvGuiGe;
    @ViewInject(R.id.tv_qualitylevel)
    private TextView tvQualityLevel;
    @ViewInject(R.id.tv_type)
    private TextView tvType;

    @ViewInject(R.id.tv_level)
    private TextView tvLevel;
    @ViewInject(R.id.tv_dec)
    private TextView tvDec;
    @ViewInject(R.id.tv_jiarugouwuche)
    private TextView tvJiaRuGouWuChe;
    @ViewInject(R.id.ll_chat)
    private LinearLayout llChat;
    @ViewInject(R.id.ll_shoucang)
    private LinearLayout llShouCang;
    @ViewInject(R.id.ll_dianpu)
    private LinearLayout llDianPu;
    @ViewInject(R.id.tv_jian)
    private TextView tvJian;
    @ViewInject(R.id.edt_count)
    private EditText edtCount;
    @ViewInject(R.id.tv_add)
    private TextView tvAdd;

    private int selectCount = 0;
    private ShouCangType shouCangType = ShouCangType.NOSAVE;

    private LoginResponseBean loginBean;
    public static final String KEY_DATA = "key_ProductDetailActivity_data";
    public static final String KEY_ID = "key_ProductDetailActivity_id";
    private CommodityInfoBean commodityInfoBean;
    private String commodity_id = "";
    private BusinessInfoBean businessInfoBean;

    private ViewPager mViewPager;
    private GuangGaoPagerAdapter adapter;
    private List<View> viewList = new ArrayList<View>();

    private List<String> urlList = new ArrayList<String>();
    private LinearLayout dotLayout;
    private ImageView[] dotViews;
    private int autoIndex = 0; // 自动轮询时自增坐标，能确定导航圆点的位置
    private int imgsize = 0;
    private boolean isLoop = false;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // 要做的事情
            if (mViewPager.getChildCount() > 0) {
                handler.postDelayed(this, AppContext.GUANGGAOTIME);
                autoIndex++;
                mViewPager.setCurrentItem(autoIndex % imgsize, true);
            }
        }
    };

    public void startLoopViewPager() {
        if (!isLoop && mViewPager != null) {
            handler.postDelayed(runnable, AppContext.GUANGGAOTIME);// 每两秒执行一次runnable.
            isLoop = true;
        }

    }

    public void stopLoopViewPager() {
        if (isLoop && mViewPager != null) {
            handler.removeCallbacks(runnable);
            isLoop = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
        startLoopViewPager();
        mViewPager.setCurrentItem(0);
        refreshShouCang();
    }

    private void refreshShouCang(){
        if (shouCangType == ShouCangType.NOSAVE) {
            tvShouCangStatus.setText("收藏");
            ivSave.setImageResource(R.drawable.icon_product_shoucang);
            llShouCang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commodityInfoBean != null && loginBean != null) {
                        String business_code = loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code();
                        String user_name = loginBean.getData().getLogin_info().getBusiness_info().getMobile();
                        String trans_no = System.currentTimeMillis()+"";
                        String sign = CommonUtils.getSign(business_code,user_name,trans_no,PreManager.getString(getApplicationContext(),AppContext.USER_PWD));
                        String commodity_id = commodityInfoBean.getCommodity_id();
                        pushEventNoProgress(EventCode.HTTP_ADDBUSINESSCOMMODITYCOLLECT,business_code,user_name,trans_no,sign,commodity_id);
                    }
                }
            });
        } else {
            tvShouCangStatus.setText("取消收藏");
            ivSave.setImageResource(R.drawable.icon_star_saved);
            llShouCang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commodityInfoBean != null && loginBean != null) {
                        String business_code = loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code();
                        String user_name = loginBean.getData().getLogin_info().getBusiness_info().getMobile();
                        String trans_no = System.currentTimeMillis()+"";
                        String sign = CommonUtils.getSign(business_code,user_name,trans_no,PreManager.getString(getApplicationContext(),AppContext.USER_PWD));
                        String commodity_id = commodityInfoBean.getCommodity_id();
                        pushEventNoProgress(EventCode.HTTP_DELBUSINESSCOMMODITYCOLLECT,business_code,user_name,trans_no,sign,commodity_id);
                    }
                }
            });
        }
    }

    private void initData() {
        loginBean = PreManager.get(getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
        commodityInfoBean = (CommodityInfoBean) getIntent().getSerializableExtra(KEY_DATA);
        commodity_id = getIntent().getStringExtra(KEY_ID);
        refreshShouCangData();
        refreshCommodityInfo();
//        if (commodityInfoBean != null) {
//            if (!TextUtils.isEmpty(commodityInfoBean.getCommodity_name())) {
//                tvName.setText(commodityInfoBean.getCommodity_name());
//            }
//
//            if (!TextUtils.isEmpty(commodityInfoBean.getBrand_name())) {
//                tvBrand.setText(commodityInfoBean.getBrand_name());
//            }
//
//            if (!TextUtils.isEmpty(commodityInfoBean.getCommodity_pic_url())) {
//                String dataArray[] = commodityInfoBean.getCommodity_pic_url().split(",");
//                List<String> dataList = new ArrayList<String>();
//                for (int i = 0 ; i<dataArray.length ; i++) {
//                    dataList.add(dataArray[i]);
//                }
//                refreshPic(dataList);
//            }
//
//            if (!TextUtils.isEmpty(commodityInfoBean.getSale_num())) {
//                tvSaleNum.setText("销量  "+commodityInfoBean.getSale_num());
//            }
//
//            if (!TextUtils.isEmpty(commodityInfoBean.getA_price())) {
//                float price = Float.valueOf(commodityInfoBean.getA_price()) / 100;
//                tvPrice.setText("￥"+price + "");
//            }
//
//            if (!TextUtils.isEmpty(commodityInfoBean.getCollection_num())) {
//                tvShouCang.setText("收藏  "+commodityInfoBean.getCollection_num()+"人");
//            }
//
//            if (!TextUtils.isEmpty(commodityInfoBean.getAdd_time())) {
//                tvTime.setText(commodityInfoBean.getAdd_time());
//            }
//
//            if (!TextUtils.isEmpty(commodityInfoBean.getProduction_place())) {
//                tvChanDi.setText(commodityInfoBean.getProduction_place());
//            }
//
//            if (!TextUtils.isEmpty(commodityInfoBean.getSpecification())) {
//                tvGuiGe.setText(commodityInfoBean.getSpecification());
//            }
//
//            if (!TextUtils.isEmpty(commodityInfoBean.getSpecification())) {
//                tvGuiGe.setText(commodityInfoBean.getSpecification());
//            }
//
//            if (!TextUtils.isEmpty(commodityInfoBean.getQuality_grade())) {
//                tvQualityLevel.setText(commodityInfoBean.getQuality_grade());
//            }
//
//            if (!TextUtils.isEmpty(commodityInfoBean.getFirst_level_category_name())) {
//                tvType.setText(commodityInfoBean.getFirst_level_category_name());
//            }
//
//            if (!TextUtils.isEmpty(commodityInfoBean.getAgent_level())) {
//                tvLevel.setText(commodityInfoBean.getAgent_level());
//            }
//
//            if (!TextUtils.isEmpty(commodityInfoBean.getAgent_level())) {
//                tvLevel.setText(commodityInfoBean.getAgent_level());
//            }
//
//            if (!TextUtils.isEmpty(commodityInfoBean.getIntro())) {
//                tvDec.setText(commodityInfoBean.getIntro());
//            }
//
//            if (commodityInfoBean.getBusiness_info() != null) {
//
//                if (!TextUtils.isEmpty(commodityInfoBean.getBusiness_info().getHead_pic_url())) {
//                    ImageLoaderUtil.display(commodityInfoBean.getBusiness_info().getHead_pic_url(),ivPic);
//                }
//
//                if (!TextUtils.isEmpty(commodityInfoBean.getBusiness_info().getName())) {
//                    tvDianPu.setText(commodityInfoBean.getBusiness_info().getName());
//                }
//
//            }
//
//        }
    }

    private void refreshCommondityView(CommodityInfoBean infoBean) {
        if (infoBean == null) return;
        commodityInfoBean = infoBean;
        if (!TextUtils.isEmpty(infoBean.getCommodity_name())) {
            tvName.setText(infoBean.getCommodity_name());
        }

        if (!TextUtils.isEmpty(infoBean.getBrand_name())) {
            tvBrand.setText(infoBean.getBrand_name());
        }

        if (!TextUtils.isEmpty(infoBean.getCommodity_pic_url())) {
            String dataArray[] = infoBean.getCommodity_pic_url().split(",");
            List<String> dataList = new ArrayList<String>();
            for (int i = 0 ; i<dataArray.length ; i++) {
                dataList.add(dataArray[i]);
            }
            refreshPic(dataList);
        }

        if (!TextUtils.isEmpty(infoBean.getSale_num())) {
            tvSaleNum.setText("销量  "+infoBean.getSale_num());
        }

        if (!TextUtils.isEmpty(infoBean.getA_price())) {
            float price = Float.valueOf(infoBean.getA_price()) / 100;
            tvPrice.setText("￥"+price + "");
        }

        if (!TextUtils.isEmpty(infoBean.getCollection_num())) {
            tvShouCang.setText("收藏  "+infoBean.getCollection_num()+"人");
        }

        if (!TextUtils.isEmpty(infoBean.getProduction_place())) {
            tvChanDi.setText(infoBean.getProduction_place());
        }

        if (!TextUtils.isEmpty(infoBean.getSpecification())) {
            tvGuiGe.setText(infoBean.getSpecification());
        }

        if (!TextUtils.isEmpty(infoBean.getQuality_grade())) {
            tvQualityLevel.setText(infoBean.getQuality_grade());
        }

        if (!TextUtils.isEmpty(infoBean.getFirst_level_category_name())) {
            tvType.setText(infoBean.getFirst_level_category_name());
        }

        if (!TextUtils.isEmpty(infoBean.getAgent_level())) {
            for (AgentLevelType alt : AgentLevelType.values()) {
                if (alt.getType().equals(infoBean.getAgent_level())) {
                    tvLevel.setText(alt.getName());
                }
            }
        } else {
            tvLevel.setText("无");
        }

        if (!TextUtils.isEmpty(infoBean.getIntro())) {
            tvDec.setText(infoBean.getIntro());
        }
    }

    private void refreshBusinessinfo(BusinessInfoBean infoBean) {
        if (infoBean == null) return;
        businessInfoBean = infoBean;
        if (!TextUtils.isEmpty(infoBean.getHead_pic_url())) {
            ImageLoaderUtil.display(infoBean.getHead_pic_url(),ivPic);
        }
        if (!TextUtils.isEmpty(infoBean.getTotal_commodity_num())
                && !TextUtils.isEmpty(infoBean.getTotal_sale_num())) {
            tvDianPuData.setText("总销量:  "+infoBean.getTotal_sale_num()+"  全部产品："+infoBean.getTotal_commodity_num());
        }
        if (!TextUtils.isEmpty(infoBean.getName())) {
            if (!TextUtils.isEmpty(infoBean.getMarket_name())) {
                tvDianPu.setText(infoBean.getName() + infoBean.getMarket_name() + infoBean.getShop_number());
            }
        }

        if (!TextUtils.isEmpty(infoBean.getMarket_name())) {
            tvTime.setText("市场："+infoBean.getMarket_name());
        }

    }

    private void refreshShouCangData() {
        if (loginBean != null && commodityInfoBean != null) {
            pushEventNoProgress(EventCode.HTTP_ISBUSINESSCOLLECTCOMMODITY,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),commodityInfoBean.getCommodity_id());
        }
    }

    private void refreshCommodityInfo() {
        if (commodityInfoBean != null) {
            pushEventNoProgress(EventCode.HTTP_GETCOMMODITYINFOBYID,commodityInfoBean.getCommodity_id());
        } else if (!TextUtils.isEmpty(commodity_id)){
            pushEventNoProgress(EventCode.HTTP_GETCOMMODITYINFOBYID,commodity_id);
        }
    }

    private void initListener() {
        btnDianPu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (businessInfoBean != null) {
                    Intent intent = new Intent(ProductDetailActivity.this,DianPuDetailActivity.class);
                    intent.putExtra(DianPuDetailActivity.KEY_DATA,businessInfoBean);
                    startActivity(intent);
                }
            }
        });

        llChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (businessInfoBean != null) {
                    pushEvent(EventCode.HTTP_GETBUSINESSRONGYUNTOKEN,businessInfoBean.getBusiness_code());
                }
            }
        });

        llDianPu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (businessInfoBean != null) {
                    Intent intent = new Intent(ProductDetailActivity.this,DianPuDetailActivity.class);
                    intent.putExtra(DianPuDetailActivity.KEY_DATA,businessInfoBean);
                    startActivity(intent);
                }
            }
        });

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCount = selectCount + 1;
                edtCount.setText(selectCount + "");
            }
        });

        tvJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectCount != 0) {
                    selectCount = selectCount - 1;
                    edtCount.setText(selectCount + "");
                }
            }
        });

        tvJiaRuGouWuChe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.valueOf(edtCount.getText().toString());
                if (count <= 0) {
                    CommonUtils.showToast("选择商品数量");
                    return;
                }
                if (commodityInfoBean != null ) {
                    carAdd(commodityInfoBean,count);
                }
            }
        });

    }

    private void carAdd(CommodityInfoBean bean,int count) {
        if (loginBean != null && loginBean.getData() != null && loginBean.getData().getLogin_info() != null && loginBean.getData().getLogin_info().getBusiness_info() != null) {
            pushEventNoProgress(EventCode.HTTP_ADDBUSINESSSHOPPINGCART,loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code(),bean.getCommodity_id(),count+"");
        }
    }

    private void initView() {
        addBack(R.id.rl_back);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_guanggao);
        adapter = new GuangGaoPagerAdapter(viewList);
        mViewPager.setAdapter(adapter);

        dotLayout = (LinearLayout) findViewById(R.id.ly_dot);

        edtCount.setText(selectCount + "");
    }

    private  void refreshPic(List<String> picList) {
        if (picList == null) {
            return;
        }
        urlList.clear();
        urlList.addAll(picList);

        imgsize = urlList.size();
        initDots(dotLayout, urlList);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        viewList.clear();
        for(int i=0; i<urlList.size() ; i++) {
            String url = urlList.get(i);
            ImageView iv = new ImageView(ProductDetailActivity.this);
            iv.setLayoutParams(param);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageLoaderUtil.displayWithCache(url, iv);
            viewList.add(iv);
            iv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProductDetailActivity.this,UrlWebClientActivity.class);
                    intent.putExtra(UrlWebClientActivity.KEY_TITILE, "图片详情");
                    intent.putExtra(UrlWebClientActivity.KEY_URL, url);
                    startActivity(intent);
                }
            });
        }
        adapter.notifyDataSetChanged();
    }

    private void initDots(LinearLayout layout,List<String> list) {
        if (list.size() > 0) {
            LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(CommonUtils.dip2px(ProductDetailActivity.this, 5), CommonUtils.dip2px(ProductDetailActivity.this, 5));
            mParams.setMargins(CommonUtils.dip2px(ProductDetailActivity.this, 3), 0, CommonUtils.dip2px(ProductDetailActivity.this, 3), CommonUtils.dip2px(ProductDetailActivity.this, 3));// 设置小圆点左右之间的间隔

            dotViews = new ImageView[list.size()];
            layout.removeAllViews();

            for (int i = 0; i < list.size(); i++) {
                ImageView imageView = new ImageView(ProductDetailActivity.this);
                imageView.setLayoutParams(mParams);
                imageView.setImageResource(R.drawable.icon_set_white);
                if (i == 0) {
                    imageView.setSelected(true);// 默认启动时，选中第一个小圆点
                } else {
                    imageView.setSelected(false);
                }
                dotViews[i] = imageView;// 得到每个小圆点的引用，用于滑动页面时，（onPageSelected方法中）更改它们的状态。
                layout.addView(imageView);// 添加到布局里面显示
            }
        }
    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_GETCOMMODITYINFOBYID) {
            if (event.isSuccess()) {
                GetCommodityInfoByIdResponseBean bean = (GetCommodityInfoByIdResponseBean) event.getReturnParamAtIndex(0);
                if (bean != null && bean.getData() != null && bean.getData().getCommodity_info() != null) {
                    refreshCommondityView(bean.getData().getCommodity_info());
                }
                if (bean != null && bean.getData() != null && bean.getData().getBusiness_info() != null) {
                    refreshBusinessinfo(bean.getData().getBusiness_info());
                }
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
        if (event.getEventCode() == EventCode.HTTP_ISBUSINESSCOLLECTCOMMODITY) {
            if (event.isSuccess()) {
                IsBusinessCollectCommodityResponseBean bean = (IsBusinessCollectCommodityResponseBean) event.getReturnParamAtIndex(0);
                if (bean != null && bean.getData() != null && !TextUtils.isEmpty(bean.getData().getIs_collect())) {
                    for (ShouCangType type : ShouCangType.values()) {
                        if (type.getType().equals(bean.getData().getIs_collect())) {
                            shouCangType = type;
                            refreshShouCang();
                        }
                    }

                }
            } else {

            }
        }
        if (event.getEventCode() == EventCode.HTTP_ADDBUSINESSSHOPPINGCART) {
            if (event.isSuccess()) {
                Intent intent = new Intent(ProductDetailActivity.this,CarActivity.class);
                startActivity(intent);
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
        if (event.getEventCode() == EventCode.HTTP_ADDBUSINESSCOMMODITYCOLLECT) {
            if (event.isSuccess()) {
                CommonUtils.showToast("收藏成功");
                refreshShouCangData();
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }

        if (event.getEventCode() == EventCode.HTTP_DELBUSINESSCOMMODITYCOLLECT) {
            if (event.isSuccess()) {
                CommonUtils.showToast("取消收藏成功");
                refreshShouCangData();
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }

        if (event.getEventCode() == EventCode.HTTP_GETBUSINESSRONGYUNTOKEN) {
            if (event.isSuccess()) {
                GetBusinessRongYunTokenResponseBean bean = (GetBusinessRongYunTokenResponseBean) event.getReturnParamAtIndex(0);
                if (bean != null
                        && bean.getData() != null
                        && !TextUtils.isEmpty(bean.getData().getToken())) {
                    if (businessInfoBean != null) {
                        RongIM.getInstance().startPrivateChat(ProductDetailActivity.this, businessInfoBean.getBusiness_code(), businessInfoBean.getName());
                    }
                }
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }

    }
}
