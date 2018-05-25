package com.example.suhongxianshi.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*利用HttpURLConnection类来建立的http连接类*/
public class HttpUtil {
 //public static final String tag="HttpUtil";
	
	/*
	 * 使用get方式来请求http连接
	 * */
	public static void sendHttpRequest(final String address,
			final HttpCallbackListener httpCallbackListener) 
	{
		new Thread(new Runnable()
		{
			public void run() 
			{
				HttpURLConnection connection=null;
				try{
					URL url=new URL(address);//address
					connection=(HttpURLConnection)url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in=connection.getInputStream();
					BufferedReader reader=new BufferedReader(new InputStreamReader(in));//"UTF-8"是重点，关于是否乱码
					StringBuilder response=new StringBuilder();
					String line;
					while((line=reader.readLine())!=null)
					{
						response.append(line);
					}
					if(httpCallbackListener!=null)
					{
						httpCallbackListener.onFinish(response.toString());
					}
				}catch(Exception e)
				{
					if(httpCallbackListener!=null)
					{
						httpCallbackListener.onError(e);
					}
				}finally{
					if(connection!=null)
						connection.disconnect();
				}
			}
			
		}).start();
	}
	/*
	 * 使用post方式来请求http连接
	 * */
	public static void sendHttpRequestUsePost(final int n_id,final String address,
			final HttpCallbackListener listener) 
	{	
		new Thread(new Runnable()
		{
			public void run() 
			{
				HttpURLConnection connection=null;
				try{
					URL url=new URL(address);//address
					connection=(HttpURLConnection)url.openConnection();
					connection.setRequestMethod("POST");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					DataOutputStream out = new DataOutputStream(connection.getOutputStream());
					out.writeBytes("id="+n_id); 
					InputStream in=connection.getInputStream();
					BufferedReader reader=new BufferedReader(new InputStreamReader(in));//"UTF-8"是重点，关于是否乱码
					StringBuilder response=new StringBuilder();
					String line;
					while((line=reader.readLine())!=null)
					{
						response.append(line);
					}
					if(listener!=null)
					{
						listener.onFinish(response.toString());
					}
				}catch(Exception e)
				{
					if(listener!=null)
					{
						listener.onError(e);
					}
				}finally{
					if(connection!=null)
						connection.disconnect();
				}
			}
			
		}).start();
	}
		

}
