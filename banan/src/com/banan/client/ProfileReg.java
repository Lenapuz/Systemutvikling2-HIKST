package com.banan.client;

import java.util.ArrayList;


import com.banan.shared.Profile;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

/***
 * Classe for skjemabiten for � registrere profil. denne vil da bli brukt p� annen m�te!
 * men utgangspunktet er der � kan endres p�.
 * @author Martin
 *
 */
public class ProfileReg extends Composite {
	
	//Array
	private ArrayList<ActionHandler> profilHandlers = new ArrayList<ActionHandler>();
	
	//Global private variables
	private VerticalPanel panel;
	
	//Buttons
	private Button buttonRegisterProfile;
	
	//private textbox
	private TextBox textBoxName;
	private TextBox	textBoxBuildYear;
	private TextBox	textBoxHouseSize;
	private TextBox textBoxHouseResidents;
	
	private TextBox textBoxFirstName;
	private TextBox textBoxMiddleName;
	private TextBox textBoxLastName;
	private TextBox textBoxAdresse;
	private TextBox textBoxZipCode;
	private TextBox textBoxCity;
	private TextBox textBoxPhoneNumber;
	private TextBox textBoxEmailAdresse;
	private TextBox textHeatProsent;
	
	 
	//ListBoxs
	private ListBox listBoxSelect;
    private ListBox listBoxType;
    private ListBox listBoxIsolated;
    
    
	
  
    /***
     * Konstructør som Definerer div elementer-
     */
	public ProfileReg(){
		
		panel = new VerticalPanel();
		panel.addStyleName("register");
		initWidget(panel);
		
		Label labelMellomRom = new Label("-------------------------------------------------------------");
		labelMellomRom.setWidth("250px");
		
		Label labelFirstName = new Label("Fornavn:");
		labelFirstName.setWidth("80px");
		textBoxFirstName = new TextBox();
		
		Label labelMiddleName = new Label("Mellom navn:");
		labelMiddleName.setWidth("80px");
		textBoxMiddleName = new TextBox();
		
		Label labelLastName = new Label("Etternavn:");
		labelLastName.setWidth("80px");
		textBoxLastName = new TextBox();
		
		Label labelAdresse = new Label("Adresse");
		labelAdresse.setWidth("80px");
		textBoxAdresse = new TextBox();
		
		Label labelZipCode = new Label("Zip code:");
		labelZipCode.setWidth("80px");
		textBoxZipCode = new TextBox();
		
		Label labelCity = new Label("By");
		labelCity.setWidth("80px");
		textBoxCity = new TextBox();
		
		Label labelPhoneNumber = new Label("Telefon:");
		labelPhoneNumber.setWidth("80px");
		textBoxPhoneNumber = new TextBox();
		
		Label labelEmailAdresse = new Label("E-post:");
		labelEmailAdresse.setWidth("80px");
		textBoxEmailAdresse = new TextBox();
		
		Label labelName = new Label("Navn:");
		labelName.setWidth("80px");
		textBoxName = new TextBox();
		
		Label labelBuildYear = new Label("Byggeår:");
		labelBuildYear.setWidth("80px");
		textBoxBuildYear = new TextBox();
		
		Label labelHouseSize = new Label("Størrelse:");
		labelHouseSize.setWidth("80px");
		textBoxHouseSize = new TextBox();
		Label labelHouseResidents = new Label("Beboere:");
		labelHouseResidents.setWidth("80px");
		textBoxHouseResidents = new TextBox();
		
		//listbox add Items 
		Label labelSelect = new Label("Primær:");
		labelSelect.setWidth("80px");
		listBoxSelect = new ListBox();
		listBoxSelect.addItem("N/A");
		listBoxSelect.addItem("Varmepumpe");
		listBoxSelect.addItem("Oljefyring");
		listBoxSelect.addItem("Sentralvarme");
		textHeatProsent = new TextBox();
		
		
		//list box
		Label labelIsolated = new Label("Isolert:");
		labelIsolated.setWidth("80px");
		listBoxIsolated = new ListBox();
		listBoxIsolated.addItem("Ja");
		listBoxIsolated.addItem("Nei");
		
		Label labelType = new Label("Boligtype:");
		labelType.setWidth("80px");
		listBoxType = new ListBox();
		listBoxType.addItem("Enebolig");
		listBoxType.addItem("Tomanns Bolig");
		listBoxType.addItem("Leilighet");
		listBoxType.addItem("Industripark");
		
		//buttons
		buttonRegisterProfile = new Button("Registrer");
		buttonRegisterProfile.addStyleName("btn");
		
		//Set pref
		listBoxSelect.setWidth("164px");
		listBoxIsolated.setWidth("164px");
		listBoxType.setWidth("164px");
		textBoxName.setWidth("150px");
		textBoxBuildYear.setWidth("150px");
		textBoxHouseResidents.setWidth("150px");
		textBoxHouseSize.setWidth("150px");
		buttonRegisterProfile.setWidth("164px");
		textBoxFirstName.setWidth("164px");
		textBoxMiddleName.setWidth("164px");
		textBoxLastName.setWidth("164px");
		textBoxAdresse.setWidth("164px");
		textBoxZipCode.setWidth("164px");
		textBoxCity.setWidth("164px");
		textBoxPhoneNumber.setWidth("164px");
		textBoxEmailAdresse.setWidth("164px");
	    
		//ny greier
		HorizontalPanel p1 = new HorizontalPanel();
		
		
		
		/*p1.add(labelFirstName);
		p1.add(textBoxFirstName);
		panel.add(p1);
		p1 = new HorizontalPanel();
		
		p1.add(labelMiddleName);
		p1.add(textBoxMiddleName);
		panel.add(p1);
		p1 = new HorizontalPanel();
		
		p1.add(labelLastName);
		p1.add(textBoxLastName);
		panel.add(p1);
		p1 = new HorizontalPanel();
		
		p1.add(labelAdresse);
		p1.add(textBoxAdresse);
		panel.add(p1);
		p1 = new HorizontalPanel();
		
		p1.add(labelZipCode);
		p1.add(textBoxZipCode);
		panel.add(p1);
		p1 = new HorizontalPanel();
		
		p1.add(labelCity);
		p1.add(textBoxCity);
		panel.add(p1);
		p1 = new HorizontalPanel();
		
		p1.add(labelPhoneNumber);
		p1.add(textBoxPhoneNumber);
		panel.add(p1);
		p1 = new HorizontalPanel();
		
		p1.add(labelEmailAdresse);
		p1.add(textBoxEmailAdresse);
		panel.add(p1);
		p1 = new HorizontalPanel();*/
		
		
		//andre greier
		HorizontalPanel p = new HorizontalPanel();		
		p.add(labelMellomRom);
		panel.add(p);
		p = new HorizontalPanel();
		
	/*	p.add(labelProfileOwner)
		p.add(textBoxProfileOwner);
		panel.add(p);
		p = new HorizontalPanel();*/
		
		p.add(labelName);
		p.add(textBoxName);
		panel.add(p);
		p = new HorizontalPanel();
		
		p.add(labelBuildYear);
		p.add(textBoxBuildYear);
		panel.add(p);
		p = new HorizontalPanel();
		p.add(labelHouseSize);
		p.add(textBoxHouseSize);
		panel.add(p);
		p = new HorizontalPanel();
		p.add(labelHouseResidents);
		p.add(textBoxHouseResidents);
		panel.add(p);
		p = new HorizontalPanel();
		
		p.add(labelSelect);
		p.add(listBoxSelect);	
		textHeatProsent.setWidth("30px");
		p.add(textHeatProsent);
		panel.add(p);
		
		p = new HorizontalPanel();
		listBoxSelect = new ListBox();
		labelSelect = new Label();
		labelSelect.setWidth("80px");
		listBoxSelect.addItem("N/A");
		listBoxSelect.addItem("Varmepumpe");
		listBoxSelect.addItem("Oljefyring");
		listBoxSelect.addItem("Sentralvarme");
		labelSelect.setText("Sekundær");
		listBoxSelect.setWidth("165px");
		textHeatProsent = new TextBox();
		textHeatProsent.setWidth("30px");
		
		p.add(labelSelect);
		p.add(listBoxSelect);		
		p.add(textHeatProsent);
		panel.add(p);
		
		p = new HorizontalPanel();
		labelSelect = new Label();
		labelSelect.setWidth("80px");
		listBoxSelect = new ListBox();
		listBoxSelect.addItem("N/A");
		listBoxSelect.addItem("Varmepumpe");
		listBoxSelect.addItem("Oljefyring");
		listBoxSelect.addItem("Sentralvarme");
		labelSelect.setText("Tertiær");
		listBoxSelect.setWidth("165px");
		textHeatProsent = new TextBox();
		textHeatProsent.setWidth("30px");
		p.add(labelSelect);
		p.add(listBoxSelect);	
		p.add(textHeatProsent);
		panel.add(p);
		
		p = new HorizontalPanel();
		p.add(labelIsolated);
		p.add(listBoxIsolated);
		panel.add(p);
		p = new HorizontalPanel();
		p.add(labelType);
		p.add(listBoxType);
		panel.add(p);
		p = new HorizontalPanel();
		Label l = new Label();
		l.setWidth("80px");
		p.add(l);
		p.add(buttonRegisterProfile);
		panel.add(p);
		
		//click event
		buttonRegisterProfile.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				try {
					onRegister();
				
				} catch (Exception e) {
					// TODO: handle exception
					Window.alert("Didnt work");
				}
				
			}
		});
		
	}	
	
