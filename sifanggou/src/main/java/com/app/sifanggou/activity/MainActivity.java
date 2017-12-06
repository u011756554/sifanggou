package com.app.sifanggou.activity;

import java.util.ArrayList;

import com.app.sifanggou.R;
import com.app.sifanggou.fragment.BaseFragment;
import com.app.sifanggou.fragment.CarFragment;
import com.app.sifanggou.fragment.MainFragemnt;
import com.app.sifanggou.fragment.DianPuFragment;
import com.app.sifanggou.fragment.MyselfFragment;
import com.app.sifanggou.fragment.CategoryFragment;
import com.app.sifanggou.net.Event;
import com.app.sifanggou.net.EventCode;
import com.app.sifanggou.utils.CommonUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.RadioButton;

public class MainActivity extends BaseActivity {

	@ViewInject(R.id.main_icon)
	private RadioButton rbMain;
	@ViewInject(R.id.category_icon)
	private RadioButton rbCategory;
	@ViewInject(R.id.dianpu_icon)
	private RadioButton rbDianPu;
	@ViewInject(R.id.car_icon)
	private RadioButton rbCar;
	@ViewInject(R.id.me_icon)
	private RadioButton rbMyself;	
	
	@ViewInject(R.id.container)
	private FrameLayout container;
	
	private FragmentManager fragmentManager;
	
	private MainFragemnt mainFragemnt;
	private CategoryFragment categoryFragment;
	private DianPuFragment dianPuFragment;
	private CarFragment carFragment;
	private MyselfFragment myselfFragment;
	
	private ArrayList<BaseFragment> fragments = new ArrayList<BaseFragment>();
	private int currentTabIndex = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initFragment();
		initView();
		getData();
	}
	
	private void initFragment() {
		fragmentManager = getSupportFragmentManager();
		
		mainFragemnt = new MainFragemnt();
		categoryFragment = new CategoryFragment();
		dianPuFragment = new DianPuFragment();
		carFragment = new CarFragment();
		myselfFragment = new MyselfFragment();
		fragments.add(mainFragemnt);
		fragments.add(categoryFragment);
		fragments.add(dianPuFragment);
		fragments.add(carFragment);
		fragments.add(myselfFragment);
		
		FragmentTransaction ft = fragmentManager.beginTransaction();
        for(BaseFragment fragment : fragments) {
            ft.add(R.id.container, fragment);
        }
        ft.commit();
	}
	
	private void initView() {
		rbMain.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pageChanged(0);
			}
		});
		
		rbCategory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pageChanged(1);
			}
		});
		
		rbDianPu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pageChanged(2);
			}
		});
		
		rbCar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pageChanged(3);
			}
		});		
		
		rbMyself.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pageChanged(4);
			}
		});	
		
		rbMain.performClick();
	}	
	
	private void pageChanged(int index) {
		currentTabIndex = index;
		showFragment(index);
		switch (index)
		{
	        case 0:
	        	rbMain.setSelected(true);
	        	rbCategory.setSelected(false);
	        	rbDianPu.setSelected(false);
	        	rbCar.setSelected(false);
	        	rbMyself.setSelected(false);
	            break;
	        case 1:
	        	rbMain.setSelected(false);
	        	rbCategory.setSelected(true);
	        	rbDianPu.setSelected(false);
	        	rbCar.setSelected(false);
	        	rbMyself.setSelected(false);
	            break;
	        case 2:
	        	rbMain.setSelected(false);
	        	rbCategory.setSelected(false);
	        	rbDianPu.setSelected(true);
	        	rbCar.setSelected(false);
	        	rbMyself.setSelected(false);
	            break;
	        case 3:
	        	rbMain.setSelected(false);
	        	rbCategory.setSelected(false);
	        	rbDianPu.setSelected(false);
	        	rbCar.setSelected(true);
	        	rbMyself.setSelected(false);
				if (carFragment != null) {
					carFragment.clickRefresh();
				}
	            break;	 
	        case 4:
	        	rbMain.setSelected(false);
	        	rbCategory.setSelected(false);
	        	rbDianPu.setSelected(false);
	        	rbCar.setSelected(false);
	        	rbMyself.setSelected(true);
	            break;		            
        }
	}
	
    public void showFragment(int position) {
        hideFragment();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.show(fragments.get(position));
        ft.commit();
    }
    
    public void hideFragment() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        for(BaseFragment fragment : fragments) {
            if(fragment != null)
                ft.hide(fragment);
        }
        ft.commit();
    }
    
    private void getData() {
    	pushEvent(EventCode.HTTP_GETPROVINCECITYZONE);
    }
    
    @Override
    public void onEventRunEnd(Event event) {
    	// TODO Auto-generated method stub
    	super.onEventRunEnd(event);
    	if (event.getEventCode() == EventCode.HTTP_GETPROVINCECITYZONE) {
			if (event.isSuccess()) {
				
			} else {
				CommonUtils.showToast(event.getFailMessage());
			}
		}
    }
}
