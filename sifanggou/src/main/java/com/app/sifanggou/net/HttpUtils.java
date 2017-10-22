package com.app.sifanggou.net;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.app.sifanggou.utils.CommonUtils;
import com.google.gson.Gson;


public class HttpUtils {

	public static Gson gson = new Gson();
	/**
	 * 网络连接处理
	 * @param url
	 * @param map
	 * @return
	 */
	public static String doPost(String url,HashMap<String,String> map) {
		String result = "";
		System.out.println("url: "+url);
		try{
			HttpPost httpost = new HttpPost(url);
			if (map != null) {
				String json = CommonUtils.toUtf8(gson.toJson(map));
				StringEntity postingString = new StringEntity(json);
				System.out.println("postingString:" + json);
				httpost.setEntity(postingString);
				httpost.setHeader("Content-type", "application/json");
				httpost.setHeader("Accept-Charset","utf-8");
			}

			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpParams params = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, TIMEOUT_CONNECTION);
			HttpConnectionParams.setSoTimeout(params, TIMEOUT_SO);

			HttpResponse response = null;
			response = httpclient.execute(httpost);

			if(isResponseAvailable(response)){
				try {
					result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
					System.out.println("result : "+ result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 网络连接处理
	 * @param url
	 * @param map
	 * @return
	 */
	public static String doPostObject(String url,HashMap<String,Object> map) {
		String result = "";
		System.out.println("url: "+url);
		try{
	        HttpPost httpost = new HttpPost(url);
	        if (map != null) {
				String json = CommonUtils.toUtf8(gson.toJson(map));
				StringEntity postingString = new StringEntity(json);
				System.out.println("postingString:" + json);
				httpost.setEntity(postingString);
				httpost.setHeader("Content-type", "application/json");
				httpost.setHeader("Accept-Charset","utf-8");
			}
	        
	        DefaultHttpClient httpclient = new DefaultHttpClient();
	        HttpParams params = httpclient.getParams();
	        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT_CONNECTION);
	        HttpConnectionParams.setSoTimeout(params, TIMEOUT_SO);
	        
	        HttpResponse response = null;
	        response = httpclient.execute(httpost);
	        
			if(isResponseAvailable(response)){
				try {
					result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
					System.out.println("result : "+ result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String doGetString(String preUrl,HashMap<String, String> map) {
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(preUrl);
        for(String key : map.keySet()) {
        	sbBuffer.append("&").append(key).append("=").append((String)map.get(key));	
        }
		return doGetString(sbBuffer.toString());
	}
	
	public static String doGetString(String strUrl){
		System.out.println("getUrl:"+strUrl);
		String strResult = null;
		HttpResponse response = doConnection(strUrl);
		if(isResponseAvailable(response)){
			try {
				strResult = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
				System.out.print("result:"+strResult);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return strResult;
	}
	
	private static boolean	isResponseAvailable(HttpResponse response){
		if (response == null) {
			return false;
		}
		System.out.print("statusCode;"+response.getStatusLine().getStatusCode());
		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			return true;
		}
		return false;
	}
	
	private static final int TIMEOUT_CONNECTION = 8000;
	private static final int TIMEOUT_SO = 30000;
	
	private static HttpResponse doConnection(String strUrl){
		HttpResponse response = null;
		try {
			final URI uri = new URI(strUrl);
			HttpGet httpGet = new HttpGet(uri);
	        HttpClient httpClient = new DefaultHttpClient();
	        HttpParams params = httpClient.getParams();
	        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT_CONNECTION);
	        HttpConnectionParams.setSoTimeout(params, TIMEOUT_SO);
	        
	        response = httpClient.execute(httpGet);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
}
