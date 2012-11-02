package com.banan.client;



import com.banan.server.ProfileServiceImpl;
import com.banan.shared.Profile;
import com.banan.shared.SimResult;
import com.banan.shared.User;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

//Husk komentarer
public class ProfilAdmin extends Composite {
	
	
	private VerticalPanel panel;
	private FlexTable flextable = new FlexTable();
	private int row = 0;
	
	
	private FlexTable fucktable;
	Button b;
	
	/**
	 * Konstruktor
	 */
	public ProfilAdmin(){
		
		panel = new VerticalPanel();
		panel.addStyleName("profileAdmin");
		initWidget(panel);		
		
		
		
		Main.ProfileService.getProfiles(new AsyncCallback<Profile[]>() {
			
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert(caught.getMessage());
			}

			/**
			 * @param result
			 */
			@Override
			public void onSuccess(Profile[] result) {
				// TODO Auto-generated method stub
				flextable.setWidget(row, 0, new Label("Navn: "));
				flextable.setWidget(row, 1, new Label("Størrelse: "));
				flextable.setWidget(row, 2, new Label("Bygge År: "));
				flextable.setWidget(row, 3, new Label("Slett: "));
				flextable.setWidget(row, 4, new Label("Edit: "));
		
				
				for(Profile p : result)
				{
					addProfileToTable(p);
				}
			}	
		});	
		
	}//End of Constructor

	//method to do
	public void addProfileToTable(final Profile p){
		
		Button b;
		
		row++;
		
		flextable.setWidget(row, 0, new Label(p.getName()));
		flextable.setWidget(row, 1, new Label(p.getHouseSize()));
		flextable.setWidget(row, 2, new Label(p.getBuildYear()));
		
		b = new Button();
		b.setText("Edit");
		
		b.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Main.profileAdmin.setData(p);
				Main.mainPanel.showWidget(UI.PROFIL);
				//Main.mainPanel.showWidget(UI.EDITPROFILE);
			}
		});
		
			/**
			 * Sletting av profil
			 */
			flextable.setWidget(row, 3, b);
			b = new Button();
			b.setText("Slett");
			flextable.setWidget(row, 4, b);			
			b.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					Main.ProfileService.DeleteProfile(p, new AsyncCallback<Profile>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							Window.alert(caught.getMessage());
						}

						@Override
						public void onSuccess(Profile result) {
							// TODO Auto-generated method stub
							reload();
							Window.alert(result.getStatusMessage());
							row--;
						}
					});
				}
			});
		
		panel.add(flextable);
		
	}//end of method 
	
	
	public void F(){
		
	}
	
	/**
	 *
	 */
	public void reload() {
		flextable.clear();
		Main.ProfileService.getProfiles(
				new AsyncCallback<Profile[]>() 
				{
					@Override
					public void onFailure(Throwable caught) 
					{
						Window.alert(caught.getMessage());						
					}

					@Override
					public void onSuccess(Profile[] result) 
					{
						flextable.setWidget(row, 0, new Label("Fultnavn:"));
						flextable.setWidget(row, 1, new Label("Brukernavn:"));
						flextable.setWidget(row, 2, new Label("Type:"));
						flextable.setWidget(row, 3, new Label("Slett:"));
						flextable.setWidget(row, 4, new Label("Edit:"));
						
						for(final Profile p : result)
						{
							addProfileToTable(p);
						}
						panel.add(flextable);
					}
				});	
	}
	

	
		
	
}//End of class
