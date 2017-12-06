package com.app.sifanggou.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.app.sifanggou.R;
import com.app.sifanggou.fragment.BaseFragment;
import com.app.sifanggou.fragment.DaiShouFragment;
import com.app.sifanggou.fragment.ShouKuanRecordFragment;
import com.app.sifanggou.listener.PageSelectListener;
import com.app.sifanggou.view.tab.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class SaleOrderTabActivity extends BaseActivity implements PageSelectListener{

    private ViewPager myViewPager;
    private ViewPagerIndicator mIndicator;
    private List<String> mTitle= Arrays.asList("待收账单","收款记录");
    private List<BaseFragment> mContents=new ArrayList<BaseFragment>();
    private FragmentPagerAdapter mAdapter;

    private DaiShouFragment daiShouFragment;
    private ShouKuanRecordFragment shouKuanRecordFragment;

    public static final String KEY_TYPE = "key_SaleOrderTabActivity_type";
    public static final String VALUE_TYPE_DAISHOU = "daishou";
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
                    Toast.makeText(SaleOrderTabActivity.this,"哈哈",Toast.LENGTH_LONG).show();
                }
            });
        } else {
            setRightImageGone();
        }
    }
}
