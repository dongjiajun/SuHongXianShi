package com.example.suhongxianshi.activity;


import java.util.ArrayList;
import java.util.List;

import com.example.suhongxianshi.R;
import com.example.suhongxianshi.model.OrderMessage;
import com.example.suhongxianshi.util.AddrInterface;
import com.example.suhongxianshi.util.BaseActivity;
import com.example.suhongxianshi.util.HttpCallbackListener;
import com.example.suhongxianshi.util.HttpUtil;
import com.example.suhongxianshi.util.Utility;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;




public class RealActivity extends BaseActivity {
	public static List<OrderMessage> orderList = new ArrayList<OrderMessage>();
	public static final String TAG = "RealActivity";
	private static int test_modify_content_flag=0;
	
	private LinearLayout data_page_layout;	//����ҳ�������㲼�֣������Ƿ���Ҫ��ʾ
	private LinearLayout wait_layout;
	private LinearLayout network_disconnect;
	private boolean threadCut = true;
	
	private TextView name_text;
	private TextView standard_text;
	private TextView task_num_text;
	private TextView complete_text;
	
	private TextView ready_one_text;
	private TextView ready_two_text;
	private TextView ready_three_text;
	
	/*ˢ�����ݵ��߳�*/
	Runnable refreshDataThread=new Runnable(){
		public void run(){
			while(threadCut)
			{
				try 
				{
					Thread.sleep(3000);
					getNewerData();
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}
	};
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.real_use_layout);
		
		threadCut = true;
		
		data_page_layout = (LinearLayout)findViewById(R.id.data_range_layout);
		wait_layout = (LinearLayout)findViewById(R.id.watting_layout);
		network_disconnect = (LinearLayout)findViewById(R.id.network_disconnect);
		
		
		
		//data_page_layout.setVisibility(8);
		
		name_text = (TextView)findViewById(R.id.shopping_name_text);
		standard_text = (TextView)findViewById(R.id.goods_standard_text);
		task_num_text = (TextView)findViewById(R.id.task_num_text);
		complete_text = (TextView)findViewById(R.id.complete_num_text);
		
		ready_one_text = (TextView)findViewById(R.id.ready_one_text);
		ready_two_text = (TextView)findViewById(R.id.ready_two_text);
		ready_three_text = (TextView)findViewById(R.id.ready_three_text);
		
		Log.d(TAG, "onCreate");
		//Toast.makeText(RealActivity.this, orderList.get(1).toString(), Toast.LENGTH_SHORT).show();
	}
	
	
	private void getNewerData()
	{
		String addr = AddrInterface.CONNECT_REAL_SERVER;
		HttpUtil.sendHttpRequest(addr, new HttpCallbackListener(){
			
			public void onFinish(final String response) {//�ɹ�
				runOnUiThread(new Runnable(){
					public void run() {
							
							
							Utility.handleOrderData(response, 1); //response
							//Toast.makeText(RealActivity.this, "this orderList length is:"+RealActivity.orderList.size(), Toast.LENGTH_SHORT).show();
							if(RealActivity.orderList.size()!=0)	//������ʾ����
							{
								//Toast.makeText(RealActivity.this, "response content is:"+response, Toast.LENGTH_SHORT).show(); //response
								data_page_layout.setVisibility(0);
								wait_layout.setVisibility(8);
								network_disconnect.setVisibility(8);
								name_text.setText(RealActivity.orderList.get(0).getName());
								standard_text.setText(RealActivity.orderList.get(0).getStandard());
								task_num_text.setText(RealActivity.orderList.get(0).getTaskNum());
								complete_text.setText(RealActivity.orderList.get(0).getCompleteNum());
								
								if(RealActivity.orderList.size()>=2)
								{
									//Toast.makeText(RealActivity.this, ""+RealActivity.orderList.size(),Toast.LENGTH_SHORT).show();
									ready_one_text.setText(RealActivity.orderList.get(1).getName());
								}
								else
									ready_one_text.setText("1:��ʱû�д�Ͷ����");
								if(RealActivity.orderList.size()>=3)
									ready_two_text.setText(RealActivity.orderList.get(2).getName());
								else
									ready_two_text.setText("2:��ʱû�д�Ͷ����");
								if(RealActivity.orderList.size()>=4)
									ready_two_text.setText(RealActivity.orderList.get(2).getName());
								else
									ready_three_text.setText("3:��ʱû�д�Ͷ����");
							}
							else
							{
								/*�ݴ��߼�*/
								//Toast.makeText(RealActivity.this, "����ɹ�����ȡ��Ϣ����", Toast.LENGTH_SHORT).show();
								name_text.setText("");
								standard_text.setText("");
								task_num_text.setText("");
								complete_text.setText("");
								ready_one_text.setText("");
								ready_two_text.setText("");
								ready_three_text.setText("");
								data_page_layout.setVisibility(8);
								network_disconnect.setVisibility(8);
								wait_layout.setVisibility(0);
								
								Toast.makeText(RealActivity.this, "��ǰû�в�ѯ�����ݣ����ڵȴ���Ӧ״̬...", Toast.LENGTH_SHORT).show();
							}
					}
				});
			}
			
			public void onError(Exception e) {//���� 
				runOnUiThread(new Runnable(){
					public void run() {
						data_page_layout.setVisibility(8);
						network_disconnect.setVisibility(0);
						wait_layout.setVisibility(8);
						Toast.makeText(RealActivity.this, "�������ݳ�ʱ��������������״̬..", Toast.LENGTH_LONG).show();
					}
				});
			}
		});
		
	}
	protected void onStart()
    {
    	super.onStart();
    	data_page_layout.setVisibility(0);	//������ȷ�ĳ�ʼ������ʾ����
		wait_layout.setVisibility(8);
		network_disconnect.setVisibility(8);
    	/*�ڻ��ʽ������ȫ����֮ǰ��һЩ���ڴ���׼��������onCreate����ɵ���Ҫ�ǹ�����Ŀxml�й����ݵĳ�ʼ�����ߴ���*/
    	Log.d(TAG, "onStart");
    }
    protected void onResume()
    {
    	/*visible	invisible	gone 0 4 8*/
    	super.onResume();
    	threadCut=true;
    	if(data_page_layout.getVisibility() == 0)
    	{
    		new Thread(refreshDataThread).start(); //�����߳�
    	}
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
