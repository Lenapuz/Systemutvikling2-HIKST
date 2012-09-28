package com.banan.shared;

public class Heatsource {
	/*
	 * name part	id	factor

varmepumpe   50			0
vedovn        0			1
panelovn      100		2	
oljeovn        90		3
gassovn        0		4
	 * 
	 */
	private String name;
	private int id;
	private double heatFactor;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getheatFactor() {
		return heatFactor;
	}
	public void setHeatFactor(double factor) {
		this.heatFactor = factor;
	}


	public Heatsource()
	{
		
	}
	public Heatsource(int id, String name, double heatFactor)
	{
		this.id = id;
		this.name = name;
		this.heatFactor = heatFactor;
	}
	public String toString()
	{
		StringBuilder sb = new StringBuilder();				
		sb.append("id: " + id);
		sb.append("\nname: " + name);
		sb.append("\nheatFactor: " + heatFactor+"\n");
		return sb.toString();
	}
}
