package com.banan.client;

import com.banan.shared.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;

public class ProfileList extends Composite {
	private VerticalPanel herp;
	
	public ProfileList()
	{
		herp = new VerticalPanel();
		initWidget(herp);
		Main.ProfileService.getProfiles(
				new AsyncCallback<Profile[]>() {
					public void onFailure(Throwable caught) 
					{
						Window.alert(caught.getMessage());
					}

					public void onSuccess(Profile[] result) 
					{
						for (final Profile p : result)
						{
							Label l = new Label( p.getBuildYear() + " StOrrelse: " + p.getHouseSize() + " Beboere: " + p.getHouseResidents());
							l.setStyleName("profilelist_item");
							l.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									//Window.alert("!");
									
									Main.SimService.simulate(p.getID(), new AsyncCallback<SimResult>() {

										@Override
										public void onFailure(Throwable caught) {
											Window.alert(caught.getMessage());											
										}

										@Override
										public void onSuccess(SimResult result) {	
											DataTable data = DataTable.create();
											Integer[] d = result.getData();
											data.addColumn(ColumnType.NUMBER, "kW");
											
											data.addRows(24);
											for (int i = 0; i < 24; i++)
											{
												data.setValue(i, 0, d[i]);
											}
																						
											Main.mainPanel.showWidget(UI.SIMGRAPHICS);
											Main.simGraphics.shoveData(data);
										}										
									});
								}								
							});
							herp.add(l);
						}
					}
				});		
	}
}
