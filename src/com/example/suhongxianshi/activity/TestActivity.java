package com.example.suhongxianshi.activity;


import com.example.suhongxianshi.R;
import com.example.suhongxianshi.util.BaseActivity;
import com.example.suhongxianshi.util.HttpCallbackListener;
import com.example.suhongxianshi.util.HttpUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


public class TestActivity extends BaseActivity {
	private TextView test_web_connect;
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case 1:
				Toast.makeText(TestActivity.this, "handler is running", Toast.LENGTH_SHORT).show();
			}
		}
	};
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_layout);
		final TextView test_web_connect = (TextView)findViewById(R.id.test_web);
		String address = "http://10.0.2.2/interface_mutil/virtual/test.php";
		HttpUtil.sendHttpRequest(address,new HttpCallbackListener(){
			
			public void onFinish(final String response) {//成功
				runOnUiThread(new Runnable(){
					public void run() {
						//Utility.handleServerResponseWarn(response, 2);
						//Toast.makeText(WarnActivity.this, response, Toast.LENGTH_SHORT).show();
						test_web_connect.setText(response);
						
					}
				});
			}
			
			public void onError(Exception e) {//错误 
				runOnUiThread(new Runnable(){
					public void run() {
						String content="";
						//Utility.handleServerResponseWarn(content, 2);
						Toast.makeText(TestActivity.this, "连接超时，请检查网络连接状态", Toast.LENGTH_SHORT).show();
						
					}
				});
			}
		});
	}
	protected void onResume()
    {
    	super.onResume();
    }
    protected void onStop()
    {
    	super.onStop();
    	
    }
}
