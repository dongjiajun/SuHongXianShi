package com.example.suhongxianshi.model;


//һ��JSON���ݽ���������
public class JsonTestClass {

	private int id;
	private double version;
	private String name;
	
	/*�������ݴ��*/
	public static String TEST_STR="[{\"id\":\"5\",\"version\":\"5.5\",\"name\":\"Angry Birds\"}," +
								"{\"id\":\"6\",\"version\":\"7.0\",\"name\":\"Clash of Clans\"}," +
								"{\"id\":\"7\",\"version\":\"3.5\",\"name\":\"Hey Day\"}]";
	
	public JsonTestClass()
	{
		/*�޲ι��캯����ʵ����*/
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
