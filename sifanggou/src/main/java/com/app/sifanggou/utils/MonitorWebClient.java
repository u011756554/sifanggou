package com.app.sifanggou.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MonitorWebClient extends WebViewClient{
	private WebView webView;
	private Activity activity;
	public MonitorWebClient(WebView webView,Activity activity){
		this.webView = webView;
		this.activity = activity;
	}
	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
		// TODO Auto-generated method stub
		Log.i(getClass().getName(), description);
		try{
			webView.stopLoading();
			webView.clearView();
		}catch(Exception e){
			
		}
		if(webView.canGoBack()){
			webView.goBack();
		}
	}
	
	@Override
	public void onReceivedSslError(WebView view, SslErrorHandler handler,
			SslError error) {
		// TODO Auto-generated method stub
		handler.proceed();
	}
	
	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		// TODO Auto-generated method stub
		super.onPageStarted(view, url, favicon);
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		// TODO Auto-generated method stub
		super.onPageFinished(view, url);
	}
	
	@Override
	public boolean shouldOverrideUrlLoading(final WebView view, String url) {
		// TODO Auto-generated method stub
        return super.shouldOverrideUrlLoading(view, url); 
	}
}
