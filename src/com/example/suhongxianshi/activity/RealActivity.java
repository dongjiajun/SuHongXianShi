package com.example.suhongxianshi.activity;


import java.util.ArrayList;
import java.util.List;

import com.example.suhongxianshi.R;
import com.example.suhongxianshi.model.JsonTestClass;
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
	public static List<String> ready_order_list = new ArrayList<String>();
	public static final String TAG = "RealActivity";
	private static int test_modify_content_flag=0;
	private static Toast MTOAST;			//ȫ��Toast
	
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
					getNewReadyData();
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
	}
	
	
	private void getNewerData()
	{
		String addr = AddrInterface.CONNECT_REAL_SERVER_ORDER;
		HttpUtil.sendHttpRequest(addr, new HttpCallbackListener(){
			
			public void onFinish(final String response) {//�ɹ�
				runOnUiThread(new Runnable(){
					public void run() {
							Utility.handleOrderData(response, 1); //response
							Toast.makeText(RealActivity.this, response, Toast.LENGTH_SHORT).show();
							if(RealActivity.orderList.size()!=0)	//������ʾ����
							{
								if(MTOAST !=null)
									MTOAST.cancel();
								data_page_layout.setVisibility(0);
								wait_layout.setVisibility(8);
								network_disconnect.setVisibility(8);
								name_text.setText(RealActivity.orderList.get(0).getName());
								standard_text.setText(RealActivity.orderList.get(0).getStandard());
								task_num_text.setText(RealActivity.orderList.get(0).getTaskNum());
								complete_text.setText(RealActivity.orderList.get(0).getCompleteNum());
							}
							else
							{
								/*�ݴ��߼�*/
								name_text.setText("");
								standard_text.setText("");
								task_num_text.setText("");
								complete_text.setText("");
								data_page_layout.setVisibility(8);
								network_disconnect.setVisibility(8);
								wait_layout.setVisibility(0);
								
								MTOAST = Toast.makeText(RealActivity.this, "��ǰû�в�ѯ�����ݣ����ڵȴ���Ӧ״̬...", Toast.LENGTH_SHORT);
								if(MTOAST != null)
									MTOAST.show();
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
						MTOAST = Toast.makeText(RealActivity.this, "�������ݳ�ʱ��������������״̬..", Toast.LENGTH_SHORT);
						if(MTOAST != null)
							MTOAST.show();
					}
				});
			}
		});
		
	}
	private void getNewReadyData()
	{
		String addr = AddrInterface.CONNECT_REAL_SERVER_READY;  //CONNCT_LOCAL_SERVER_READY
		HttpUtil.sendHttpRequest(addr, new HttpCallbackListener(){
			
			public void onFinish(final String response) {//�ɹ�
				runOnUiThread(new Runnable(){
					public void run() {
							Utility.handleReadyOrderData(response, 1); //response
							Toast.makeText(RealActivity.this, response, Toast.LENGTH_SHORT).show();
							if(RealActivity.ready_order_list.size()!=0)	//������ʾ����
							{
								if(RealActivity.ready_order_list.size()>=1)
									ready_one_text.setText("1:"+RealActivity.ready_order_list.get(0).toString());
								else
									ready_one_text.setText("1:��ʱû�д�Ͷ����");
								if(RealActivity.ready_order_list.size()>=2)
									ready_two_text.setText("2:"+RealActivity.ready_order_list.get(1).toString());
								else
									ready_two_text.setText("2:��ʱû�д�Ͷ����");
								if(RealActivity.ready_order_list.size()>=3)
									ready_three_text.setText("3:"+RealActivity.ready_order_list.get(2).toString());
								else
									ready_three_text.setText("3:��ʱû�д�Ͷ����");
							}
							else
							{
								/*�ݴ��߼�*/
								ready_one_text.setText("1:��ʱû�д�Ͷ����");
								ready_two_text.setText("2:��ʱû�д�Ͷ����");
								ready_three_text.setText("3:��ʱû�д�Ͷ����");
								
							}
					}
				});
			}
			
			public void onError(Exception e) {//���� 
				runOnUiThread(new Runnable(){
					public void run() {
						/*��ʱ�ſմ��뼴�ɣ�����Ҫ�߼�*/
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
