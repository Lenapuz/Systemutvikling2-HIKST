package com.banan.server;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import com.banan.shared.*;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;

public class SimServiceImpl extends RemoteServiceServlet implements SimService
{
	private Database db;	
	
	public SimServiceImpl()
	{
		db = new Database("kark.hin.no/gruppe16", "gruppe16", "php@hin-16");
	}
	
	//TODO: void newSimResult(int[] result, int profil_id) legge inn nytt resultat
	//
	

	

	
	public SimResult simulate(int profileID)
	{		
		//TODO: hente ordentlig bygg data basert på profil_id
		//TODO: hente alle heatsources fra database
		
		Profile p = new Profile("Johansens Hybel", "1989", "true", "hybel", "panelovn", "1", "20");
		Heatsource h1 = new Heatsource(0, "Panelovn", 0.5);
		Heatsource h3 = new Heatsource(0, "Varmepumpe", 0.3);
		
		//Her må vi løse hvordan vi skal behandle flere oppvarmingskilder
		
		
		
		
		
		
		
		
		
		
		
		
		
		
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
	//Faktorer til bruk i utregning
	public static double byggårForbruksFaktor(int byggår)
	{
		if (byggår > 1997)
		{		
			return 0.5;
		}
		else if (byggår > 1987 && byggår < 1997)
		{
			return 0.8;
		}
		else
		{
			return 1;
		}	
	}
	public static double beboereForbruksFaktor(int beboere)	
	{
		if (beboere == 1)
		{
			return 0.5;			
		}
		else if(beboere == 2)
		{
			return 0.6;
		}
		else if(beboere == 3)
		{
			return 0.7;
		}
		else if(beboere == 4)
		{
			return 0.8;
		}
		else if(beboere == 5)
		{
			return 0.85;
		}
		else if(beboere == 6)
		{
			return 0.9;
		}
		else if(beboere > 6)
		{
			return 0.95;
		}
		else return 1;
	}
	
	public SimResult[] GetSimResultByProfileId(int profile_id) throws IllegalArgumentException 
	{
		try
		{		
			this.db.connect();
			Statement statement = db.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM result WHERE profil_id='" + profile_id +"'");
			ArrayList<SimResult> tempResults = new ArrayList<SimResult>();
			//SimResult s = new SimResult();
			
			while (result.next())
			{
				tempResults.add(new SimResult(result.getInt("id"), result.getInt("profil_id"), result.getString("magic")));
			}
			SimResult[] results = new SimResult[tempResults.size()];
			
			for (int i = 0; i < tempResults.size(); i++)
			{
				results[i] = tempResults.get(i);
			}
			
			return results;
			
		}
		catch(Exception ex)
		{
			return null;
		}
		finally
		{
			db.disconnect();
		}
	}
	
	
	
	public void DeleteSimResult(int id) throws IllegalArgumentException 
	{
		try
		{
			this.db.connect();
			Statement statement = db.createStatement();
			String query = "DELETE FROM result WHERE id='" + id+"'";
			int i = statement.executeUpdate(query);
			
			
			if(i > 0)
			{
				//success
			}
			else
			{
				//
			}
			
		}
		catch (Exception ex)
		{
			
		}
		finally
		{
			db.disconnect();
		}
	}
	
	
}
