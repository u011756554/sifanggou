package com.app.sifanggou.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.app.sifanggou.R;
import com.app.sifanggou.fragment.BaseFragment;
import com.app.sifanggou.fragment.DaiJieFragment;
import com.app.sifanggou.fragment.JieKuanRecordFragment;
import com.app.sifanggou.listener.PageSelectListener;
import com.app.sifanggou.view.tab.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class BurOrderTabActivity extends BaseActivity implements PageSelectListener {

    private ViewPager myViewPager;
    private ViewPagerIndicator mIndicator;
    private List<String> mTitle= Arrays.asList("待结账单","结款记录");
    private List<BaseFragment> mContents=new ArrayList<BaseFragment>();
    private FragmentPagerAdapter mAdapter;

    private DaiJieFragment daiJieFragment;
    private JieKuanRecordFragment jieKuanRecordFragment;

    public static final String KEY_TYPE = "key_BurOrderTabActivity_type";
    public static final String VALUE_TYPE_DAIJIE = "daijie";
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
                    Toast.makeText(BurOrderTabActivity.this,"哈哈",Toast.LENGTH_LONG).show();
                }
            });
        } else {
            setRightImageGone();
        }
    }
}
