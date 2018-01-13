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
import com.app.sifanggou.bean.CommodityInfoBean;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.GetBusinessRongYunTokenResponseBean;
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
    @ViewInject(R.id.edt_count)
    private EditText edtCount;
    @ViewInject(R.id.tv_shoucang)
    private TextView tvShouCang;
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
    @ViewInject(R.id.tv_xiadan)
    private TextView tvXiaDan;
    @ViewInject(R.id.ll_chat)
    private LinearLayout llChat;
    @ViewInject(R.id.ll_shoucang)
    private LinearLayout llShouCang;
    @ViewInject(R.id.ll_dianpu)
    private LinearLayout llDianPu;


    private LoginResponseBean loginBean;
    public static final String KEY_DATA = "key_ProductDetailActivity_data";
    private CommodityInfoBean commodityInfoBean;

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
    }

    private void initData() {
        loginBean = PreManager.get(getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
        commodityInfoBean = (CommodityInfoBean) getIntent().getSerializableExtra(KEY_DATA);
        if (commodityInfoBean != null) {
            if (!TextUtils.isEmpty(commodityInfoBean.getCommodity_name())) {
                tvName.setText(commodityInfoBean.getCommodity_name());
            }

            if (!TextUtils.isEmpty(commodityInfoBean.getBrand_name())) {
                tvBrand.setText(commodityInfoBean.getBrand_name());
            }

            if (!TextUtils.isEmpty(commodityInfoBean.getCommodity_pic_url())) {
                String dataArray[] = commodityInfoBean.getCommodity_pic_url().split(",");
                List<String> dataList = new ArrayList<String>();
                for (int i = 0 ; i<dataArray.length ; i++) {
                    dataList.add(dataArray[i]);
                }
                refreshPic(dataList);
            }

            if (!TextUtils.isEmpty(commodityInfoBean.getSale_num())) {
                tvSaleNum.setText("销量  "+commodityInfoBean.getSale_num());
            }

            if (!TextUtils.isEmpty(commodityInfoBean.getA_price())) {
                float price = Float.valueOf(commodityInfoBean.getA_price()) / 100;
                tvPrice.setText("￥"+price + "");
            }

            if (!TextUtils.isEmpty(commodityInfoBean.getCollection_num())) {
                tvShouCang.setText("收藏  "+commodityInfoBean.getCollection_num()+"人");
            }

            if (!TextUtils.isEmpty(commodityInfoBean.getAdd_time())) {
                tvTime.setText(commodityInfoBean.getAdd_time());
            }

            if (!TextUtils.isEmpty(commodityInfoBean.getProduction_place())) {
                tvChanDi.setText(commodityInfoBean.getProduction_place());
            }

            if (!TextUtils.isEmpty(commodityInfoBean.getSpecification())) {
                tvGuiGe.setText(commodityInfoBean.getSpecification());
            }

            if (!TextUtils.isEmpty(commodityInfoBean.getSpecification())) {
                tvGuiGe.setText(commodityInfoBean.getSpecification());
            }

            if (!TextUtils.isEmpty(commodityInfoBean.getQuality_grade())) {
                tvQualityLevel.setText(commodityInfoBean.getQuality_grade());
            }

            if (!TextUtils.isEmpty(commodityInfoBean.getFirst_level_category_name())) {
                tvType.setText(commodityInfoBean.getFirst_level_category_name());
            }

            if (!TextUtils.isEmpty(commodityInfoBean.getAgent_level())) {
                tvLevel.setText(commodityInfoBean.getAgent_level());
            }

            if (!TextUtils.isEmpty(commodityInfoBean.getAgent_level())) {
                tvLevel.setText(commodityInfoBean.getAgent_level());
            }

            if (!TextUtils.isEmpty(commodityInfoBean.getIntro())) {
                tvDec.setText(commodityInfoBean.getIntro());
            }

            if (commodityInfoBean.getBusiness_info() != null) {

                if (!TextUtils.isEmpty(commodityInfoBean.getBusiness_info().getHead_pic_url())) {
                    ImageLoaderUtil.display(commodityInfoBean.getBusiness_info().getHead_pic_url(),ivPic);
                }

                if (!TextUtils.isEmpty(commodityInfoBean.getBusiness_info().getName())) {
                    tvDianPu.setText(commodityInfoBean.getBusiness_info().getName());
                }

            }

        }
    }

    private void initListener() {
        btnDianPu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commodityInfoBean != null && commodityInfoBean.getBusiness_info() != null) {
                    Intent intent = new Intent(ProductDetailActivity.this,DianPuDetailActivity.class);
                    intent.putExtra(DianPuDetailActivity.KEY_DATA,commodityInfoBean.getBusiness_info());
                    startActivity(intent);
                }
            }
        });

        llChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commodityInfoBean != null && commodityInfoBean.getBusiness_info() != null) {
                    pushEvent(EventCode.HTTP_GETBUSINESSRONGYUNTOKEN,commodityInfoBean.getBusiness_code());
                }
            }
        });

        llDianPu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commodityInfoBean != null && commodityInfoBean.getBusiness_info() != null) {
                    Intent intent = new Intent(ProductDetailActivity.this,DianPuDetailActivity.class);
                    intent.putExtra(DianPuDetailActivity.KEY_DATA,commodityInfoBean.getBusiness_info());
                    startActivity(intent);
                }
            }
        });

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

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager_guanggao);
        adapter = new GuangGaoPagerAdapter(viewList);
        mViewPager.setAdapter(adapter);

        dotLayout = (LinearLayout) findViewById(R.id.ly_dot);
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
        if (event.getEventCode() == EventCode.HTTP_ADDBUSINESSCOMMODITYCOLLECT) {
            if (event.isSuccess()) {
                CommonUtils.showToast("收藏成功");
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
                    if (commodityInfoBean != null && commodityInfoBean.getBusiness_info() != null) {
                        RongIM.getInstance().startPrivateChat(ProductDetailActivity.this, commodityInfoBean.getBusiness_code(), commodityInfoBean.getBusiness_info().getName());
                    }
                }
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }

    }
}
