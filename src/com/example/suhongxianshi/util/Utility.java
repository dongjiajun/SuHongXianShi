package com.example.suhongxianshi.util;

import org.json.JSONArray;
import org.json.JSONObject;


import com.example.suhongxianshi.activity.MainActivity;
import com.example.suhongxianshi.activity.RealActivity;
import com.example.suhongxianshi.model.JsonTestClass;
import com.example.suhongxianshi.model.OrderMessage;


import android.content.ContentValues;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;



public class Utility {
	/*������������ */
	public synchronized static boolean handleTestData(String response,int type)
	{
		if (!TextUtils.isEmpty(response)) //�ſմ������ִ��
		{
			try{
				JSONArray jsonArray = new JSONArray(response);
				for (int i = 0; i < jsonArray.length(); i++) 
				{
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					MainActivity.testList.add(
							new JsonTestClass(
									Integer.parseInt(jsonObject.getString("id")),
									Double.parseDouble(jsonObject.getString("version")),
									jsonObject.getString("name")
									)
					);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/*������������*/
	public synchronized static boolean handleOrderData(String response, int type)
	{
		RealActivity.orderList.clear(); //�����ǰ��������
		if(!TextUtils.isEmpty(response))
		{
			try{
				//������Ҫ�յ�����һ��json���飬��Ȼ�Ļ�������ʱ������.
				JSONArray jsonArray = new JSONArray(response);
				Log.d(RealActivity.TAG,response);
				for(int i = 0;i < jsonArray.length(); i++)
				{
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					RealActivity.orderList.add(
							new OrderMessage(
									jsonObject.getString("shopping_name"),
									jsonObject.getString("goods_standard"),
									jsonObject.getString("task_num"),
									jsonObject.getString("complete_num")
									)
							);
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}
	
	
	
	/*public synchronized static boolean handleServerResponseWarn(String response,int type)//ע��֮ǰ�ĵط�һ���ò���forѭ����ԭ���Ƿ��ص�json�����Ͳ����������ʽ��ֻҪʹ�����������ʽ�ľͺ��ˡ�
	{
		Log.d(AddrInterface.TAG,"��ȡ�������������ݳɹ�");
		if (!TextUtils.isEmpty(response)) //�ſմ������ִ��
		{
			try{
				JSONArray jsonArray=new JSONArray(response);
				WarnActivity.warnList.clear();
				for(int i=0;i<jsonArray.length();i++)
				{
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					Warn mid=new Warn();
					mid.setDeviceId(jsonObject.getString("dev_id"));
					mid.setWarnFlag(Integer.parseInt(jsonObject.getString("warn_flag")));
					mid.setWarnText(jsonObject.getString("warn_text"));
					WarnActivity.warnList.add(mid);
					
					
				}	
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else{
			WarnActivity.warnList.clear();
		}
		return false;
	}*/
	

}
