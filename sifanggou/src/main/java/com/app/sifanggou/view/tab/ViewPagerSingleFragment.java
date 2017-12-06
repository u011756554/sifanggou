package com.app.sifanggou.view.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by master on 2016/3/18.
 * ViewPager中单一的fragment
 */
public class ViewPagerSingleFragment extends Fragment{
    private String mTitle;
    public static final String BUNDLE_TITLE="title";

    public static ViewPagerSingleFragment newInstance(String title)
    {
        Bundle bundle=new Bundle();
        bundle.putString(BUNDLE_TITLE,title);
        ViewPagerSingleFragment fragment=new ViewPagerSingleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        if(bundle!=null)
        {
            mTitle=bundle.getString(BUNDLE_TITLE);
        }
        TextView tv=new TextView(getActivity());
        tv.setText(mTitle);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

}