/*	public String getFirsName()
	{
		return textBoxFirstName.getText();
	}
	
	public String getMiddleName()
	{
		return textBoxMiddleName.getText();
	}
	
	public String getLastName()
	{
		return textBoxLastName.getText();
	}
	
	public String getAdresse()
	{
		return textBoxAdresse.getText();
	}
	
	public String getZipCode()
	{
		return textBoxZipCode.getText();
	}
	
	public String getCity()
	{
		return textBoxCity.getText();
	}
	
	public String getPhoneNumber()
	{
		return textBoxPhoneNumber.getText();
	}
	
	public String getEmailAdresse()
	{
		return textBoxEmailAdresse.getText();
	}*/
	

	public String getHouseResidents()
	{
		return textBoxHouseResidents.getText();
	}
	
	public String getName()
	{
		return textBoxName.getText();
	}

	public String getBuildYear(){
		return textBoxBuildYear.getText();
	}
	
	public String getHouseSize(){
		return textBoxHouseSize.getText();
	}
	
	public String getPrimHeating()
	{
		return listBoxSelect.getItemText(listBoxSelect.getSelectedIndex());
	}
	
	public String getTypePofile()
	{
		return listBoxType.getItemText(listBoxType.getSelectedIndex());
	}
	
	public String getIsisolated()
	{
		return listBoxIsolated.getItemText(listBoxIsolated.getSelectedIndex());
	}
	
	/**
	 * Brukes for og legge til en profil i handler Arraylist
	 * @param handler
	 */
	public void addProfileHandler(ActionHandler handler)
	{
		profilHandlers.add(handler);
	}
	
	protected void onRegister()
	{
		for (ActionHandler handler : profilHandlers)
		{
			handler.onAction();
		}
	}

	public String getStatusMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Brukes for og hente ut data til profilAdmin
	 * {@link profilAdmin}
	 * @param p
	 */
	public void setData(Profile p){
		
		textBoxName.setText(p.getName());
		textBoxBuildYear.setText(p.getBuildYear());
		textBoxHouseSize.setText(p.getHouseSize());
		textBoxHouseResidents.setText(p.getHouseResidents());
		
		
	}
	
	
	
	public void setProfileData(Profile profile){
		
		textBoxName.setText(profile.getName());
		
		//textBoxFullName.setText(user.getName());
	}
	
	
	
}
