package com.banan.client;

import com.banan.shared.*;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
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
		herp.addStyleName("profilelist");
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
					
					Label labelTemp = new Label("Temperatur");
					final TextBox textBoxTemp = new TextBox();
					textBoxTemp.setWidth("50px");
					
					labelTemp.addStyleName("profilelist_bar_item");
					textBoxTemp.addStyleName("profilelist_bar_item");
					
					HorizontalPanel hp = new HorizontalPanel();
					hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
					hp.add(labelTemp);
					hp.add(textBoxTemp);
					fp.add(hp);
					herp.add(fp);
					
					for (final Profile p : result)
					{
						HTML html = new HTML("<div class=\"profilelist_item\"><div class=\"profile_name\">" + p.getName() + "</div>" + "Byggeår: " + p.getBuildYear() + ", Størrelse: " + p.getHouseSize() + "kvm, Beboere: " + p.getHouseResidents() + "<button class=\"btn btn-primary profilelist_button\" id=\"new-" + p.getID() + "\">Ny simulering</button><button class=\"btn btn-primary profilelist_button\" id=\"res-" + p.getID() + "\">Resultater</button></div>");
						
						html.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {	
								Element newSim = Document.get().getElementById("new-" + p.getID());
								Element showResults = Document.get().getElementById("res-" + p.getID());
								Element target = Element.as(event.getNativeEvent().getEventTarget());
								if (newSim.isOrHasChild(target)) {
									int temp = 0;
									try {
										temp = Integer.parseInt(textBoxTemp.getText());
									}
									catch (Exception e) {
									}
									
									Main.SimService.simulate(p.getID(), temp, new AsyncCallback<SimResult>() {

										@Override
										public void onFailure(Throwable caught) {
											Window.alert(caught.getMessage());
										}

										@Override
										public void onSuccess(SimResult result) {
											updateData(new SimResult[] { result });
										}
										
									});// :O
								}
								else if (showResults.isOrHasChild(target)) {
									Main.SimService.GetSimResultByProfileId(p.getID(), new AsyncCallback<SimResult[]>() {
										public void onFailure(Throwable caught) {
											Window.alert(caught.getMessage());											
										}

										public void onSuccess(SimResult[] result) {	
											if (result == null) {
												Window.alert("Ingen resultater");
												
											}
											else  {
												updateData(result);
											}
										}										
									});
								}
							}						
						});
						herp.add(html);
					}
					
					HTML pagination = new HTML("<div class=\"profilelist_pagination\"><a>1</a><a>2</a><a>3</a><a>4</a></div>");
					herp.add(pagination);
				}
			});		
	}
	
	private void updateData(SimResult[] results) {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "_");
		data.addColumn(ColumnType.NUMBER, "kW");
		
		data.addRows(24);
		for (int i = 0; i < 24; i++) {
			data.setValue(i, 0, i + ":00");
		}
		
		for (int j = 0; j < results.length; j++) {
			for (int i = 0; i < 24; i++) {
				data.setValue(i, j + 1, results[j].getData()[i]);
			}
		}
													
		Main.mainPanel.showWidget(UI.SIMGRAPHICS);
		Main.simGraphics.shoveData(data);
	}
}
