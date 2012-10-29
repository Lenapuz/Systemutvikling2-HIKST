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
	//koefissienter bør kunne finjusteres fra admin panel
	//tatt fra lærebøker om termodynamikk
	private static final double koefissientVarmetapGamleHus = 0.50;
	private static final double koefissientVarmetapMiddelsHus = 0.45;
	private static final double koefissientVarmetapNyeHus = 0.40;
	
	//Gjennomsnittlig oppvarmingsbehov per kvm
	private static final int gjsnittligForbrukPrKvm = 35;
	
	public SimServiceImpl()
	{
		db = new Database("kark.hin.no/gruppe16", "gruppe16", "php@hin-16");
	}
	
	//TODO: void newSimResult(int[] result, int profil_id) legge inn nytt resultat
	//
	
	//Selve kalkuleringsmetoden
	public SimResult simulate(int profileID, int temperatur)
	{		
		ProfileServiceImpl psim = new ProfileServiceImpl();
		Profile p = psim.getProfileByProfileId(profileID);
		Integer[] resultat = new Integer[24];
		int tempInne = 20;
		int tempUte = temperatur;
		int deltaTemp = (tempInne - tempUte);
		
		
		
		//I denne løkka gjøres simuleringen
		for (int i = 0; i < 24; i++)
		{
			int res = gjsnittligForbrukPrKvm * Integer.parseInt(p.getHouseSize());
			//int res = gjsnittligForbrukPrKvm;
			//res*= byggårForbruksFaktor(Integer.parseInt(p.getBuildYear()));
			//res*= beboereForbruksFaktor(Integer.parseInt(p.getHouseResidents()));
			res *= this.hourlyPowerConsumption(i, Integer.parseInt(p.getHouseResidents()));
			
			//res += getOppvarmingsForbruk(Integer.parseInt(p.getHouseSize()), deltaTemp, Integer.parseInt(p.getBuildYear()));
			
			//Hvis hus størrelse er lik 0, ingen utregning
			if (Integer.parseInt(p.getHouseSize()) == 0)
			{
				resultat[i] = 0;
			} 
			else 
			{		
				resultat[i] = res;
			}
			res *= Integer.parseInt(p.getHouseSize());
		}
		
		SimResult result = new SimResult(0,p.getID(),resultat);	
		
		this.registerSimResult(result);
		
		return result;
	}
	
	//Faktorer til bruk i utregning
	/*public static double byggårForbruksFaktor(int byggår)
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
	//ikke i bruk
	public static double beboereForbruksFaktor(int beboere)	
	{
		if (beboere == 1)
		{
			return 1.0;			
		}
		else if(beboere == 2)
		{
			return 1.3;
		}
		else if(beboere == 3)
		{
			return 1.5;
		}
		else if(beboere == 4)
		{
			return 1.65;
		}
		else if(beboere == 5)
		{
			return 1.8;
		}
		else if(beboere == 6)
		{
			return 1.9;
		}
		else if(beboere > 6)
		{
			return 2.0;
		}
		else return 1;
	}*/
	
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
		double beboerFaktorMin = 0;
		double beboerFaktorMax = 0;
		
		if (beboere == 1)
		{
			beboerFaktorMin = 1.0;	
			beboerFaktorMax = 0.9;
		}
		else if (beboere == 2)
		{
			beboerFaktorMin = 1.30;  // gjennomsnitts husstand, Max = Min = Max 
			beboerFaktorMax = 1.30;
		}
		else if (beboere == 3 )
		{
			beboerFaktorMin = 1.55;
			beboerFaktorMax = 1.60;
		}
		else if (beboere == 4)
		{
			beboerFaktorMin = 1.70;
			beboerFaktorMax = 2.05;
		}
		else if (beboere == 5)
		{
			beboerFaktorMin = 1.80;
			beboerFaktorMax = 2.20;
		}
		else {
			beboerFaktorMin =  2.00; 	
			beboerFaktorMax =  2.45;  
		}
		
		// PowerConsumption tall er gitt snitt forbruk av strøm fra time til time. (gjennomsnitts husstand 2 personer)
		
		// beboerFaktorMin - Tiden av døgnet hvor det er minst forskjell mellom en eller flere
		// beboere, gitt i snitt.
		
		// beboerFaktorMax - Tiden av døgnet hvor det er mest forskjell mellom en eller flere
		// beboere, gitt i snitt. 
		powerConsumption[0] = 0.86 * beboerFaktorMin;
		powerConsumption[1] = 0.66 * beboerFaktorMax;
		powerConsumption[2] = 0.61 * beboerFaktorMax;
		powerConsumption[3] = 0.59 * beboerFaktorMax;
		powerConsumption[4] = 0.58 * beboerFaktorMax;
		powerConsumption[5] = 0.59 * beboerFaktorMax;
		powerConsumption[6] = 0.60 * beboerFaktorMax;
		powerConsumption[7] = 0.67 * beboerFaktorMax;
		powerConsumption[8] = 0.89 * beboerFaktorMin;
		powerConsumption[9] = 0.91 * beboerFaktorMin;
		powerConsumption[10] = 0.92 * beboerFaktorMin;
		powerConsumption[11] = 0.90 * beboerFaktorMin;
		powerConsumption[12] = 0.70 * beboerFaktorMax;
		powerConsumption[13] = 0.69 * beboerFaktorMax;
		powerConsumption[14] = 0.69 * beboerFaktorMax;
		powerConsumption[15] = 0.71 * beboerFaktorMax;
		powerConsumption[16] = 0.91 * beboerFaktorMin;
		powerConsumption[17] = 0.95 * beboerFaktorMin;
		powerConsumption[18] = 0.96 * beboerFaktorMin;
		powerConsumption[19] = 0.98 * beboerFaktorMin;
		powerConsumption[20] = 0.99 * beboerFaktorMin;
		powerConsumption[21] = 1.00 * beboerFaktorMin;
		powerConsumption[22] = 0.99 * beboerFaktorMin;
		powerConsumption[23] = 0.95 * beboerFaktorMin;
		
		return powerConsumption[time];
	}
	//strømforbruk i kWh, areal i m^2
	public static double getOppvarmingsForbruk(int areal, int deltaTemp, int byggår)
	{
		double res = areal * deltaTemp;
		
		if (byggår > 1997)
		{		
			return res * koefissientVarmetapNyeHus;
		}
		else if (byggår > 1987 && byggår < 1997)
		{
			return res * koefissientVarmetapMiddelsHus;
		}
		else
		{
			return res * koefissientVarmetapGamleHus;
		}	
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
