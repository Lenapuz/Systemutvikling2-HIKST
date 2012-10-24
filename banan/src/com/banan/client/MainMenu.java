package com.banan.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.logging.impl.LevelImplRegular;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

public class MainMenu extends Composite 
{
	Button buttonSim;
	Button buttonRegister;
	Button buttonAddProfile;
	Button buttonLogout;
	Button buttonUserAdmin;
	
	FlowPanel panel;
	
	public MainMenu(final DeckPanel p)
	{
		panel = new FlowPanel();
		panel.addStyleName("main_menu");
		initWidget(panel);
		
		//Knapper til MainPanel 
		buttonSim = new Button("Simulering");
		buttonSim.addStyleName("menu_item");
		//buttonSim.addStyleName("btn");
		
		buttonRegister = new Button("Legg til konsulent");
		buttonRegister.addStyleName("menu_item");
		//buttonRegister.addStyleName("btn");
		
		buttonLogout = new Button("Logg ut");
		buttonLogout.addStyleName("menu_item");
		buttonLogout.addStyleName("floatright");
		//buttonLogout.addStyleName("btn");
		
		buttonAddProfile = new Button("Opprett profil");
		buttonAddProfile.addStyleName("menu_item");
		//buttonAddProfile.addStyleName("btn");
		
		buttonUserAdmin = new Button("Brukeradministrasjon");
		buttonUserAdmin.addStyleName("menu_item");
		//buttonUserAdmin.addStyleName("btn");
		
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
				Main.SessionService.clear("login", new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) { }
					public void onSuccess(Void result) { }					
				});
				Window.Location.reload();
			}
		});
		
		buttonUserAdmin.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{	
				Main.userAdmin.reload();
				p.showWidget(UI.USERADMIN);
			}			
		});
		
		addButtons();		
	}
	
	public void updateButtons()
	{
		removeButtons();
		addButtons();
	}
	
	private void addButtons()
	{
		panel.add(buttonSim);
		
		if(Main.User.getType().equals("Admin") || Main.User.getType().equals("Konsulenter"))
		{
			panel.add(buttonAddProfile);
		}
				
		if(Main.User.getType().equals("Admin"))
		{
			panel.add(buttonRegister);
			panel.add(buttonUserAdmin);
		}
		panel.add(buttonLogout);
	}
	
	private void removeButtons()
	{
		panel.remove(buttonSim);
		panel.remove(buttonAddProfile);
		panel.remove(buttonRegister);
		panel.remove(buttonUserAdmin);	
		panel.remove(buttonLogout);
	}
	
	
}
