package com.banan.shared;

import java.io.Serializable;

import org.apache.http.entity.mime.content.StringBody;

import com.google.gwt.visualization.client.DataTable;

@SuppressWarnings("serial")
public class SimResult implements Serializable
{
	private int magic, id, profil_id;
	private Integer[] data;	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProfil_id() {
		return profil_id;
	}

	public void setProfil_id(int profil_id) {
		this.profil_id = profil_id;
	}

	

	
	public SimResult() {}
	
	public SimResult(int magic)
	{
		this.magic = magic;
	}

	public SimResult(int id, int profil_id, String  magicFromDatabase)
	{
		this.id = id;
		this.profil_id = profil_id;
		
		try
		{
			String[] s = magicFromDatabase.split(",");
			
			data = new Integer[s.length];
			
			for(int i = 0; i < s.length; i++)
			{
				data[i] = Integer.parseInt(s[i]);
			}
		}
		catch(Exception ex){
			
		}
		
		
	}
	
	//TODO
	/*
	public SimResult(int id, int profil_id, int[]data)
	{
		this.id = id;
		this.profil_id = profil_id;
		
		try
		{
			String[] s = magicFromDatabase.split(",");
			
			data = new Integer[s.length];
			
			for(int i = 0; i < s.length; i++)
			{
				data[i] = Integer.parseInt(s[i]);
			}
		}
		catch(Exception ex){
			
		}
		
		
	}*/
	
	
	public void setData(Integer[] data)
	{
		this.data = data;
	}
	
	public Integer[] getData()
	{
		return data;
	}
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 24; i ++)
		{
			sb.append(data[i] + ",");
		}
		return sb.toString();
	}
}
