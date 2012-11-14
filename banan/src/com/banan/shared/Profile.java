package com.banan.shared;

import java.io.Serializable;

/***
 * Klasen er laget p� samme m�te som User.java.class. 
 * @author Martin
 * Profile brukes for � importere ting til ProfileServiceImpl gjennom profileService
 */


@SuppressWarnings("serial")
public class Profile implements Serializable {
	
	private Integer ID;
	private String name;
	private String buildYear;
	private String statusMessage;
	private String typeProfile; //loft/kjeller
	
	private String primHeating;
	private String primShare;
	private String secHeating;
	private String secShare;
	private String thirdHeating;
	private String thirdShare;
	
	private String isIsolated;
	private String houseResidents;
	private String houseSize;
	
	/*private String FirstName;
	private String MellomNavn;
	private String EtterNavn;
	private String Adresse;
	private String ZipCode;
	private String City;
	private String PhoneNumber;
	private String EmailAddress;*/

	//konstrukt�r
	public Profile(){
		typeProfile ="";
		primHeating="";
		isIsolated="";
	}

	//gammel konstruktør
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
	
	//ny konstruktør med 3 heatsources, bruk denne
	public Profile(String name, String buildYear,String isIsolated, String typeProfile, String primHeating,  String primShare
			, String secHeating, String secShare, String thirdHeating, String thirdShare, String houseResidents, String houseSize)
	{
		this.name = name;		
		this.buildYear = buildYear;
		this.typeProfile = typeProfile;
		
		this.primHeating = primHeating;
		this.primShare = primShare;
		this.secHeating = secHeating;
		this.secShare = secShare;
		this.thirdHeating = thirdHeating;
		this.thirdShare = thirdShare;
		
		this.isIsolated = isIsolated;
		this.houseResidents = houseResidents;
		this.houseSize = houseSize;
	}
	
	
	
	public void setID(int id) {
		this.ID = id;
	}
	
	public int getID() {
		return ID;
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
	
	public String getPrimShare() {
		return primShare;
	}

	public void setPrimShare(String primShare) {
		this.primShare = primShare;
	}

	public String getSecHeating() {
		return secHeating;
	}

	public void setSecHeating(String secHeating) {
		this.secHeating = secHeating;
	}

	public String getSecShare() {
		return secShare;
	}

	public void setSecShare(String secShare) {
		this.secShare = secShare;
	}

	public String getThirdHeating() {
		return thirdHeating;
	}

	public void setThirdHeating(String thirdHeating) {
		this.thirdHeating = thirdHeating;
	}

	public String getThirdShare() {
		return thirdShare;
	}

	public void setThirdShare(String thirdShare) {
		this.thirdShare = thirdShare;
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
	public String toString()
	{
		StringBuilder sb = new StringBuilder();				
		sb.append("Name: " + name);
		sb.append("\nbuildYear: " + buildYear);
		sb.append("\nstatusMessage: " + statusMessage);
		sb.append("\ntypeProfile: " + typeProfile);
		sb.append("\nprimHeating: " + primHeating);
		sb.append("\nisIsolated: " + isIsolated);
		sb.append("\nhouseResidents: " + houseResidents);
		sb.append("\nhouseSize: " + houseSize+"\n");
		
		return sb.toString();
	}

	
	
	
	
	
	
	
	
	
	
	
}
