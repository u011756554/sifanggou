package com.app.sifanggou.activity;

import com.app.sifanggou.R;
import com.app.sifanggou.utils.MonitorWebClient;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UrlWebClientActivity extends BaseActivity {

	@ViewInject(R.id.webview)
	private WebView webView;
	@ViewInject(R.id.rl_back)
	private RelativeLayout rlBack;
	@ViewInject(R.id.tv_title)
	private TextView tvTitle;

	public static final String KEY_URL = "url";
	public static final String KEY_TITILE = "key_UrlWebClientActivity_title";
	private static final String KEY_PRE = "http://";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String title = getIntent().getStringExtra(KEY_TITILE);
		if (!TextUtils.isEmpty(title)) {
			tvTitle.setText(title);			
		}
		String url = getIntent().getStringExtra(KEY_URL);
		url = dealUrl(url);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.addJavascriptInterface(new JsObject(),"QiCha");
		webView.setWebViewClient(new MonitorWebClient(webView,this));
		if (TextUtils.isEmpty(url)) {
			Toast.makeText(this, "链接为空", Toast.LENGTH_LONG).show();
		} else {
			webView.loadUrl(url);			
		}
		
		rlBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (webView.canGoBack()) {
					webView.goBack();
				} else {
					finish();
				}
			}
		});
	}
	
	public String dealUrl(String url) {
		if (!TextUtils.isEmpty(url)) {
			if (!url.startsWith(KEY_PRE)) {
				url = KEY_PRE+url;
			}
		}
		return url;
	}
	
    @Override
    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
        	webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }	
	
    public class JsObject {
    	@JavascriptInterface
    	public void toindex() {
    		Toast.makeText(UrlWebClientActivity.this, "返回首页", Toast.LENGTH_LONG).show();
    	}
    }
}
