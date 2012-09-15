package com.banan.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
/***
 * Classe for skjemabiten for å registrere profil. denne vil da bli brukt på annen måte!
 * men utgangspunktet er der å kan endres på.
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
	
	 
	//ListBoxs
	private ListBox listBoxSelect;
    private ListBox listBoxType;
    private ListBox listBoxIsolated;
	
    //Constructor
	public ProfileReg(){
		
		panel = new VerticalPanel();
		panel.addStyleName("register");
		initWidget(panel);
		
		Label labelName = new Label("Navn:");
		labelName.setWidth("80px");
		textBoxName = new TextBox();
		Label labelBuildYear = new Label("ByggeAr:");
		labelBuildYear.setWidth("80px");
		textBoxBuildYear = new TextBox();
		Label labelHouseSize = new Label("StOrrelse:");
		labelHouseSize.setWidth("80px");
		textBoxHouseSize = new TextBox();
		Label labelHouseResidents = new Label("Beboere:");
		labelHouseResidents.setWidth("80px");
		textBoxHouseResidents = new TextBox();
		
		//listbox add Items 
		Label labelSelect = new Label("Fyring:");
		labelSelect.setWidth("80px");
		listBoxSelect = new ListBox();
		listBoxSelect.addItem("Varmepumpe");
		listBoxSelect.addItem("Oljefyring");
		listBoxSelect.addItem("Sentralvarme");
		
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
	    
		HorizontalPanel p = new HorizontalPanel();		
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
	
	
	
}
