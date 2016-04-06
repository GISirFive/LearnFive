package com.telchina.image.bean;

import java.io.Serializable;

public class PhotoDirectory implements Serializable
{

	/**
	 * 图片的文件夹路径
	 */
	private String dir;

	/**
	 * 第一张图片
	 */
	private PhotoItem firstPhoto;
	

	/**
	 * 文件夹的名称
	 */
	private String name;

	/**
	 * 图片的数量
	 */
	private int count;

	public String getDir()
	{
		return dir;
	}

	public void setDir(String dir)
	{
		this.dir = dir;
//		int lastIndexOf = this.dir.lastIndexOf("/");
//		this.name = this.dir.substring(lastIndexOf);
	}

	public PhotoItem getFirstPhoto() {
		return firstPhoto;
	}

	public void setFirstPhoto(PhotoItem firstPhoto) {
		this.firstPhoto = firstPhoto;
	}

	public String getName()
	{
		return name;
	}
	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public void setName(String name) {
		this.name = name;
	}

}
