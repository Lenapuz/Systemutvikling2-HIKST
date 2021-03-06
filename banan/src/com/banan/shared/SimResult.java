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
	
	public SimResult(int id, int profil_id, Integer[]data)
	{
		this.id = id;
		this.profil_id = profil_id;
		
		try
		{
			this.data = new Integer[data.length];
			
			for(int i = 0; i < data.length; i++)
			{
				this.data[i] = data[i];
			}
		}
		catch(Exception ex){
			
		}
		
		
	}

	//TODO
	public int getTotalEnergyConsumption()//kwh
	{
		int res = 0;
		for(int i =0;i<data.length;i++)
		{
			res += data[i];
		}
		return res;
	}
	public int getAvgEnergyConsumption()//kw
	{
		int res = 0;
		for(int i =0;i<data.length;i++)
		{
			res += data[i];
		}
		res /= data.length;
		return res;
	}

	public String getMagic ()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(data[0]);
		for(int i = 1; i < data.length; i++)
		{
			sb.append("," + data[i]);
			
		}
		return sb.toString();
	}
	
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
		sb.append("\nID: " + id);
		sb.append("\nprofile_id: "+profil_id+"\n");
		for(int i = 0; i < data.length; i ++)
		{
			sb.append(data[i] + ",");
		}
		return sb.toString();
	}
}
