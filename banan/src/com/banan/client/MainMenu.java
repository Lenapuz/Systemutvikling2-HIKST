package com.banan.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.logging.impl.LevelImplRegular;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

public class MainMenu extends Composite 
{
	Button buttonHome;
	Button buttonSim;
	Button buttonRegister;
	Button buttonAddProfile;
	Button buttonLogout;
	Button buttonUserAdmin;
	Button buttonProfileEdit;
	
	Button buttonSelected;
	
	FlowPanel panel;
	
	public MainMenu(final DeckPanel p)
	{
		panel = new FlowPanel();
		panel.addStyleName("main_menu");
		initWidget(panel);
		
		buttonHome = new Button("Hjem");
		buttonHome.addStyleName("menu_item selected");
		buttonSelected = buttonHome;
		
		//Knapper til MainPanel 
		buttonSim = new Button("Simulering");
		buttonSim.addStyleName("menu_item");
		//buttonSim.addStyleName("btn");
		
		buttonRegister = new Button("Ny bruker");
		buttonRegister.addStyleName("menu_item");
		//buttonRegister.addStyleName("btn");
		
		buttonLogout = new Button("Logg ut");
		buttonLogout.addStyleName("menu_item");
		buttonLogout.addStyleName("floatright");
		//buttonLogout.addStyleName("btn");
		
		buttonAddProfile = new Button("Ny profil");
		buttonAddProfile.addStyleName("menu_item");
		//buttonAddProfile.addStyleName("btn");
		buttonUserAdmin = new Button("Brukeradministrasjon");
		buttonUserAdmin.addStyleName("menu_item");
		//buttonUserAdmin.addStyleName("btn");
		
		buttonProfileEdit = new Button("Profiladministrasjon");
		buttonProfileEdit.addStyleName("menu_item");
		
		buttonHome.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event)
			{
				p.showWidget(UI.INTRO);
				buttonSelected.removeStyleName("selected");
				buttonSelected = buttonHome;
				buttonSelected.addStyleName("selected");
			}
		});
		
		buttonSim.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				p.showWidget(UI.SIMULATION);
				buttonSelected.removeStyleName("selected");
				buttonSelected = buttonSim;
				buttonSelected.addStyleName("selected");
			}			
		});
		
		//Button for registrering
		buttonRegister.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				p.showWidget(UI.REGISTRATION);
				buttonSelected.removeStyleName("selected");
				buttonSelected = buttonRegister;
				buttonSelected.addStyleName("selected");
			}			
		});
		
		//Profil skjema
		buttonAddProfile.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				p.showWidget(UI.PROFIL);
				buttonSelected.removeStyleName("selected");
				buttonSelected = buttonAddProfile;
				buttonSelected.addStyleName("selected");
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
				buttonSelected.removeStyleName("selected");
				buttonSelected = buttonUserAdmin;
				buttonSelected.addStyleName("selected");
			}			
		});
		
		/**
		 * 
		 */
		buttonProfileEdit.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				p.showWidget(UI.EDITPROFILE);
				buttonSelected.removeStyleName("selected");
				buttonSelected = buttonProfileEdit;
				buttonSelected.addStyleName("selected");
				Main.profileAdmin.reload();
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
		panel.add(buttonHome);
		panel.add(buttonSim);
		panel.add(buttonProfileEdit);
		
		if(Main.User.getType().equals("Admin") || Main.User.getType().equals("Konsulent"))
		{
			panel.add(buttonAddProfile);
			panel.add(buttonUserAdmin);
		}
				
		if(Main.User.getType().equals("Admin"))
		{
			panel.add(buttonRegister);
			
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
		panel.remove(buttonProfileEdit);
	}
	
	
}
