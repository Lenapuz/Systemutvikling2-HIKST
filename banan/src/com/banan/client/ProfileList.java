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
	private DeckPanel container;
	
	public ProfileList()
	{
		container = new DeckPanel();
		
		initWidget(container);
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
					
					Button buttonEmpty = new Button("Tøm han!");
					buttonEmpty.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							Main.SimService.purgeIt(new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void onSuccess(Void result) {
									// TODO Auto-generated method stub
									
								}
								
							});
						}
					});
					
					
					HorizontalPanel hp = new HorizontalPanel();
					hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
					hp.add(labelTemp);
					hp.add(textBoxTemp);
					hp.add(buttonEmpty);
					fp.add(hp);
					VerticalPanel herp = new VerticalPanel();
					//container.add(herp);
					//container.showWidget(0);
					herp.addStyleName("profilelist");
					herp.add(fp);
					
					int pages = 1 + result.length / 10;
					if (pages % 10 == 0) {
						pages--;
					}
					int count = 0;
					boolean added = true;

					boolean first = true;
					
					for (final Profile p : result)
					{
						HTML html = new HTML("<div class=\"profilelist_item\"><div class=\"profile_name\">(" +p.getID() + ") " + p.getName() + "</div>" + "Byggeår: " + p.getBuildYear() + ", Størrelse: " + p.getHouseSize() + "kvm, Beboere: " + p.getHouseResidents() + "<button class=\"btn btn-primary profilelist_button\" id=\"new-" + p.getID() + "\">Ny simulering</button><button class=\"btn btn-primary profilelist_button\" id=\"res-" + p.getID() + "\">Resultater</button></div>");
						
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
						count++;
						
						if (count == 10 || first) {
							first = false;
							
							container.add(herp);
							
							if (count == 10 || count == result.length) {
								String s = "<div class=\"profilelist_pagination\">";
								for (int i = 0; i < pages; i++) {
									s += "<a id=\"page-" + i + "\">" + (i + 1) + "</a>";
								}
								s += "</div";
								HTML pagination = new HTML(s);
								pagination.addClickHandler(new ClickHandler() {
									public void onClick(ClickEvent event) {
										Element target = Element.as(event.getNativeEvent().getEventTarget());
										int page = Integer.parseInt(target.getId().split("-")[1]);
										container.showWidget(page);
									}						
								});
								herp.add(pagination);
								
								if (count == 10) {
									herp = new VerticalPanel();
									first = true;
								}
							}
						}
					}
					
					container.showWidget(0);
				}
			});		
	}
	
	private void updateData(SimResult[] results) {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "_");
		for (int j = 0; j < results.length; j++) {
			data.addColumn(ColumnType.NUMBER, "ID: " + results[j].getId());
		}
		
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
