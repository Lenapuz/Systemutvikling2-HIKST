package com.banan.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.banan.server.*;
import com.banan.shared.Bygg;
import com.banan.shared.Profile;
import com.banan.shared.Heatsource;
public class Testklasse {
	public static void main(String [] args) throws SQLException{
		//7777777DIL Bygg b = new Bygg(1999, 0, 1, 2, true, true, true, 2, 150, "dildo");
		
		//Hente fra Database
		//----------------
		
		ProfileServiceImpl p = new ProfileServiceImpl();
		final Profile pr;
		Profile[] profiler = p.getProfiles();
		Heatsource[] varmekilder = p.getHeatsources();
		
		System.out.println("---------");
		System.out.println("");
		System.out.println("ALLE FRA DATABASE: ");
		
		
		for(int i=0;i<profiler.length;i++)
		{
			System.out.println(varmekilder[i]);
		}
		//Heatsource h1 = new Heatsource(0,"Varmepumpe",0.5,0.3);
		
		
		
		
		
		
		
		
		
		//-----------------------------
		
		int[] varmekildeId = new int[3];
		double[] andel = new double[3]; 
		
		varmekildeId[0] = 0;
		andel[0] = 0.5;
		
		varmekildeId[1] = 2;
		andel[1] = 0.4;
		
		varmekildeId[2] = 4;
		andel[2] = 0.1;
		
		
		Bygg c = new Bygg(1999,true,false,true,1,18,"asbjørn",varmekildeId, andel);
		//Bygg d = new Bygg(1999,true, true, true, 2, 200, " seriøst",
		System.out.println(c.bruktKWperTime() + " kWh/m^2");
		System.out.println("Tits: " + c.gjennomsnittligVKFaktor());
		/*System.out.println(c.kalkulerTotalForbruk(0, 0.4));
		System.out.println(c.kalkulerTotalForbruk(1, 0.4));
		System.out.println(c.kalkulerTotalForbruk(2, 0.2));*/
		//System.out.println(c.bruktKWperTime() * (c.kalkulerTotalForbruk(0, 0.4) + 
		//		c.kalkulerTotalForbruk(1, 0.2) + c.kalkulerTotalForbruk(2, 0.4)));
		
	}
	/*
	public static void printArray(F<>)
	{
		for(int i=0;i<profiler.length;i++)
		{
			System.out.println(varmekilder[i]);
		}
	}*/
	
}
	


