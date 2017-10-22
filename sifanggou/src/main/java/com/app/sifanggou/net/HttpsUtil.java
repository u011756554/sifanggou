package com.app.sifanggou.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.Gson;

import android.text.TextUtils;

public class HttpsUtil {
	
	private static final int TIMEOUT_CONNECTION = 10000;
	
	public static enum HttpState {
		Created("Created"),BadRequest("Bad Request");
		
		private String state;
		HttpState(String state) {
			this.state = state;
		}
		
		public String getState() {
			return this.state;
		}
	}
	
	public static String doGetString(String url) {
		String result = "";
		if (TextUtils.isEmpty(url)) {
			return result;
		}
		try {
			URL getUrl = new URL(url);
			HttpURLConnection httpsURLConnection = (HttpURLConnection) getUrl.openConnection();
			httpsURLConnection.setConnectTimeout(TIMEOUT_CONNECTION);
			httpsURLConnection.setRequestMethod("GET");
			httpsURLConnection.setRequestProperty("Content-Type", "application/json");
			
			InputStream in = httpsURLConnection.getInputStream();
			result = convertStreamToString(in);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static String doPost(String url,HashMap<String,String> map) {
		String result = "";
		if (TextUtils.isEmpty(url)) {
			return result;
		}
		try {
			URL getUrl = new URL(url);
			HttpURLConnection httpsURLConnection =  (HttpURLConnection) getUrl.openConnection();
			httpsURLConnection.setConnectTimeout(TIMEOUT_CONNECTION);
			httpsURLConnection.setRequestMethod("POST");
			httpsURLConnection.setRequestProperty("Content-Type", "application/json");
			httpsURLConnection.setRequestProperty("Connection", "Keep-Alive");
			
	        httpsURLConnection.setDoInput(true);
	        
	        if (map != null) {
	        	Gson gson = new Gson();
				String param = gson.toJson(map);
				System.out.println("param:"+param);
				httpsURLConnection.setRequestProperty("body", param);
			}

	        httpsURLConnection.connect();
	        
			InputStream in = httpsURLConnection.getInputStream();
			result = convertStreamToString(in);
			httpsURLConnection.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}	
	
	/**
     * map转url参数
     */
    public static String map2Url(HashMap<String, String> paramToMap) {
        if (null == paramToMap || paramToMap.isEmpty()) {
            return null;
        }
        StringBuffer url    = new StringBuffer();
        boolean      isfist = true;
        for (HashMap.Entry<String, String> entry : paramToMap.entrySet()) {
            if (isfist) {
                isfist = false;
            } else {
                url.append("&");
            }
            url.append(entry.getKey()).append("=");
            String value = entry.getValue();
            if (!TextUtils.isEmpty(value)) {
                try {
                    url.append(URLEncoder.encode(value, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("request data : "+url.toString());
        return url.toString();
    }	
    
	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuffer sbf = new StringBuffer();
		String line = null;
		try {
			while((line = reader.readLine()) != null) {
				sbf.append(line);
			}
		} catch (IOException  e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sbf.toString();
	}

	private static HostnameVerifier getHostnameVerifier() {
		return new HostnameVerifier() {

			@Override
			public boolean verify(String hostname, SSLSession session) {
				// TODO Auto-generated method stub
				return true;
			}
			
		};
	}
	
	private static SSLContext getSSLContext() {
		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, getTrustManager(), null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sslContext;
	}
	
	private static TrustManager[] getTrustManager() {
		TrustManager[] trustAllCerts = new TrustManager[]{
				new X509TrustManager(){

					@Override
					public void checkClientTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void checkServerTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {
						// TODO Auto-generated method stub
						
					}

					@Override
					public X509Certificate[] getAcceptedIssuers() {
						// TODO Auto-generated method stub
						return new X509Certificate[0];
					}
				}
		};
		return trustAllCerts;
	}
	
}
