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
	
	//Selve kalkuleringsmetoden
	public SimResult simulate(int profileID)
	{		
		/*
		int stigningsGrad = 5;
		Integer[] dritt = new Integer[24];
		for (int i = 0; i < 24; i++)
		{
			dritt[i] = 137 + stigningsGrad ;
			stigningsGrad++;
		}
		
		SimResult r = new SimResult(0,profileID,dritt);
		this.registerSimResult(r);
		return r;	*/
		
		
		ProfileServiceImpl psim = new ProfileServiceImpl();
		Profile p = psim.getProfileByProfileId(profileID);
		Integer[] resultat = new Integer[24];
		
		//Her må vi løse hvordan vi skal behandle flere oppvarmingskilder
		
		//I denne løkka gjøres simuleringen
		for (int i = 0; i < 24; i++)
		{
			int res = 100;
			res *= byggårForbruksFaktor(Integer.parseInt(p.getBuildYear()));
			res*= beboereForbruksFaktor(Integer.parseInt(p.getHouseResidents()));
			res*= this.hourlyPowerConsumption(i);
			resultat[i] = res;
		}
		
		SimResult result = new SimResult(0,p.getID(),resultat);	
			
		// PUTT MÆ I DATABASEN
		this.registerSimResult(result);
		
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
	public double hourlyPowerConsumption(int time)
	{
		double[] powerConsumption = new double[24];
		powerConsumption[0] = 0.40;
		powerConsumption[1] = 0.35;
		powerConsumption[2] = 0.35;
		powerConsumption[3] = 0.30;
		powerConsumption[4] = 0.25;
		powerConsumption[5] = 0.35;
		powerConsumption[6] = 0.50;
		powerConsumption[7] = 0.95;
		powerConsumption[8] = 0.80;
		powerConsumption[9] = 0.75;
		powerConsumption[10] = 0.70;
		powerConsumption[11] = 0.60;
		powerConsumption[12] = 0.65;
		powerConsumption[13] = 0.70;
		powerConsumption[14] = 0.65;
		powerConsumption[15] = 0.80;
		powerConsumption[16] = 1.00;
		powerConsumption[17] = 0.95;
		powerConsumption[18] = 0.90;
		powerConsumption[19] = 0.80;
		powerConsumption[20] = 0.70;
		powerConsumption[21] = 0.80;
		powerConsumption[22] = 0.70;
		powerConsumption[23] = 0.50;
		
		return powerConsumption[time];
	}
	
	//Databasemetoder
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
