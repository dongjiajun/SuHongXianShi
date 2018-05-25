package com.example.suhongxianshi.util;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;

public class ActivityCollector {
	public static List<Activity> activities = new ArrayList<Activity>();
		public static void addActivity(Activity activity) {
			if(!activities.contains(activity))//这里必须完成活动页面的剔除，防止页面的叠加。
			{
			activities.add(activity);
			}
		}
		
		public static void removeActivity(Activity activity) {
			activities.remove(activity);
		}
		
		public static void finishAll() {
			for (Activity activity : activities)
			{
			if (!activity.isFinishing()) {
			activity.finish();
			}
		}
	}
}