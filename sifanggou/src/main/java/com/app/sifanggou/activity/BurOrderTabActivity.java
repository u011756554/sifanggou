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
import com.app.sifanggou.fragment.DaiJieFragment;
import com.app.sifanggou.fragment.JieKuanRecordFragment;
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

public class BurOrderTabActivity extends BaseActivity implements PageSelectListener {
    @ViewInject(R.id.right_layout)
    private RelativeLayout rightLayout;

    private ViewPager myViewPager;
    private ViewPagerIndicator mIndicator;
    private List<String> mTitle= Arrays.asList("待结账单","结款记录");
    private List<BaseFragment> mContents=new ArrayList<BaseFragment>();
    private FragmentPagerAdapter mAdapter;

    private DaiJieFragment daiJieFragment;
    private JieKuanRecordFragment jieKuanRecordFragment;

    public static final String KEY_TYPE = "key_BurOrderTabActivity_type";
    public static final String VALUE_TYPE_DAIJIE = "daijie";

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
        daiJieFragment = new DaiJieFragment();
        jieKuanRecordFragment = new JieKuanRecordFragment();
        mContents.add(daiJieFragment);
        mContents.add(jieKuanRecordFragment);

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
        setTitle("买进");

        myViewPager=(ViewPager)findViewById(R.id.myViewPager);
        mIndicator=(ViewPagerIndicator)findViewById(R.id.mIndicator);
    }

    private void initListener() {
        myViewPager.setAdapter(mAdapter);
        mIndicator.setmTabVisibleCount(2);
        mIndicator.setTabTitles(mTitle);

        String type = getIntent().getStringExtra(KEY_TYPE);
        if (VALUE_TYPE_DAIJIE.equals(type)) {
            mIndicator.setViewPager(myViewPager,0,BurOrderTabActivity.this);
        } else {
            mIndicator.setViewPager(myViewPager,1,BurOrderTabActivity.this);
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
        timePopupWindow = new PopupWindow(BurOrderTabActivity.this);
        timePopupWindow.setWidth(CommonUtils.dip2px(getApplicationContext(),107));
        timePopupWindow.setHeight(CommonUtils.dip2px(getApplicationContext(),183));
        View contentView = LayoutInflater.from(BurOrderTabActivity.this).inflate(R.layout.popupwindow_time,null);
        ListView lvTime = contentView.findViewById(R.id.lv_time);
        TimeAdapter timeAdapter = new TimeAdapter(BurOrderTabActivity.this,CommonUtils.getDateList());
        lvTime.setAdapter(timeAdapter);
        lvTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DateBean dateBean = (DateBean) timeAdapter.getItem(position);
                if (dateBean != null && jieKuanRecordFragment != null) {
                    jieKuanRecordFragment.chooseTime(dateBean.getYear(),dateBean.getMonth());
                }
                timePopupWindow.dismiss();
            }
        });
        timePopupWindow.setContentView(contentView);
        timePopupWindow.setOutsideTouchable(false);
        timePopupWindow.setFocusable(true);
    }

}
