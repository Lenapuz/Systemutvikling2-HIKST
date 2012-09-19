package com.banan.shared;

public class Testklasse {
	public static void main(String [] args){
		//7777777DIL Bygg b = new Bygg(1999, 0, 1, 2, true, true, true, 2, 150, "dildo");
		
		
		Bygg c = new Bygg(1999,0,1,2,true,false,true,2,200,"mordi");
		System.out.println(c.bruktKWperTime() + " kW/h");
		System.out.println(c.kalkulerTotalForbruk(0, 0.4));
		
	}
	
	

}
