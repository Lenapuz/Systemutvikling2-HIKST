package com.banan.shared;

public class Testklasse {
	public static void main(String [] args){
		//7777777DIL Bygg b = new Bygg(1999, 0, 1, 2, true, true, true, 2, 150, "dildo");
		
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
	
	

}
