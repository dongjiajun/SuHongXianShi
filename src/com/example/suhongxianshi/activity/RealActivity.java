package com.example.suhongxianshi.activity;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;




public class RealActivity extends BaseActivity {
	public static List<OrderMessage> orderList = new ArrayList<OrderMessage>();
	public static int [] pic_switch_arr = {R.drawable.fruit1, R.drawable.fruit2, R.drawable.fruit3, R.drawable.fruit4, R.drawable.fruit5, R.drawable.fruit6, R.drawable.fruit7, R.drawable.fruit8};
	public static List<String> ready_order_list = new ArrayList<String>();
	public static final String TAG = "RealActivity";
	private static int test_modify_content_flag=0;
	private static Toast MTOAST;			//全局Toast
	private static boolean switch_flag = true;
	
	private static Random random =new Random(100);		//掐换图片的随机数种子
	
	private LinearLayout data_page_layout;	//数据页面的最外层布局，决定是否需要显示
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
	
	private ImageView no_data_select_image;
	
	
	
	/*刷新数据的线程*/
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
		
		no_data_select_image = (ImageView)findViewById(R.id.no_data_select_image);
		
		Log.d(TAG, "onCreate");
	}
	
	
	private void getNewerData()
	{
		String addr = AddrInterface.CONNECT_REAL_SERVER_ORDER;
		HttpUtil.sendHttpRequest(addr, new HttpCallbackListener(){
			
			public void onFinish(final String response) {//成功
				runOnUiThread(new Runnable(){
					public void run() {
							Utility.handleOrderData(response, 1); //response
							//Toast.makeText(RealActivity.this, response, Toast.LENGTH_SHORT).show();
							if(RealActivity.orderList.size()!=0)	//处理显示内容
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
								/*容错逻辑*/
								name_text.setText("");
								standard_text.setText("");
								task_num_text.setText("");
								complete_text.setText("");
								data_page_layout.setVisibility(8);
								network_disconnect.setVisibility(8);
								wait_layout.setVisibility(0);
								//Toast.makeText(RealActivity.this, ""+(random.nextInt()%pic_switch_arr.length), Toast.LENGTH_SHORT).show();
								switch_flag=!switch_flag;
								if(switch_flag)
									no_data_select_image.setImageResource(RealActivity.pic_switch_arr[Math.abs((random.nextInt()%pic_switch_arr.length))]);
								
								MTOAST = Toast.makeText(RealActivity.this, "当前没有查询到数据，处于等待响应状态...", Toast.LENGTH_SHORT);
								if(MTOAST != null)
									MTOAST.show();
							}
					}
				});
			}
			
			public void onError(Exception e) {//错误 
				runOnUiThread(new Runnable(){
					public void run() {
						data_page_layout.setVisibility(8);
						network_disconnect.setVisibility(0);
						wait_layout.setVisibility(8);
						MTOAST = Toast.makeText(RealActivity.this, "请求数据超时，请检查网络连接状态..", Toast.LENGTH_SHORT);
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
			
			public void onFinish(final String response) {//成功
				runOnUiThread(new Runnable(){
					public void run() {
							Utility.handleReadyOrderData(response, 1); //response
							//Toast.makeText(RealActivity.this, response, Toast.LENGTH_SHORT).show();
							if(RealActivity.ready_order_list.size()!=0)	//处理显示内容
							{
								if(RealActivity.ready_order_list.size()>=1)
									ready_one_text.setText("1:"+RealActivity.ready_order_list.get(0).toString());
								else
									ready_one_text.setText("1:暂时没有待投任务");
								if(RealActivity.ready_order_list.size()>=2)
									ready_two_text.setText("2:"+RealActivity.ready_order_list.get(1).toString());
								else
									ready_two_text.setText("2:暂时没有待投任务");
								if(RealActivity.ready_order_list.size()>=3)
									ready_three_text.setText("3:"+RealActivity.ready_order_list.get(2).toString());
								else
									ready_three_text.setText("3:暂时没有待投任务");
							}
							else
							{
								/*容错逻辑*/
								ready_one_text.setText("1:暂时没有待投任务");
								ready_two_text.setText("2:暂时没有待投任务");
								ready_three_text.setText("3:暂时没有待投任务");
								
							}
					}
				});
			}
			
			public void onError(Exception e) {//错误 
				runOnUiThread(new Runnable(){
					public void run() {
						/*暂时放空代码即可，不需要逻辑*/
					}
				});
			}
		});
	}
	protected void onStart()
    {
    	super.onStart();
    	data_page_layout.setVisibility(0);	//设置正确的初始化的显示内容
		wait_layout.setVisibility(8);
		network_disconnect.setVisibility(8);
    	/*在活动正式进入完全工作之前的一些先期处理准备，如在onCreate中完成的主要是关于项目xml有关内容的初始化或者处理*/
    	Log.d(TAG, "onStart");
    }
    protected void onResume()
    {
    	/*visible	invisible	gone 0 4 8*/
    	super.onResume();
    	threadCut=true;
    	if(data_page_layout.getVisibility() == 0)
    	{
    		new Thread(refreshDataThread).start(); //启动线程
    	}
    	/*继续活动需要进行的逻辑*/
    	Log.d(TAG, "onResume");
    }
    protected void onPause()
    {
    	super.onPause();
    	/*活动暂停*/
    	Log.d(TAG, "onPause");
    }
    protected void onStop()
    {
    	super.onStop();
    	threadCut=false;	//终止当前页面的线程
    	/*任何有关于在活动处在工作期间的工作相关的东西应该被暂停的逻辑*/
    	Log.d(TAG, "onStop");
    }
    protected void onDestroy()
    {
    	super.onDestroy();
    	/*
    	 * 所有的最终释放资源的逻辑
    	 * */
    	Log.d(TAG, "onDestroy");
    }
    protected void onRestart()
    {
    	super.onRestart();
    	/*重启活动需要进行的逻辑处理*/
    	Log.d(TAG, "onRestart");
    }
}
