package com.banan.shared;

import java.io.Serializable;

/***
 * Klasen er laget på samme måte som User.java.class. 
 * @author Martin
 * Profile brukes for å importere ting til ProfileServiceImpl gjennom profileService
 */


@SuppressWarnings("serial")
public class Profile implements Serializable {
	
	//private String profilID;
	private String name;
	private String buildYear;
	private String statusMessage;
	private String typeProfile;
	private String primHeating;
	private String isIsolated;
	private String houseResidents;
	private String houseSize;

	//konstruktør
	public Profile(){
		typeProfile ="";
		primHeating="";
		isIsolated="";
	}

	public Profile(String name, String buildYear, String typeProfile, String primHeating, String isIsolated, String houseResidents, String houseSize)
	{
		this.name = name;		
		this.buildYear = buildYear;
		this.typeProfile = typeProfile;
		this.primHeating = primHeating;
		this.isIsolated = isIsolated;
		this.houseResidents = houseResidents;
		this.houseSize = houseSize;
	}
	
	public void setTypePofile(String typeProfile)
	{
		this.typeProfile = typeProfile;
	}
	
	public void setPrimHeating(String primHeating)
	{
		this.primHeating = primHeating;
	}
	
	public void setIsisolated(String isIsolated)
	{
		this.isIsolated = isIsolated;
	}
	
	public int getID()
	{
		return 0;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getTypePofile()
	{
		return typeProfile;
	}
	
	public String getPrimHeating()
	{
		return primHeating;
	}
	
	public String getIsisolated()
	{
		return isIsolated;
	}
	
	public String getBuildYear(){
		
		return buildYear;
	}
	
	public String getHouseResidents(){
		
		return houseResidents;
	}
	
	public String getHouseSize(){
		return houseSize;
	}
	
/*	public String getHouseSize(){
		return houseSize;
	}*/
	
	public String getStatusMessage()
	{
		return statusMessage;
	}
	
	public void setStatusMessage(String message) {
		// TODO Auto-generated method stub
		
		statusMessage = message;
		
	}
		
}
