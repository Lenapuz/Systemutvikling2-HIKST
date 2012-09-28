package com.banan.shared;

public class Bygg {
	private int byggeår, beboere, kvadratmeter;
	private String byggtype;
	private boolean takis, veggis, vinduis;
	private int energiPerKvm;
	
	private int[] varmekildeId;
	private double[] andel;
	
	private String[] varmekilde;
	private double[] varmekildeFaktor;

	//Konstruktør
	public Bygg(int byggeår, boolean takis, boolean veggis, boolean vinduis, int beboere,
			int kvadratmeter, String byggtype, int[] varmekildeId, double[] andel)
	{
		this.byggeår = byggeår;
		this.beboere = beboere;
		this.kvadratmeter = kvadratmeter;
		this.byggtype = byggtype;
		this.takis = takis;
		this.veggis = veggis;
		this.vinduis = vinduis;
		this.varmekildeId = varmekildeId;
		this.andel = andel;
		fyllVarmekilder();
		fyllVarmekildeFaktor();		
	}
	
	//Get og set for lokale variabler
	public int getByggeår() {
		return byggeår;
	}
	public void setByggeår(int byggeår) {
		this.byggeår = byggeår;
	}
	public int getBeboere() {
		return beboere;
	}
	public void setBeboere(int beboere) {
		this.beboere = beboere;
	}
	public int getKvadratmeter() {
		return kvadratmeter;
	}
	public void setKvadratmeter(int kvadratmeter) {
		this.kvadratmeter = kvadratmeter;
	}	
	public String getByggtype() {
		return byggtype;
	}
	public void setByggtype(String byggtype) {
		this.byggtype = byggtype;
	}
	public boolean isTakis() {
		return takis;
	}
	public void setTakis(boolean takis) {
		this.takis = takis;
	}
	public boolean isVeggis() {
		return veggis;
	}
	public void setVeggis(boolean veggis) {
		this.veggis = veggis;
	}
	public boolean isVinduis() {
		return vinduis;
	}
	public void setVinduis(boolean vinduis) {
		this.vinduis = vinduis;
	}

	
	public double kalkulerTotalForbruk(int varmekildeId, double andel)
	{
		double d = varmekildeFaktor[varmekildeId];
		d *= andel;
		return d;
	}
	public double bruktKWperTime()
	{
		double kwh = 114;
		kwh *= byggårForbruksFaktor(byggeår);
		kwh *= beboereForbruksFaktor(beboere);
		kwh *= kvadratmeter;
		kwh *= gjennomsnittligVKFaktor();
		return kwh;
	}
	public double varmeKildeForbruksFaktor()
	{
		return 0;
	}
	public double byggårForbruksFaktor(int byggår)
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
	public double beboereForbruksFaktor(int beboere)	
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
	private void fyllVarmekilder()
	{
		varmekilde = new String [5];
		varmekilde[0] = "Varmepumpe";
		varmekilde[1] = "Vedovn";
		varmekilde[2] = "Panelovn";
		varmekilde[3] = "Gassovn";
		varmekilde[4] = "Oljeovn";
	}
	private void fyllVarmekildeFaktor()
	{
		varmekildeFaktor = new double[5];
		varmekildeFaktor[0] = 0.5; //varmepumpe
		varmekildeFaktor[1] = 0; //"Vedovn";
		varmekildeFaktor[2] = 1;	//"Panelovn";
		varmekildeFaktor[3] = 0; //"Gassovn";
		varmekildeFaktor[4] = 0.9;	//"Oljeovn";
	}
	public double gjennomsnittligVKFaktor()
	{
		int varmekildeAntall = varmekildeId.length;
		double totalForbruk = 0;
		for(int i = 0;i<varmekildeAntall;i++)
		{
			totalForbruk+= kalkulerTotalForbruk(varmekildeId[i], andel[i]);
		}
		return totalForbruk;
	}
	
	
	
	
	
	
	
	
	
}
