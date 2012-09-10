package com.banan.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.banan.server.ProfileServiceImpl;
import com.banan.shared.*;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

public class Main implements EntryPoint 
{
	public static final UserServiceAsync UserService = GWT.create(UserService.class);
	public static User User = new User();
	
	public static final ProfileServiceAsync ProfileService = GWT.create(ProfileService.class);
	public static ProfileReg profile = new ProfileReg();
	
	public static final SimServiceAsync SimService = GWT.create(SimService.class);
	
	public static final DeckPanel mainPanel = new DeckPanel();
	public static final SimulationGraphics simGraphics = new SimulationGraphics();
	
	public static UserAdmin userAdmin = new UserAdmin();
	
	//onAction Events.
	public void onModuleLoad() 
	{
		RootPanel.get("main_content").add(mainPanel);
		VerticalPanel introPanel = new VerticalPanel();
		mainPanel.add(introPanel);
		
		final DeckPanel menuPanel = new DeckPanel();
		RootPanel.get("menu").add(menuPanel);
		menuPanel.setVisible(false);
	    
		MainMenu menu = new MainMenu(mainPanel);
		menuPanel.add(menu);	
		
		final Login login = new Login();
		VerticalPanel p = new VerticalPanel();
		p.add(login);
		p.add(new HTML("<div id=\"portal\"><h3>Kunde?</h3><a id=\"linkportal\" href=\"http://gruppe2.dyndns.org/portal\">Til kundeportal</a></div>"));
		mainPanel.add(p);
		
		final Registration register = new Registration();
		p = new VerticalPanel();
		p.add(register);
		mainPanel.add(p);
		
		final ProfileReg profil = new ProfileReg();
		p = new VerticalPanel();
		p.add(profil);
		mainPanel.add(p);
		
		//loginHandler, brukes får sjekke brukeren når man logger inn.
		login.addLoginHandler(new ActionHandler() {
			public void onAction()
			{
				UserService.login(new User(login.getUsername(), login.getPassword()),
					new AsyncCallback<User>() {
						public void onFailure(Throwable caught) 
						{
							Window.alert(caught.getMessage());
						}
	
						public void onSuccess(User result) 
						{
							User = result;
							if (User.isLoggedIn())
							{
								menuPanel.setVisible(true);
								mainPanel.showWidget(UI.INTRO);
							}
							else
							{
								Window.alert(User.getStatusMessage());
							}
						}
					});		
			}
		});
		// on onAction for å registree brukere,konsulent eller Administraotr
		register.addRegisterHandler(new ActionHandler() {
			public void onAction()
			{
				
				UserService.register(new User(register.getFullName(), register.getUsername(), register.getPassword(), register.getType()),
					new AsyncCallback<User>() {
						public void onFailure(Throwable caught) 
						{
							Window.alert(caught.getMessage());
						}
	
						public void onSuccess(User result) 
						{
							Window.alert(result.getStatusMessage());
						}
					});	
			}
		});
		
		// Starter Profil registrering OnAction() 
		// Fungerer ikke! feil i SQL spørring ser ut til!! hvis ikke,
		// så ligger feilen en anne plass.
		profil.addProfileHandler(new ActionHandler(){
			public void onAction()
			{
				
				//String buildYear,  String typeProfile, String primHeating, String isIsolated
			ProfileService.profil(new Profile(profil.getName(), profil.getBuildYear(),profil.getIsisolated(),profil.getTypePofile(), profil.getPrimHeating(), profil.getHouseResidents() , profil.getHouseSize()),
				new AsyncCallback<Profile>() {
					public void onFailure(Throwable caught)
					{
						Window.alert(caught.getMessage());
					}
					
					public void onSuccess(Profile result) {
						// TODO Auto-generated method stub
						
						Window.alert(result.getStatusMessage());
					}
					
				});
			}
		
		});
							
		HTML intro = new HTML("<div class=\"foo\"><b>Du</b> er logget inn!</div>");
		introPanel.add(intro);
		
		mainPanel.add(new ProfileList());
		mainPanel.add(simGraphics);		
		mainPanel.add(new UserAdmin());

		menuPanel.showWidget(UI.MAIN_MENU);	
		mainPanel.showWidget(UI.LOGIN);
	}
}
