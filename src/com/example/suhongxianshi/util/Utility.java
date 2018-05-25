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
	/*解析测试数据 */
	public synchronized static boolean handleTestData(String response,int type)
	{
		if (!TextUtils.isEmpty(response)) //放空代码可以执行
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
	
	/*解析订单数据*/
	public synchronized static boolean handleOrderData(String response, int type)
	{
		RealActivity.orderList.clear(); //清除当前所有数据
		if(!TextUtils.isEmpty(response))
		{
			try{
				//这里需要收到的是一个json数组，不然的话解析的时候会出错.
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
	
	
	
	/*public synchronized static boolean handleServerResponseWarn(String response,int type)//注意之前的地方一条用不了for循环的原因是返回的json本来就不是数组的形式，只要使它返回数组格式的就好了。
	{
		Log.d(AddrInterface.TAG,"获取服务器报警数据成功");
		if (!TextUtils.isEmpty(response)) //放空代码可以执行
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
