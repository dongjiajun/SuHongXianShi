package com.example.suhongxianshi.activity;

import java.util.ArrayList;
import java.util.List;


import com.example.suhongxianshi.R;
import com.example.suhongxianshi.model.JsonTestClass;
import com.example.suhongxianshi.util.Utility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	public static String TAG = "SuHongTest";
	private Button testButton;
	private Button testUiThreadButton;
	private Button testJsonButton;
	private Button goToButton;
	private TextView textUiThread;
	
	private  Boolean threadCut = true;  //�߳���ֹ���
	public static List<JsonTestClass> testList = new ArrayList<JsonTestClass>();
	
	Handler uiHandler = new Handler(){
		public void handleMessage(Message msg)
		{
			String temp;
			switch(msg.what)
			{
				case 1:
					temp = textUiThread.getText().toString();
					textUiThread.setText(temp+"\n�����߳�");
					break;
			}
		}
	};
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        threadCut = true; //ʹUI�߳��ܹ�����
        
        testButton = (Button)findViewById(R.id.test_button);
        testUiThreadButton = (Button)findViewById(R.id.test_uithread);
        testJsonButton = (Button)findViewById(R.id.test_json_parse);
        goToButton = (Button)findViewById(R.id.go_to_real);
        textUiThread = (TextView)findViewById(R.id.text_ui_thread);
        
        testButton.setOnClickListener(this);
        testUiThreadButton.setOnClickListener(this);
        testJsonButton.setOnClickListener(this);
        goToButton.setOnClickListener(this);
        Log.d(TAG, "onCreate");
    }
    
    public void onClick(View view)
	{
		switch(view.getId())
		{
		case R.id.test_button:
			Intent intent_test = new Intent(MainActivity.this, TestActivity.class);
			startActivity(intent_test);
			break;
		case R.id.test_uithread:
			//���ﲻ����UI�̣߳����и����쳣
			new Thread(
					new Runnable(){
						public void run()
						{
							while(threadCut)
							{
								
								try {
									Thread.sleep(2000);
									Message msg = new Message();
									msg.what = 1;
									uiHandler.sendMessage(msg);
									//Log.d(TAG, "�����߳����Handler�������߳��е�UI��");
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								
							}
						}
					}
					).start();
			break;
		case R.id.test_json_parse:
			//Toast.makeText(MainActivity.this, "json����", Toast.LENGTH_SHORT).show();
			Utility.handleTestData(JsonTestClass.TEST_STR, 1);
			String str="";
			for(int i=0;i<testList.size();i++)
				str+=(testList.get(i)).toString();
			textUiThread.setText(str);
			break;
		case R.id.go_to_real:
			Intent intent_real = new Intent(MainActivity.this, RealActivity.class);
			startActivity(intent_real);
		default:
			break;
		}
	}

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    protected void onStart()
    {
    	super.onStart();
    	/*�ڻ��ʽ������ȫ����֮ǰ��һЩ���ڴ���׼��������onCreate����ɵ���Ҫ�ǹ�����Ŀxml�й����ݵĳ�ʼ�����ߴ���*/
    	Log.d(TAG, "onStart");
    }
    protected void onResume()
    {
    	super.onResume();
    	threadCut=true;
    	/*�������Ҫ���е��߼�*/
    	Log.d(TAG, "onResume");
    }
    protected void onPause()
    {
    	super.onPause();
    	/*���ͣ*/
    	Log.d(TAG, "onPause");
    }
    protected void onStop()
    {
    	super.onStop();
    	threadCut=false;	//��ֹ��ǰҳ����߳�
    	/*�κ��й����ڻ���ڹ����ڼ�Ĺ�����صĶ���Ӧ�ñ���ͣ���߼�*/
    	Log.d(TAG, "onStop");
    	textUiThread.setText("");
    	
    }
    protected void onDestroy()
    {
    	super.onDestroy();
    	/*
    	 * ���е������ͷ���Դ���߼�
    	 * */
    	Log.d(TAG, "onDestroy");
    }
    protected void onRestart()
    {
    	super.onRestart();
    	/*�������Ҫ���е��߼�����*/
    	Log.d(TAG, "onRestart");
    }
}
