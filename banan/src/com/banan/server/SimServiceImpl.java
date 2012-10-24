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
	public SimResult simulate(int profileID, int temperatur)
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
			res*= byggårForbruksFaktor(Integer.parseInt(p.getBuildYear()));
			res*= beboereForbruksFaktor(Integer.parseInt(p.getHouseResidents()));
			res*= this.hourlyPowerConsumption(i, Integer.parseInt(p.getHouseResidents()));
			
			//Hvis hus størrelse er lik 0, ingen utregning
			if (Integer.parseInt(p.getHouseSize()) == 0)
			{
				resultat[i] = 0;
			} 
			else 
			{		
				resultat[i] = res;
			}
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
			return 1.0;			
		}
		else if(beboere == 2)
		{
			return 1.2;
		}
		else if(beboere == 3)
		{
			return 1.3;
		}
		else if(beboere == 4)
		{
			return 1.37;
		}
		else if(beboere == 5)
		{
			return 1.42;
		}
		else if(beboere == 6)
		{
			return 1.46;
		}
		else if(beboere > 6)
		{
			return 1.5;
		}
		else return 1;
	}
	
	public static int tempToCelsius(int tempKelvin)
	{
		int res = tempKelvin-273;
		return res;
	}
	public static int tempToKelvin(int tempCelsius)
	{
		int res = tempCelsius + 273;
		return res;
	}
	public double hourlyPowerConsumption(int time, int beboere)
	{
		double[] powerConsumption = new double[24];
		double residentsFactor = 0;
		
		if (beboere == 1)
		{
			residentsFactor = 1.0;
		}
		else if (beboere == 2)
		{
			residentsFactor = 1.1;
		}
		else if (beboere == 3)
		{
			residentsFactor = 1.18;
		}
		else if (beboere == 4)
		{
			residentsFactor = 1.22;
		}
		else if (beboere >= 5)
		{
			residentsFactor = 1.25;
		}
		else {
			residentsFactor = 0;  // feil
		}
		double faktorJustering = 0.94;
		
		// * resFactor for jamnere strømforbruk med flere folk i huset
		powerConsumption[0] = 0.40 * (residentsFactor/faktorJustering);
		powerConsumption[1] = 0.35 * (residentsFactor/faktorJustering);
		powerConsumption[2] = 0.33 * residentsFactor;
		powerConsumption[3] = 0.30 * residentsFactor;
		powerConsumption[4] = 0.25 * residentsFactor;
		powerConsumption[5] = 0.35 * residentsFactor;
		powerConsumption[6] = 0.50 * (residentsFactor/faktorJustering);
		powerConsumption[7] = 0.95;
		powerConsumption[8] = 0.65;
		powerConsumption[9] = 0.50;
		powerConsumption[10] = 0.48;
		powerConsumption[11] = 0.50;
		powerConsumption[12] = 0.52 * (residentsFactor/faktorJustering);
		powerConsumption[13] = 0.54 * (residentsFactor/faktorJustering);
		powerConsumption[14] = 0.55 * (residentsFactor/faktorJustering);
		powerConsumption[15] = 0.60 * (residentsFactor/faktorJustering);
		powerConsumption[16] = 1.00;
		powerConsumption[17] = 0.95;
		powerConsumption[18] = 0.67 * (residentsFactor/faktorJustering);
		powerConsumption[19] = 0.65 * (residentsFactor/faktorJustering);
		powerConsumption[20] = 0.63 * (residentsFactor/faktorJustering);
		powerConsumption[21] = 0.61 * (residentsFactor/faktorJustering);
		powerConsumption[22] = 0.58 * (residentsFactor/faktorJustering);
		powerConsumption[23] = 0.50 * (residentsFactor/faktorJustering);
		
		return powerConsumption[time];
	}
	//strømforbruk i kWh, areal i m^2
	public static double getVarmeTap(int stromForbruk, int areal)
	{
		double resultat = 1;
		resultat = (1.5*(stromForbruk/areal));
		return resultat;
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
				//dildo
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
