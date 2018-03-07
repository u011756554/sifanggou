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
import com.app.sifanggou.net.bean.GetVerInfoResponseBean;
import com.app.sifanggou.utils.CommonUtils;
import com.app.sifanggou.view.BaseDialog;
import com.app.sifanggou.view.VersionUpdateDialog;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
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

	private VersionUpdateDialog versionUpdateDialog;

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
	
	public void pageChanged(int index) {
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
		pushEvent(EventCode.HTTP_GETVERINFO);
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

		if (event.getEventCode() == EventCode.HTTP_GETVERINFO) {
			if (event.isSuccess()) {
				GetVerInfoResponseBean bean = (GetVerInfoResponseBean) event.getReturnParamAtIndex(0);
				if (bean != null
						&& bean.getData() != null
						&& bean.getData().getVer_info() != null) {
					if (!CommonUtils.checkVersionIsSame(bean.getData().getVer_info().getVersion())) {
						if (versionUpdateDialog == null) {
							versionUpdateDialog = new VersionUpdateDialog(MainActivity.this);
						}
						versionUpdateDialog.setData("发现新版本："+bean.getData().getVer_info().getVersion());
						versionUpdateDialog.setListener(new BaseDialog.DialogListener() {

							@Override
							public void update(Object object) {
								if ("true".equals((String)object)) {
									Intent intent = new Intent(Intent.ACTION_VIEW);
									intent.setData(Uri.parse(bean.getData().getVer_info().getDownload_url()));
									startActivity(intent);
								}
							}
						});
						versionUpdateDialog.show();
					}
				}
			} else {
				CommonUtils.showToast(event.getFailMessage());
			}
		}
    }

	private Long backCode = 0l;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if((keyCode == KeyEvent.KEYCODE_BACK)){
			if((System.currentTimeMillis() - backCode < 2000)){
				exitProgram();
			}else{
				backCode = System.currentTimeMillis();
				CommonUtils.showToast("再点一次退出程序");
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exitProgram(){
		Intent intent=new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.startActivity(intent);
	}
}
