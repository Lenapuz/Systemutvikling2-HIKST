package com.banan.shared;

import java.io.Serializable;

import com.google.gwt.visualization.client.DataTable;

@SuppressWarnings("serial")
public class SimResult implements Serializable
{
	private int magic;
	private Integer[] data;
	
	public SimResult() {}
	
	public SimResult(int magic)
	{
		this.magic = magic;
	}
	
	public int getMagic()
	{
		return magic;
	}
	
	public void setData(Integer[] data)
	{
		this.data = data;
	}
	
	public Integer[] getData()
	{
		return data;
	}
}
