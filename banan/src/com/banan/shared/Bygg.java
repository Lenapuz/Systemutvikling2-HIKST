package com.banan.shared;

public class Bygg {
	private int byggeår, beboere, kvadratmeter, hovedVKId, sekundærVKId, tertiærVKId;
	private String hoved, sekundær, tertiær, byggtype;
	private boolean takis, veggis, vinduis;
	private int energiPerKvm;
	
	String[] varmekilde;
	double[] varmekildeFaktor;

	public Bygg(int byggeår, int hoved, int sekundær, int tertiær, 
			boolean takis, boolean veggis, boolean vinduis, int beboere,
			int kvadratmeter, String byggtype)
	{
		this.byggeår = byggeår;
		this.beboere = beboere;
		this.kvadratmeter = kvadratmeter;
		this.hovedVKId = hoved;
		this.sekundærVKId = sekundær;
		this.tertiærVKId = tertiær;
		this.byggtype = byggtype;
		this.takis = takis;
		this.veggis = veggis;
		this.vinduis = vinduis;
		fyllVarmekilder();
		fyllVarmekildeFaktor();
		fyllVKStrings();
	}
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
	public String getHoved() {
		return hoved;
	}
	public void setHoved(String hoved) {
		this.hoved = hoved;
	}
	public String getSekundær() {
		return sekundær;
	}
	public void setSekundær(String sekundær) {
		this.sekundær = sekundær;
	}
	public String getTertiær() {
		return tertiær;
	}
	public void setTertiær(String tertiær) {
		this.tertiær = tertiær;
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
		double kwh = 1;
		kwh *= byggårForbruksFaktor(byggeår);
		kwh *= beboereForbruksFaktor(beboere);
		kwh *= kvadratmeter;
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
	private void fyllVKStrings()
	{		
		hoved = varmekilde[hovedVKId];
		sekundær = varmekilde[sekundærVKId];
		tertiær = varmekilde[tertiærVKId];
	}
}
