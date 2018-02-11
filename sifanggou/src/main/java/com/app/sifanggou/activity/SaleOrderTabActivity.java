package com.app.sifanggou.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.sifanggou.R;
import com.app.sifanggou.adapter.TimeAdapter;
import com.app.sifanggou.bean.DateBean;
import com.app.sifanggou.fragment.BaseFragment;
import com.app.sifanggou.fragment.DaiShouFragment;
import com.app.sifanggou.fragment.ShouKuanRecordFragment;
import com.app.sifanggou.listener.PageSelectListener;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.view.tab.ViewPagerIndicator;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class SaleOrderTabActivity extends BaseActivity implements PageSelectListener{
    @ViewInject(R.id.right_layout)
    private RelativeLayout rightLayout;

    private ViewPager myViewPager;
    private ViewPagerIndicator mIndicator;
    private List<String> mTitle= Arrays.asList("待收账单","收款记录");
    private List<BaseFragment> mContents=new ArrayList<BaseFragment>();
    private FragmentPagerAdapter mAdapter;

    private DaiShouFragment daiShouFragment;
    private ShouKuanRecordFragment shouKuanRecordFragment;

    public static final String KEY_TYPE = "key_SaleOrderTabActivity_type";
    public static final String VALUE_TYPE_DAISHOU = "daishou";

    private PopupWindow timePopupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initDatas();
        initListener();
    }

    //初始化数据
    private void initDatas() {
        daiShouFragment = new DaiShouFragment();
        shouKuanRecordFragment = new ShouKuanRecordFragment();
        mContents.add(daiShouFragment);
        mContents.add(shouKuanRecordFragment);

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
    }

    //初始化界面
    private void initView() {
        addBack(R.id.rl_back);
        setTitle("卖出");

        myViewPager=(ViewPager)findViewById(R.id.myViewPager);
        mIndicator=(ViewPagerIndicator)findViewById(R.id.mIndicator);
    }

    private void initListener() {
        myViewPager.setAdapter(mAdapter);
        mIndicator.setmTabVisibleCount(2);
        mIndicator.setTabTitles(mTitle);

        String type = getIntent().getStringExtra(KEY_TYPE);
        if (VALUE_TYPE_DAISHOU.equals(type)) {
            mIndicator.setViewPager(myViewPager,0,SaleOrderTabActivity.this);
        } else {
            mIndicator.setViewPager(myViewPager,1,SaleOrderTabActivity.this);
        }

    }

    @Override
    public void pageSelect(int postion) {
        if (postion == 1) {
            setRightResource(R.drawable.icon_right_date);
            setRightClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (timePopupWindow == null) {
                        initPopupWindow();
                    }
                    timePopupWindow.showAsDropDown(rightLayout);
                }
            });
        } else {
            setRightImageGone();
        }
    }

    private void initPopupWindow() {
        timePopupWindow = new PopupWindow(SaleOrderTabActivity.this);
        timePopupWindow.setWidth(CommonUtils.dip2px(getApplicationContext(),107));
        timePopupWindow.setHeight(CommonUtils.dip2px(getApplicationContext(),183));
        View contentView = LayoutInflater.from(SaleOrderTabActivity.this).inflate(R.layout.popupwindow_time,null);
        ListView lvTime = contentView.findViewById(R.id.lv_time);
        TimeAdapter timeAdapter = new TimeAdapter(SaleOrderTabActivity.this,CommonUtils.getDateList());
        lvTime.setAdapter(timeAdapter);
        lvTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DateBean dateBean = (DateBean) timeAdapter.getItem(position);
                if (dateBean != null && shouKuanRecordFragment != null) {
                    shouKuanRecordFragment.chooseTime(dateBean.getYear(),dateBean.getMonth());
                }
                timePopupWindow.dismiss();
            }
        });
        timePopupWindow.setContentView(contentView);
        timePopupWindow.setOutsideTouchable(false);
        timePopupWindow.setFocusable(true);
    }
}
