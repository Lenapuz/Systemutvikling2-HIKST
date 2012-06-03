package com.banan.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

public class MainMenu extends Composite 
{
	public MainMenu(final DeckPanel p)
	{
		FlowPanel panel = new FlowPanel();
		panel.addStyleName("main_menu");
		initWidget(panel);
		
		//Knapper til MainPanel 
		Button buttonSim = new Button("Simulering");
		buttonSim.addStyleName("menu_item");
		
		Button buttonRegister = new Button("Legg til konsulent");
		buttonRegister.addStyleName("menu_item");
		
		Button buttonLogout = new Button("Logg ut");
		buttonLogout.addStyleName("menu_item");
		
		Button buttonAddProfile = new Button("Opprett profil");
		buttonAddProfile.addStyleName("menu_item");
		
		buttonSim.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{				
				p.showWidget(UI.SIMULATION);
			}			
		});
		
		//Button for registrering
		buttonRegister.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				p.showWidget(UI.REGISTRATION);
			}			
		});
		
		//Profil skjema
		buttonAddProfile.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				p.showWidget(UI.PROFIL);
			}			
		});
		
		
		
		buttonLogout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{				
				p.showWidget(UI.LOGIN);
			}			
		});
		
		panel.add(buttonSim);
		if (Main.User.getType().equals("Admin"))
		{
			panel.add(buttonRegister);
			
		}
		//panel.add(buttonLogout);
		panel.add(buttonAddProfile);
		panel.add(buttonRegister);
		
	}
}
