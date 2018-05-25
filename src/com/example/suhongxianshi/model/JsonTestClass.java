package com.example.suhongxianshi.model;


//一个JSON数据解析测试类
public class JsonTestClass {

	private int id;
	private double version;
	private String name;
	
	/*测试数据存放*/
	public static String TEST_STR="[{\"id\":\"5\",\"version\":\"5.5\",\"name\":\"Angry Birds\"}," +
								"{\"id\":\"6\",\"version\":\"7.0\",\"name\":\"Clash of Clans\"}," +
								"{\"id\":\"7\",\"version\":\"3.5\",\"name\":\"Hey Day\"}]";
	
	public JsonTestClass()
	{
		/*无参构造函数的实现体*/
	}
	public JsonTestClass(int id ,double version,String name)
	{
		this.id=id;
		this.version=version;
		this.name=name;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public void setVersion(double version)
	{
		this.version=version;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public int getId()
	{
		return id;
	}
	public double getVersion()
	{
		return version;
	}
	public String getName()
	{
		return name;
	}
	public String toString()
	{
		return id+" "+version+" "+name;
	}
}
