package com.banan.shared;

import java.io.Serializable;

/***
 * Klasen er laget p� samme m�te som User.java.class. 
 * @author Martin
 * Profile brukes for � importere ting til ProfileServiceImpl gjennom profileService
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
	
	private String FirstName;
	private String MellomNavn;
	private String EtterNavn;
	private String Adresse;
	private String ZipCode;
	private String City;
	private String PhoneNumber;
	private String EmailAddress;

	//konstrukt�r
	public Profile(){
		typeProfile ="";
		primHeating="";
		isIsolated="";
	}

	public Profile(String name, String buildYear,String isIsolated, String typeProfile, String primHeating,  String houseResidents, String houseSize)
	{
		this.name = name;		
		this.buildYear = buildYear;
		this.typeProfile = typeProfile;
		this.primHeating = primHeating;
		this.isIsolated = isIsolated;
		this.houseResidents = houseResidents;
		this.houseSize = houseSize;
	}
	
	/*public Profile(String Firstname, String MellomNavn, String EtterNavn, String Adresse, String ZipCode, String City, String PhoneNumber, String EmailAddress)
	{
		this.FirstName = Firstname;
		this.MellomNavn = MellomNavn;
		this.EtterNavn = EtterNavn;
		this.Adresse = Adresse;
		this.ZipCode = ZipCode;
		this.City = City;
		this.PhoneNumber = PhoneNumber;
		this.EmailAddress = EmailAddress;
		
	}*/
	

	
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
	
	/*public String getFirstname()
	{
		return FirstName;
	}
	
	public String getMiddleName()
	{
		return MellomNavn;
	}
	
	public String getLastName()
	{
		return EtterNavn;
	}
	
	public String getAdress()
	{
		return Adresse;
	}
	
	public String getZipCode()
	{
		return ZipCode;
	}
	
	public String getCity()
	{
		return City;
	}
	
	public String getPhoneNumber()
	{
		return PhoneNumber;
	}
	
	public String getEmailAddress()
	{
		return EmailAddress;
	}*/
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
