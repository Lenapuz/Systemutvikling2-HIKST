package com.banan.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
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
	private TextBox textBoxProfileID;
	private TextBox	textBoxBuidYear;
	private TextBox	textBoxHouseSize;
	
	 
	//ListBoxs
	private ListBox listBoxSelect;
    private ListBox listBoxType;
    private ListBox listBoxIsolated;
	
	public ProfileReg(){
		
		panel = new VerticalPanel();
		panel.addStyleName("register");
		initWidget(panel);
		
		//Text box
		textBoxProfileID = new TextBox();
		textBoxProfileID.getElement().setPropertyString("placeholder", "Profil ID");
		
		textBoxBuidYear = new TextBox();
		textBoxBuidYear.getElement().setPropertyString("placeholder", "build year");
		
		textBoxHouseSize = new TextBox();
		textBoxHouseSize.getElement().setPropertyString("placeholder", "Size(kvm)");
		
		//listbox add Items 
		listBoxSelect = new ListBox();
		listBoxSelect.addItem("Varmepumpe");
		listBoxSelect.addItem("Oljefyring");
		
		//list box
		listBoxIsolated = new ListBox();
		listBoxIsolated.addItem("Ja");
		listBoxIsolated.addItem("Nei");
		
		listBoxType = new ListBox();
		listBoxType.addItem("Enebolig");
		listBoxType.addItem("Tomanns Bolig");
		listBoxType.addItem("Leilighet");
		listBoxType.addItem("Industripark");
		
		//buttons
		buttonRegisterProfile = new Button("registrer");
		
		//Set pref
		listBoxSelect.setWidth("160px");
		listBoxIsolated.setWidth("160px");
		textBoxProfileID.setWidth("160px");
		textBoxBuidYear.setWidth("160px");
		buttonRegisterProfile.setWidth("160px");
		
		
		//adder listbox
		panel.add(textBoxProfileID);
		panel.add(textBoxBuidYear);
		panel.add(textBoxHouseSize);
		panel.add(listBoxSelect);
		panel.add(listBoxIsolated);
		panel.add(listBoxType);
		panel.add(buttonRegisterProfile);
		
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

	public String getProfileID()
	{
		return textBoxProfileID.getText();
	}
	
	public String getBuildYear(){
		return textBoxBuidYear.getText();
	}
	
	public String getHouseSize(){
		return textBoxHouseSize.getText();
	}
	
	public String getType()
	{
		return listBoxSelect.getItemText(listBoxSelect.getSelectedIndex());
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
