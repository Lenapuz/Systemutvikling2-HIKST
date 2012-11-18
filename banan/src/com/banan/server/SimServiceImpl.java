package com.banan.server;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import com.banan.shared.*;
import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class SimServiceImpl extends RemoteServiceServlet implements SimService
{
	
	private Database db;	
	//koefissienter bør kunne finjusteres fra admin panel
	//tatt fra lærebøker om termodynamikk
	double gjsnittligForbrukApparater = 1.43; // forklaring se "hourlyPowerConsumption"
	private static int maxHeatSources = 10;
	private static final double koefissientVarmetapGamleHus = 0.50;
	private static final double koefissientVarmetapMiddelsHus = 0.45;
	private static final double koefissientVarmetapNyeHus = 0.40;
	private static final double skaleringsFaktorTemperaturVarmeBehov = -1.2963;
	private static final double varmeBehovVedNullGraderKonstant = 25.9259;
	
		
	//private HashMap<double, Heatsource> hm
	private HashMap<String, Heatsource> hm;
	
	public SimServiceImpl()
	{
		db = new Database("kark.hin.no/gruppe16", "gruppe16", "php@hin-16");
		//hs = new ArrayList<Heatsource>();
		Heatsource[] hsarr;
		hm = new HashMap<String, Heatsource>();
		
		ProfileServiceImpl p = new ProfileServiceImpl();
		hsarr = p.getHeatsources();
		
		for(int i = 0;i<hsarr.length;i++)
		{
			//hs.add(hsarr[i]);
			hm.put(hsarr[i].getName().toLowerCase(),hsarr[i]);
		}
		System.out.println("Hashmap length after add: " + hm.size());
	}
	
	//Selve kalkuleringsmetoden
	//Heatsource h;
	
	//Simuler 1 profil
	public SimResult simulate(int profileID, int temperatur)
	{		
		ProfileServiceImpl psim = new ProfileServiceImpl();
		Profile p = psim.getProfileByProfileId(profileID);
		
		h = (Heatsource)hm.get(p.getPrimHeating().toLowerCase()); 
		
		
		//DEBUGGING
		/*Object o = hm.get("varmepumpe");
		System.out.println("object.class:" + o.getClass());
		if(o == null)
		{
			System.out.println("varmepumpe returnerer null");
		}*/
		//System.out.println(o.toString());
		//System.out.println("Primheating: " + p.getPrimHeating().toString());
		//System.out.println("Primheating: " + Integer.parseInt(p.getPrimHeating()));
		//System.out.println("fra p.getPrimHeating()");
		//System.out.println("h.tostring inc");
		System.out.println("h.toString(): "+ h.toString());
		
		
		Integer[] resultat = new Integer[24];
		double res = 0;
		double sumOppvarmingsForbruk = 0;
		double sumApparaterForbruk = 0;
		
		
		//I enne løkka gjøres simuleringen
		for (int i = 0; i < 24; i++)
		{
			//Hvis hus størrelse er lik 0, ingen utregning
			if (Integer.parseInt(p.getHouseSize()) == 0)
			{
				resultat[i] = 0;
				res = 0;
			} 
			
			else 
			{		
				//oppvarming
				sumOppvarmingsForbruk += getOppvarmingsForbrukPerKvm(temperatur,Integer.parseInt(p.getBuildYear()));  //varmeforbrukperKVM mot varmetaphus
				sumOppvarmingsForbruk *= h.getheatFactor(); //varmefaktor fra oppvarming
				
				
				
				/////////////////
				
				// Kan dette over være en vei å gå? if sjekker mot "noe" som man krysser av (som Aleks sa)?
				
				/*if ("helg")
				{
					sumOppvarmingsForbruk *= this.hourlyHeatingWeekend(i); 
					sumOppvarmingsForbruk *= Integer.parseInt(p.getHouseSize());
					
					sumApparaterForbruk += this.hourlyPowerConsumptionWeekend(i, Integer.parseInt(p.getHouseResidents()));
					sumApparaterForbruk *= gjsnittligForbrukApparater;
				}
				else
				{
					sumOppvarmingsForbruk *= this.hourlyHeating(i);
					sumOppvarmingsForbruk *= Integer.parseInt(p.getHouseSize());
					
					sumApparaterForbruk += this.hourlyPowerConsumption(i, Integer.parseInt(p.getHouseResidents()));	
					sumApparaterForbruk *= gjsnittligForbrukApparater; 
				}
				res+= sumOppvarmingsForbruk;
				res+= sumApparaterForbruk;*/
				
				
				//////////////////
				
				sumOppvarmingsForbruk *= this.hourlyHeating(i); // Endringer i oppvarming gjennom døgnet
				sumOppvarmingsForbruk *= Integer.parseInt(p.getHouseSize()); // kvm
				
				//andre strømforbrukende artikler
				sumApparaterForbruk += this.hourlyPowerConsumption(i, Integer.parseInt(p.getHouseResidents()));	// Endringer i lys og el.app. bruk gjennom døgnet 
				sumApparaterForbruk *= gjsnittligForbrukApparater; // forklaring se "hourlyPowerConsumption"  
				
				//summerer alt som resultat
				res+= sumOppvarmingsForbruk;
				res+= sumApparaterForbruk;
				
				
				resultat[i] = (int)res;
				sumApparaterForbruk = 0;
				sumOppvarmingsForbruk = 0;
				res = 0;	
			}		
				
		}		
		
		SimResult result = new SimResult(0,p.getID(),resultat);	
		
		this.registerSimResult(result);
		
		return result;
	}
	
	//Simuler flere profiler
	//ikke testet enda men har chuck norris seal of approval
	public SimResult[] simulate(int profileID[], int temperatur)
	{		
		ProfileServiceImpl psim = new ProfileServiceImpl();
		Profile p;
		ArrayList<Profile> profiler = new ArrayList<Profile>();
		ArrayList<Heatsource> varmekilder = new ArrayList<Heatsource>();
		ArrayList<SimResult> resultater = new ArrayList<SimResult>();
		//private ArrayList list;
		//= psim.getProfileByProfileId(profileID);
		for(int i = 0;i<profileID.length;i++)
		{
			p = psim.getProfileByProfileId(profileID[i]);
			profiler.add(p);
			h = (Heatsource)hm.get(p.getPrimHeating().toLowerCase()); 
			varmekilder.add(h);
		}
		
		
		
		
		//DEBUGGING
		/*Object o = hm.get("varmepumpe");
		System.out.println("object.class:" + o.getClass());
		if(o == null)
		{
			System.out.println("varmepumpe returnerer null");
		}*/
		//System.out.println(o.toString());
		//System.out.println("Primheating: " + p.getPrimHeating().toString());
		//System.out.println("Primheating: " + Integer.parseInt(p.getPrimHeating()));
		//System.out.println("fra p.getPrimHeating()");
		//System.out.println("h.tostring inc");
		System.out.println("h.toString(): "+ h.toString());
		
		
		Integer[] resultat = new Integer[24];
		double res = 0;
		double sumOppvarmingsForbruk = 0;
		double sumApparaterForbruk = 0;
		
		for(int j = 0;j<profiler.size();j++)
		{
			p = profiler.get(j);
		
			//I enne løkka gjøres simuleringen
			for (int i = 0; i < 24; i++)
			{
				//Hvis hus størrelse er lik 0, ingen utregning
				if (Integer.parseInt(p.getHouseSize()) == 0)
				{
					resultat[i] = 0;
					res = 0;
				} 
				
				else 
				{		
					//oppvarming
					sumOppvarmingsForbruk += getOppvarmingsForbrukPerKvm(temperatur,Integer.parseInt(p.getBuildYear()));  //varmeforbrukperKVM mot varmetaphus
					sumOppvarmingsForbruk *= varmekilder.get(j).getheatFactor(); //varmefaktor fra oppvarming
					
					
					
					/////////////////
					
					// Kan dette over være en vei å gå? if sjekker mot "noe" som man krysser av (som Aleks sa)?
					
					/*if ("helg")
					{
						sumOppvarmingsForbruk *= this.hourlyHeatingWeekend(i); 
						sumOppvarmingsForbruk *= Integer.parseInt(p.getHouseSize());
						
						sumApparaterForbruk += this.hourlyPowerConsumptionWeekend(i, Integer.parseInt(p.getHouseResidents()));
						sumApparaterForbruk *= gjsnittligForbrukApparater;
					}
					else
					{
						sumOppvarmingsForbruk *= this.hourlyHeating(i);
						sumOppvarmingsForbruk *= Integer.parseInt(p.getHouseSize());
						
						sumApparaterForbruk += this.hourlyPowerConsumption(i, Integer.parseInt(p.getHouseResidents()));	
						sumApparaterForbruk *= gjsnittligForbrukApparater; 
					}
					res+= sumOppvarmingsForbruk;
					res+= sumApparaterForbruk;*/
					
					
					//////////////////
					
					sumOppvarmingsForbruk *= this.hourlyHeating(i); // Endringer i oppvarming gjennom døgnet
					sumOppvarmingsForbruk *= Integer.parseInt(p.getHouseSize()); // kvm
					
					//andre strømforbrukende artikler
					sumApparaterForbruk += this.hourlyPowerConsumption(i, Integer.parseInt(p.getHouseResidents()));	// Endringer i lys og el.app. bruk gjennom døgnet 
					sumApparaterForbruk *= gjsnittligForbrukApparater; // forklaring se "hourlyPowerConsumption"  
					
					//summerer alt som resultat
					res+= sumOppvarmingsForbruk;
					res+= sumApparaterForbruk;
					
					
					resultat[i] = (int)res;
					sumApparaterForbruk = 0;
					sumOppvarmingsForbruk = 0;
					res = 0;	
				}		
					
			}		
		
		SimResult result = new SimResult(0,p.getID(),resultat);	
		
		this.registerSimResult(result);
		resultater.add(result);
		
		}
		return resultater.toArray(new SimResult[resultater.size()]);
	}
	
	public static int tempToCelsius(int tempKelvin)
	{
		int res = tempKelvin - 273;
		return res;
	}
	public static int tempToKelvin(int tempCelsius)
	{
		int res = tempCelsius + 273;
		return res;
	}
	
	public double hourlyHeating(int time)
	{
		double[] heating = new double[24];
		heating[0] = 0.75;
		heating[1] = 0.75;
		heating[2] = 0.75;
		heating[3] = 0.75;
		heating[4] = 0.75;
		heating[5] = 0.75;
		heating[6] = 0.75;
		heating[7] = 0.85;
		heating[8] = 1.00;
		heating[9] = 1.00;
		heating[10] = 1.00;
		heating[11] = 1.00;
		heating[12] = 1.00;
		heating[13] = 1.00;
		heating[14] = 1.00;
		heating[15] = 1.00;
		heating[16] = 1.00;
		heating[17] = 1.00;
		heating[18] = 1.00; 
		heating[19] = 1.00;
		heating[20] = 1.00;
		heating[21] = 1.00;
		heating[22] = 1.00;
		heating[23] = 0.85;
		
		return heating[time];
	}
	
	public double hourlyHeatingWeekend(int time) //fredag, lørdag og søndag
	{
		double[] heatingWeekend = new double[24];
		heatingWeekend[0] = 1.00;
		heatingWeekend[1] = 0.85;
		heatingWeekend[2] = 0.75;
		heatingWeekend[3] = 0.75;
		heatingWeekend[4] = 0.75;
		heatingWeekend[5] = 0.75;
		heatingWeekend[6] = 0.75;
		heatingWeekend[7] = 0.75;
		heatingWeekend[8] = 0.75;
		heatingWeekend[9] = 0.85;
		heatingWeekend[10] = 1.00;
		heatingWeekend[11] = 1.00;
		heatingWeekend[12] = 1.00;
		heatingWeekend[13] = 1.00;
		heatingWeekend[14] = 1.00;
		heatingWeekend[15] = 1.00;
		heatingWeekend[16] = 1.00;
		heatingWeekend[17] = 1.00;
		heatingWeekend[18] = 1.00; 
		heatingWeekend[19] = 1.00;
		heatingWeekend[20] = 1.00;
		heatingWeekend[21] = 1.00;
		heatingWeekend[22] = 1.00;
		heatingWeekend[23] = 1.00;
		
		return heatingWeekend[time];
	}
	
	// ca. Snitt i strømforbruk 2000w per time for 2,2 personer. gir 680w i el.apparater (36% jamfør NVE energirap. 2012).  
	// beboere == 2; sammenregnet døgn gir 16320 / 10383,6 Power.Con. = 1.57.  snitt av døgnet skal være 680w på 2.2 beboere,       
	// 1.57 / (2.2 beboere / 2 beboere) = 1.43
	// legge til faktor "gjsnittligForbrukApparater" = 1.43
	public double hourlyPowerConsumption(int time, int beboere)
	{
		double[] powerConsumption = new double[24];
		double beboerFaktorMin = 0;
		double beboerFaktorMax = 0;
	
	if (beboere == 1)
	{
		beboerFaktorMin = 550;	
		beboerFaktorMax = 450;
	}
	else if (beboere == 2)
	{
		beboerFaktorMin = 680;  // gjennomsnitts husstand, Max = Min = Max 
		beboerFaktorMax = 680;	// 680 watt lys&el.app. 34% av 2000w (ca.snitt "ssb" per time)
	}
	else if (beboere == 3 )
	{
		beboerFaktorMin = 890;
		beboerFaktorMax = 950;
	}
	else if (beboere == 4)
	{
		beboerFaktorMin = 1000;
		beboerFaktorMax = 1300;
	}
	else if (beboere == 5)
	{
		beboerFaktorMin = 1100;
		beboerFaktorMax = 1450;
	}
	else {
		beboerFaktorMin =  1200; 	
		beboerFaktorMax =  1700;  
	}
		
		// PowerConsumption tall er gitt snitt forbruk av strøm fra time til time. (gjennomsnitts husstand 2 personer)
		
		// beboerFaktorMin - Tiden av døgnet hvor det er minst forskjell mellom en eller flere
		// beboere, gitt i snitt.
		
		// beboerFaktorMax - Tiden av døgnet hvor det er mest forskjell mellom en eller flere
		// beboere, gitt i snitt. 
		powerConsumption[0] = 0.72 * beboerFaktorMin;
		powerConsumption[1] = 0.38 * beboerFaktorMax;
		powerConsumption[2] = 0.33 * beboerFaktorMax;
		powerConsumption[3] = 0.24 * beboerFaktorMax;
		powerConsumption[4] = 0.23 * beboerFaktorMax;
		powerConsumption[5] = 0.24 * beboerFaktorMax;
		powerConsumption[6] = 0.32 * beboerFaktorMax;
		powerConsumption[7] = 0.39 * beboerFaktorMax;
		powerConsumption[8] = 0.75 * beboerFaktorMin;
		powerConsumption[9] = 0.84 * beboerFaktorMin;
		powerConsumption[10] = 0.85 * beboerFaktorMin;
		powerConsumption[11] = 0.83 * beboerFaktorMin;
		powerConsumption[12] = 0.59 * beboerFaktorMax;
		powerConsumption[13] = 0.52 * beboerFaktorMax;
		powerConsumption[14] = 0.51 * beboerFaktorMax;
		powerConsumption[15] = 0.58 * beboerFaktorMax;
		powerConsumption[16] = 0.84 * beboerFaktorMin;
		powerConsumption[17] = 0.88 * beboerFaktorMin;
		powerConsumption[18] = 0.89 * beboerFaktorMin;
		powerConsumption[19] = 0.91 * beboerFaktorMin;
		powerConsumption[20] = 0.92 * beboerFaktorMin;
		powerConsumption[21] = 0.96 * beboerFaktorMin;
		powerConsumption[22] = 0.92 * beboerFaktorMin;
		powerConsumption[23] = 0.88 * beboerFaktorMin;
		
		return powerConsumption[time];
	}
	
	public double hourlyPowerConsumptionWeekend(int time, int beboere)
	{
		double[] powerConsumptionWeekend = new double[24];
		double beboerFaktorMin = 0;
		double beboerFaktorMax = 0;
	
	if (beboere == 1)
	{
		beboerFaktorMin = 550;	
		beboerFaktorMax = 450;
	}
	else if (beboere == 2)
	{
		beboerFaktorMin = 680; 
		beboerFaktorMax = 680;	
	}
	else if (beboere == 3 )
	{
		beboerFaktorMin = 890;
		beboerFaktorMax = 950;
	}
	else if (beboere == 4)
	{
		beboerFaktorMin = 1000;
		beboerFaktorMax = 1300;
	}
	else if (beboere == 5)
	{
		beboerFaktorMin = 1100;
		beboerFaktorMax = 1450;
	}
	else {
		beboerFaktorMin =  1200; 	
		beboerFaktorMax =  1700;  
	}
		 
		powerConsumptionWeekend[0] = 0.85 * beboerFaktorMin;
		powerConsumptionWeekend[1] = 0.50 * beboerFaktorMax;
		powerConsumptionWeekend[2] = 0.39 * beboerFaktorMax;
		powerConsumptionWeekend[3] = 0.26 * beboerFaktorMax;
		powerConsumptionWeekend[4] = 0.24 * beboerFaktorMax;
		powerConsumptionWeekend[5] = 0.23 * beboerFaktorMax;
		powerConsumptionWeekend[6] = 0.32 * beboerFaktorMax;
		powerConsumptionWeekend[7] = 0.33 * beboerFaktorMax;
		powerConsumptionWeekend[8] = 0.49 * beboerFaktorMax;
		powerConsumptionWeekend[9] = 0.85 * beboerFaktorMin;
		powerConsumptionWeekend[10] = 0.86 * beboerFaktorMin;
		powerConsumptionWeekend[11] = 0.85 * beboerFaktorMin;
		powerConsumptionWeekend[12] = 0.51 * beboerFaktorMax;
		powerConsumptionWeekend[13] = 0.52 * beboerFaktorMax;
		powerConsumptionWeekend[14] = 0.59 * beboerFaktorMax;
		powerConsumptionWeekend[15] = 0.60 * beboerFaktorMax;
		powerConsumptionWeekend[16] = 0.89 * beboerFaktorMin;
		powerConsumptionWeekend[17] = 0.90 * beboerFaktorMin;
		powerConsumptionWeekend[18] = 0.92 * beboerFaktorMin;
		powerConsumptionWeekend[19] = 0.95 * beboerFaktorMin;
		powerConsumptionWeekend[20] = 0.96 * beboerFaktorMin;
		powerConsumptionWeekend[21] = 1.00 * beboerFaktorMin;
		powerConsumptionWeekend[22] = 0.96 * beboerFaktorMin;
		powerConsumptionWeekend[23] = 0.90 * beboerFaktorMin;
		
		return powerConsumptionWeekend[time];
	}
	
	//strømforbruk i kWh/m^2
	//bare nøyaktig for temp [-50,20]
	public static double getOppvarmingsForbrukPerKvm(int uteTemp, int byggår)
	{
		if(uteTemp<20 && uteTemp >-50)
		{
			double res = skaleringsFaktorTemperaturVarmeBehov*uteTemp+varmeBehovVedNullGraderKonstant;
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
		else 
			return 0;
		
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

	//støttemetoder
	
	//denne må fikses
	public double getHeatSourceByName(String name)
	{
		Heatsource htemp = hm.get(name);
		return htemp.getheatFactor();	
	}
	
	@Override
	public void purgeIt() {
		try
		{
			this.db.connect();
			Statement statement = db.createStatement();
			String query = "DELETE FROM result";
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
	
	//arvid debugging
	static Heatsource h = new Heatsource(0, "Varmepumpe", 0.90);
	
	public static void main(String[] args)
	{
		SimServiceImpl s = new SimServiceImpl();
		s.simulate(74, 20);
		
	}
}
