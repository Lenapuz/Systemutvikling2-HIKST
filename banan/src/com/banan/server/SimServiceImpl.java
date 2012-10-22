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
		int stigningsGrad = 5;
		Integer[] dritt = new Integer[24];
		for (int i = 0; i < 24; i++)
		{
			dritt[i] = 137 + stigningsGrad ;
			stigningsGrad++;
		}
		
		SimResult r = new SimResult(0,profileID,dritt);
		this.registerSimResult(r);
		return r;	
		
		//sources fra database
		/*
		Profile p = new Profile("Johansens Hybel", "1989", "true", "hybel", "panelovn", "1", "20");
		Heatsource h1 = new Heatsource(0, "Panelovn", 0.5);
		Heatsource h3 = new Heatsource(0, "Varmepumpe", 0.3);
		Integer[] resultat = new Integer[24];
		
		//Her må vi løse hvordan vi skal behandle flere oppvarmingskilder
		
		//I denne løkka gjøres simuleringen
		for (int i = 0; i < 24; i++)
		{
			int res = 1;
			
			res *= byggårForbruksFaktor(Integer.parseInt(p.getBuildYear()));
			res*= beboereForbruksFaktor(Integer.parseInt(p.getHouseResidents()));
			res*= h1.getheatFactor();
			//res *= tidsfaktor for å variere iløpet av døgnet
			
			resultat[i] = res;
		}
		
		SimResult result = new SimResult(0,p.getID(),resultat);	
		
		// PUTT MÆ I DATABASEN
		
		return result;*/
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
	
	public void registerSimResult(SimResult result) throws IllegalArgumentException {
		try
		{
			db.connect();
			Statement statement = db.createStatement();
			int i = statement.executeUpdate("INSERT result (profil_id, magic) VALUES('" + result.getProfil_id()+ "','" + result.getMagic() + "')");
		}
		catch (Exception ex)
		{
		}
		finally
		{
			db.disconnect();
		}
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
			
			if (tempResults.size() < 1)
				return null;
			
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
