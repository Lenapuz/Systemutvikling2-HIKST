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
	public static final SessionServiceAsync SessionService = GWT.create(SessionService.class);
	
	public static final DeckPanel mainPanel = new DeckPanel();
	public static final SimulationGraphics simGraphics = new SimulationGraphics();
	
	public static UserAdmin userAdmin = new UserAdmin();
	public static Registration userEdit = new Registration();
	
	//onAction Events.
	public void onModuleLoad() 
	{
		mainPanel.addStyleName("mainPanel");
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
				
		//loginHandler, brukes f�r sjekke brukeren n�r man logger inn.
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
								SessionService.set("login", (Integer)UI.INTRO, new AsyncCallback<Void>() {
									public void onFailure(Throwable caught) { }
									public void onSuccess(Void result) { }									
								});								
							}
							else
							{
								Window.alert(User.getStatusMessage());
							}
						}
					});		
			}
		});
		// on onAction for � registree brukere,konsulent eller Administraotr
		register.addRegisterHandler(new ActionHandler() {
			public void onAction()
			{
				final User user = new User(register.getFullName(), register.getUsername(), register.getPassword(), register.getType());
								
				UserService.register(user,
					new AsyncCallback<User>() {
						public void onFailure(Throwable caught) 
						{
							Window.alert(caught.getMessage());
						}
	
						public void onSuccess(User result) 
						{
							Window.alert(result.getStatusMessage());
							register.clear();
							userAdmin.addUser(user);
						}
					});	
			}
		});
		
		// on onAction for � registree brukere,konsulent eller Administraotr
				userEdit.addRegisterHandler(new ActionHandler() {
					public void onAction()
					{
						
						final User user = new User(userEdit.getFullName(), userEdit.getUsername(), userEdit.getPassword(), userEdit.getType());
						UserService.EditUser(user, userEdit.getOldUserName(),
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
		// Fungerer ikke! feil i SQL sp�rring ser ut til!! hvis ikke,
		// s� ligger feilen en anne plass.
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
		
		HTML intro = new HTML("<div class=\"foo\">" +
				"<h1>Velkommen!</h1></div>" +
				"<div class=\"intro_col\">" +
				"	<h2>Ka</h2>" +
				"	<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque metus odio, volutpat sollicitudin lobortis vitae, commodo a orci. Mauris congue interdum mauris, ut condimentum magna molestie sed. Morbi dignissim nisl et tellus venenatis sit amet tristique libero interdum. Ut blandit, tellus ac suscipit molestie, lectus lacus molestie eros, sit amet semper nisl dui et purus. Duis et venenatis massa. Sed arcu risus, dapibus eu ultricies vel, blandit in libero. Quisque semper tincidunt turpis, id commodo erat vulputate eget. Sed vitae dui a sem ornare faucibus sit amet pharetra nisl. Nunc et ipsum nunc, in fringilla felis." +
				"	</p>" +
				"</div>" +
				"<div class=\"intro_col\">" +
				"	<h2>Så</h2>" +
				"	<p>Pellentesque tempor gravida quam sed sagittis. Curabitur turpis turpis, iaculis sit amet scelerisque vitae, tincidunt ut tortor. Suspendisse feugiat ultrices risus ut tristique. Fusce placerat tristique aliquam. Sed ornare fringilla justo. Vivamus ac venenatis felis. Suspendisse potenti." +
				"	</p>" +
				"</div>" +
				"<div class=\"intro_col\">" +
				"	<h2>Skjer?</h2>" +
				"	<p>Integer volutpat, tellus et tempus semper, erat orci convallis lectus, gravida interdum elit purus pulvinar erat. Quisque vestibulum porta neque, non interdum elit rhoncus a. Aliquam convallis auctor mi ac laoreet. Nunc metus ipsum, tempus ac rutrum a, lacinia ac ligula. Praesent massa felis, sodales ac faucibus quis, tempus nec sapien. Praesent vel libero quam, ultricies vehicula purus. Aliquam semper pretium sagittis. Fusce faucibus ornare placerat. Pellentesque vel volutpat lectus. Aenean facilisis lectus pellentesque lorem tempus ultrices. Vestibulum varius ornare odio, in sodales orci dapibus suscipit. Mauris risus odio, condimentum in imperdiet ac, vehicula imperdiet ipsum. Proin molestie, mi eget vestibulum porta, magna felis fringilla ipsum, eget eleifend lectus metus sed ante." +
				"	</p>" +
				"</div>");
		introPanel.add(intro);
		
		mainPanel.add(new ProfileList());
		mainPanel.add(simGraphics);		
		mainPanel.add(userAdmin);
		mainPanel.add(userEdit);
		
		SessionService.get("login", new AsyncCallback<Integer>() {
			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(Integer result) {
				if (result != null)
				{
					menuPanel.setVisible(true);
					menuPanel.showWidget(UI.MAIN_MENU);
					mainPanel.showWidget(result);
				}
				else
				{
					menuPanel.showWidget(UI.MAIN_MENU);	
					mainPanel.showWidget(UI.LOGIN);
				}
			}									
		});
	}
}
