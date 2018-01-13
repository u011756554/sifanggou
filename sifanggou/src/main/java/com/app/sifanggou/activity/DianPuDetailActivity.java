package com.app.sifanggou.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sifanggou.AppContext;
import com.app.sifanggou.R;
import com.app.sifanggou.bean.AgentLevelType;
import com.app.sifanggou.bean.BusinessInfoBean;
import com.app.sifanggou.fragment.AllProductFragment;
import com.app.sifanggou.fragment.BaseFragment;
import com.app.sifanggou.fragment.DaiJieFragment;
import com.app.sifanggou.fragment.DaiLiProductFragment;
import com.app.sifanggou.fragment.JieKuanRecordFragment;
import com.app.sifanggou.fragment.PuTongProductFragment;
import com.app.sifanggou.fragment.XinPinProductFragment;
import com.app.sifanggou.listener.PageSelectListener;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.net.bean.LoginResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.utils.ImageLoaderUtil;
import com.app.sifanggou.utils.PreManager;
import com.app.sifanggou.view.tab.ViewPagerIndicator;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class DianPuDetailActivity extends BaseActivity implements PageSelectListener {
    @ViewInject(R.id.tv_name)
    private TextView tvName;
    @ViewInject(R.id.tv_level)
    private TextView tvLevel;
    @ViewInject(R.id.tv_rezheng)
    private TextView tvRenZheng;
    @ViewInject(R.id.tv_save)
    private TextView tvSave;
    @ViewInject(R.id.tv_huodong)
    private TextView tvHuoDong;
    @ViewInject(R.id.iv_head)
    private ImageView ivHead;
    @ViewInject(R.id.rl_huodong)
    private RelativeLayout rlHuoDong;
    @ViewInject(R.id.ll_save)
    private LinearLayout llSave;

    private ViewPager myViewPager;
    private ViewPagerIndicator mIndicator;
    private List<String> mTitle= Arrays.asList("全部","普通","代理/直销","新品");
    private List<BaseFragment> mContents=new ArrayList<BaseFragment>();
    private FragmentPagerAdapter mAdapter;

    private AllProductFragment allProductFragment;
    private PuTongProductFragment puTongProductFragment;
    private DaiLiProductFragment daiLiProductFragment;
    private XinPinProductFragment xinPinProductFragment;

    public static final String KEY_DATA = "key_DianPuDetailActivity_data";
    private BusinessInfoBean dataBean;
    private LoginResponseBean loginBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initDatas();
        initListener();
    }

    //初始化数据
    private void initDatas() {
        loginBean = PreManager.get(getApplicationContext(), AppContext.USER_LOGIN,LoginResponseBean.class);
        allProductFragment = new AllProductFragment();
        puTongProductFragment = new PuTongProductFragment();
        daiLiProductFragment = new DaiLiProductFragment();
        xinPinProductFragment = new XinPinProductFragment();
        mContents.add(allProductFragment);
        mContents.add(puTongProductFragment);
        mContents.add(daiLiProductFragment);
        mContents.add(xinPinProductFragment);

        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }

            @Override
            public int getCount() {
                return mContents.size();
            }
        };

        dataBean = (BusinessInfoBean) getIntent().getSerializableExtra(KEY_DATA);
        refreshView(dataBean);
    }

    private void refreshView(BusinessInfoBean bean) {
        if (bean == null) {return;}
        tvName.setText(bean.getName());
        for(int i = 0 ; i < AgentLevelType.values().length ; i++) {
            if (bean.getAgent_level().equals(AgentLevelType.values()[i].getType())) {
                tvLevel.setText(AgentLevelType.values()[i].getCode());
                break;
            }
        }
        if (!TextUtils.isEmpty(bean.getHead_pic_url())) {
            ImageLoaderUtil.display(bean.getHead_pic_url(),ivHead);
        }
    }

    //初始化界面
    private void initView() {
        addBack(R.id.rl_back);

        myViewPager=(ViewPager)findViewById(R.id.myViewPager);
        mIndicator=(ViewPagerIndicator)findViewById(R.id.mIndicator);
    }

    private void initListener() {
        myViewPager.setAdapter(mAdapter);
        mIndicator.setmTabVisibleCount(4);
        mIndicator.setTabTitles(mTitle);

        mIndicator.setViewPager(myViewPager,0,DianPuDetailActivity.this);

        llSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataBean != null && loginBean != null) {
                    String business_code = loginBean.getData().getLogin_info().getBusiness_info().getBusiness_code();
                    String user_name = loginBean.getData().getLogin_info().getBusiness_info().getMobile();
                    String trans_no = System.currentTimeMillis()+"";
                    String sign = CommonUtils.getSign(business_code,user_name,trans_no, PreManager.getString(getApplicationContext(), AppContext.USER_PWD));
                    String partner_business_code = dataBean.getBusiness_code();
                    pushEventNoProgress(EventCode.HTTP_ADDBUSINESSPARTNER,business_code,user_name,trans_no,sign,partner_business_code);
                }
            }
        });

    }

    @Override
    public void pageSelect(int postion) {

    }

    @Override
    public void onEventRunEnd(Event event) {
        super.onEventRunEnd(event);
        if (event.getEventCode() == EventCode.HTTP_ADDBUSINESSPARTNER) {
            if (event.isSuccess()) {
                CommonUtils.showToast("关注成功");
            } else {
                CommonUtils.showToast(event.getFailMessage());
            }
        }
    }
}
