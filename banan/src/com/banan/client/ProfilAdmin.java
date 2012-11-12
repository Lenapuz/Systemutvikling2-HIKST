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
/***
 * Brukes for editering av rpfileer
 */
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
				flextable.setWidget(row, 0, new Label("Navn"));
				flextable.setWidget(row, 1, new Label("Størrelse"));
				flextable.setWidget(row, 2, new Label("Byggeår"));
				flextable.setWidget(row, 3, new Label("Rediger"));
				flextable.setWidget(row, 4, new Label("Slett"));
						
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
		
		/**
		 * Editering av profil
		 */
		flextable.setWidget(row, 0, new Label(p.getName()));
		flextable.setWidget(row, 1, new Label(p.getBuildYear()));
		flextable.setWidget(row, 2, new Label(p.getHouseSize()));
		flextable.setWidget(row, 3, new Label(p.getHouseResidents()));
		
		b = new Button();
		b.setText("Edit");
		b.addStyleName("btn");
		b.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Main.profileEdit.setData(p);
				Main.mainPanel.showWidget(UI.EDITPROFILE2);
				
				//Main.mainPanel.showWidget(UI.EDITPROFILE);
			}
		});//end of edit
		
			/**
			 * Sletting av profil
			 */
			flextable.setWidget(row, 3, b);
			b = new Button();
			b.setText("Slett");
			b.addStyleName("btn");
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
			});//end of delete profile
		
		panel.add(flextable);
		
	}//end of method 
	
	
	public void F(){
		
	}
	
	/**
	 *
	 */
	public void reload() {
		row = 0;
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
						flextable.setWidget(row, 0, new Label("Navn"));
						flextable.setWidget(row, 1, new Label("Størrelse"));
						flextable.setWidget(row, 2, new Label("Byggeår"));
						flextable.setWidget(row, 3, new Label("Rediger"));
						flextable.setWidget(row, 4, new Label("Slett"));
						
						for(final Profile p : result)
						{
							addProfileToTable(p);
						}
						panel.add(flextable);
					}
				});	
	}
	

	
		
	
}//End of class
