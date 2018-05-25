package com.example.suhongxianshi.util;

public interface HttpCallbackListener {

	void onFinish(String response);
	void onError(Exception e);
}
