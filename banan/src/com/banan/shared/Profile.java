package com.banan.shared;

import java.io.Serializable;

/***
 * Klasen er laget p� samme m�te som User.java.class. 
 * @author Martin
 * Profile brukes for � importere ting til ProfileServiceImpl gjennom profileService
 */


@SuppressWarnings("serial")
public class Profile implements Serializable {
	
	private String profilID;
	private String buildYear;
	private String houseSize;
	private String statusMessage;
	
	
	//konstrukt�r
	public Profile(){
		
	}
	

	public Profile(String profilID,String buildYear, String houseSize)
	{
		
		this.profilID = profilID;
		this.buildYear = buildYear;
		this.houseSize = houseSize;
	}
	
	

	public String getProfilID()
	{
		return profilID;
	}
	
	public String getBuildYear(){
		
		return buildYear;
	}
	
	public String getHouseSize(){
		return houseSize;
	}
	
	public String getStatusMessage()
	{
		return statusMessage;
	}
	
	public void setStatusMessage(String message) {
		// TODO Auto-generated method stub
		
		statusMessage = message;
		
	}
		
}
