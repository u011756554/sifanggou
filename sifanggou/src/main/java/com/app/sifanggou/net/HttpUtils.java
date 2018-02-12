package com.app.sifanggou.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//	/**
//	 * 网络连接处理
//	 * @param url
//	 * @param map
//	 * @return
//	 */
//	public static String doPost(String url,HashMap<String,String> map) {
//		String result = "";
//		System.out.println("url: "+url);
//		try{
//			HttpPost httpost = new HttpPost(url);
//			if (map != null) {
//				String json = CommonUtils.toUtf8(gson.toJson(map));
//				StringEntity postingString = new StringEntity(json);
//				System.out.println("postingString:" + json);
//				httpost.setEntity(postingString);
//				httpost.setHeader("Content-type", "application/json");
//				httpost.setHeader("Accept-Charset",HTTP.UTF_8);
//			}
//
//			DefaultHttpClient httpclient = new DefaultHttpClient();
//			HttpParams params = httpclient.getParams();
//			HttpConnectionParams.setConnectionTimeout(params, TIMEOUT_CONNECTION);
//			HttpConnectionParams.setSoTimeout(params, TIMEOUT_SO);
//
//			HttpResponse response = null;
//			response = httpclient.execute(httpost);
//
//			if(isResponseAvailable(response)){
//				try {
//					result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
//					System.out.println("result : "+ result);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}


	/**
	 * 发送消息体到服务端
	 *
	 * @param params
	 * @return
	 */
	public static String doPost(String url,HashMap<String, String> params) {
          		String content = gson.toJson(params);
		try {
			URL getUrl = new URL(url);
			HttpURLConnection urlConnection = (HttpURLConnection) getUrl.openConnection();
			urlConnection.setConnectTimeout(3000);
			urlConnection.setRequestMethod("POST"); // 以post请求方式提交
			urlConnection.setDoInput(true); // 读取数据
			urlConnection.setDoOutput(true); // 向服务器写数据
			// 获取上传信息的大小和长度
			byte[] myData = content.getBytes("utf-8");
			// 设置请求体的类型是文本类型,表示当前提交的是文本数据
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestProperty("Content-Length",
					String.valueOf(myData.length));
			// 获得输出流，向服务器输出内容
			OutputStream outputStream = urlConnection.getOutputStream();
			// 写入数据
			outputStream.write(myData, 0, myData.length);
			outputStream.close();
			// 获得服务器响应结果和状态码
			int responseCode = urlConnection.getResponseCode();
			if (responseCode == 200) {
				// 取回响应的结果
				return changeInputStream(urlConnection.getInputStream(), HTTP.UTF_8);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 将一个输入流转换成指定编码的字符串
	 *
	 * @param inputStream
	 * @param encode
	 * @return
	 */
	private static String changeInputStream(InputStream inputStream,
											String encode) {
		// 内存流
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = "";
		if (inputStream != null) {
			try {
				while ((len = inputStream.read(data)) != -1) {
					byteArrayOutputStream.write(data, 0, len);
				}
				result = new String(byteArrayOutputStream.toByteArray(), encode);
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				if(byteArrayOutputStream != null) {
					try{
						byteArrayOutputStream.close();
						byteArrayOutputStream = null;
					}
					catch(Exception ex){
					}
				}
			}
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

		String content = gson.toJson(map);
		try {
			URL getUrl = new URL(url);
			HttpURLConnection urlConnection = (HttpURLConnection) getUrl.openConnection();
			urlConnection.setConnectTimeout(3000);
			urlConnection.setRequestMethod("POST"); // 以post请求方式提交
			urlConnection.setDoInput(true); // 读取数据
			urlConnection.setDoOutput(true); // 向服务器写数据
			// 获取上传信息的大小和长度
			byte[] myData = content.getBytes("utf-8");
			// 设置请求体的类型是文本类型,表示当前提交的是文本数据
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestProperty("Content-Length",
					String.valueOf(myData.length));
			// 获得输出流，向服务器输出内容
			OutputStream outputStream = urlConnection.getOutputStream();
			// 写入数据
			outputStream.write(myData, 0, myData.length);
			outputStream.close();
			// 获得服务器响应结果和状态码
			int responseCode = urlConnection.getResponseCode();
			if (responseCode == 200) {
				// 取回响应的结果
				return changeInputStream(urlConnection.getInputStream(), HTTP.UTF_8);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

//		String result = "";
//		System.out.println("url: "+url);
//		try{
//	        HttpPost httpost = new HttpPost(url);
//	        if (map != null) {
//				String json = CommonUtils.toUtf8(gson.toJson(map));
//				StringEntity postingString = new StringEntity(json);
//				System.out.println("postingString:" + json);
//				httpost.setEntity(postingString);
//				httpost.setHeader("Content-type", "application/json");
//				httpost.setHeader("Accept-Charset","utf-8");
//			}
//
//	        DefaultHttpClient httpclient = new DefaultHttpClient();
//	        HttpParams params = httpclient.getParams();
//	        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT_CONNECTION);
//	        HttpConnectionParams.setSoTimeout(params, TIMEOUT_SO);
//
//	        HttpResponse response = null;
//	        response = httpclient.execute(httpost);
//
//			if(isResponseAvailable(response)){
//				try {
//					result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
//					System.out.println("result : "+ result);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
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
