package com.banan.client;

import com.banan.shared.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
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
					FlowPanel fp = new FlowPanel();
					fp.addStyleName("profilelist_bar");
					
					Label labelTemp = new Label("Temperatur:");
					TextBox textBoxTemp = new TextBox();
					textBoxTemp.setWidth("50px");
					
					fp.add(labelTemp);
					fp.add(textBoxTemp);
					herp.add(fp);
					
					for (final Profile p : result)
					{
						HTML html = new HTML("<div class=\"profilelist_item\"><div class=\"profile_name\">" + p.getName() + "</div>" + "Byggeår: " + p.getBuildYear() + ", Størrelse: " + p.getHouseSize() + "kvm, Beboere: " + p.getHouseResidents() + "</div>");
						html.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {					
								Main.SimService.GetSimResultByProfileId(p.getID(), new AsyncCallback<SimResult[]>() {

									@Override
									public void onFailure(Throwable caught) {
										Window.alert(caught.getMessage());											
									}

									@Override
									public void onSuccess(SimResult[] result) {	
										if (result == null) {
											Main.SimService.simulate(p.getID(), new AsyncCallback<SimResult>() {

												@Override
												public void onFailure(Throwable caught) {
													Window.alert(caught.getMessage());
												}

												@Override
												public void onSuccess(SimResult result) {
													updateData(result.getData());
												}
												
											});// :O
										}
										else  {
											updateData(result[0].getData());
										}
									}										
								});
							}						
						});
						herp.add(html);
					}
				}
			});		
	}
	
	private void updateData(Integer[] d) {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "_");
		data.addColumn(ColumnType.NUMBER, "kW");
		
		data.addRows(24);
		for (int i = 0; i < 24; i++)
		{
			data.setValue(i, 0, i + ":00");
			data.setValue(i, 1, d[i]);												
		}
													
		Main.mainPanel.showWidget(UI.SIMGRAPHICS);
		Main.simGraphics.shoveData(data);
	}
}
