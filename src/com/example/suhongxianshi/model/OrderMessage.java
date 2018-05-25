package com.example.suhongxianshi.model;

public class OrderMessage {
	private String shopping_name;
	private String goods_standard;
	private String task_num;
	private String complete_num;
	
	public static String TEST_STR="[{\"shopping_name\":\"·ïÀæ\",\"goods_standard\":\"1x24¸ö/Ïä\",\"task_num\":\"5\",\"complete_num\":\"6\"}," +
			"{\"shopping_name\":\"·ïÀæ\",\"goods_standard\":\"1x24¸ö/Ïä\",\"task_num\":\"5\",\"complete_num\":\"6\"}," +
			"{\"shopping_name\":\"·ïÀæ\",\"goods_standard\":\"1x24¸ö/Ïä\",\"task_num\":\"5\",\"complete_num\":\"6\"}]";
	
	public OrderMessage()
	{}
	public OrderMessage(String shopping_name, String goods_standard, String task_num, String complete_num)
	{
		this.shopping_name = shopping_name;
		this.goods_standard = goods_standard;
		this.task_num = task_num;
		this.complete_num = complete_num;
	}
	public void SetName(String shopping_name)
	{
		this.shopping_name = shopping_name;
	}
	public String getName()
	{
		return this.shopping_name;
	}
	
	public void SetStandard(String goods_standard)
	{
		this.goods_standard = goods_standard;
	}
	public String getStandard()
	{
		return this.goods_standard;
	}
	
	public void SetTaskNum(String task_num)
	{
		this.task_num = task_num;
	}
	public String getTaskNum()
	{
		return this.task_num;
	}
	
	public void SetCompleteNum(String complete_num)
	{
		this.task_num = task_num;
	}
	public String getCompleteNum()
	{
		return this.complete_num;
	}
	public String toString()
	{
		return this.shopping_name+" "+this.goods_standard+" "+this.task_num+" "+this.complete_num;
	}
}
