package com.app.sifanggou.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.app.sifanggou.R;
import com.app.sifanggou.fragment.BaseFragment;
import com.app.sifanggou.fragment.DianPuListFrament;
import com.app.sifanggou.fragment.ProductListFragment;
import com.app.sifanggou.listener.PageSelectListener;
import com.app.sifanggou.view.tab.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/1/6.
 */

public class ShouCangActivity extends BaseActivity implements PageSelectListener {

    private ViewPager myViewPager;
    private ViewPagerIndicator mIndicator;
    private List<String> mTitle= Arrays.asList("商品","店铺");
    private List<BaseFragment> mContents=new ArrayList<BaseFragment>();
    private FragmentPagerAdapter mAdapter;

    private ProductListFragment productListFragment;
    private DianPuListFrament dianPuListFrament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initDatas();
        initListener();
    }

    //初始化界面
    private void initView() {
        addBack(R.id.rl_back);
        setTitle("我的关注");

        myViewPager=(ViewPager)findViewById(R.id.myViewPager);
        mIndicator=(ViewPagerIndicator)findViewById(R.id.mIndicator);
    }

    private void initDatas() {
        productListFragment = new ProductListFragment();
        dianPuListFrament = new DianPuListFrament();

        mContents.add(productListFragment);
        mContents.add(dianPuListFrament);
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

    private void initListener() {
        myViewPager.setAdapter(mAdapter);
        mIndicator.setmTabVisibleCount(2);
        mIndicator.setTabTitles(mTitle);

        mIndicator.setViewPager(myViewPager,0,ShouCangActivity.this);
    }

    @Override
    public void pageSelect(int postion) {

    }
}
