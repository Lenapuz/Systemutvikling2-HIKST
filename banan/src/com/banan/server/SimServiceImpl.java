package com.banan.server;

import java.util.Random;

import com.banan.shared.*;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;

public class SimServiceImpl extends RemoteServiceServlet implements SimService
{
	public SimResult simulate(int profileID)
	{
		SimResult result = new SimResult(profileID * 1881);	
		Random rand = new Random();
		
		Integer[] data = new Integer[24];	
		for (int i = 0; i < 24; i++)
		{
			data[i] = rand.nextInt(1337);
		}
		result.setData(data);
		
		return result;
	}
}
