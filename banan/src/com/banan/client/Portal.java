package com.banan.client;

import com.banan.shared.Profile;
import com.banan.shared.SimResult;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;

public class Portal extends Composite {
	
	
	private TextBox textBoxSøk;
	//private VerticalPanel panel; 
	private Button buttonSøk;
	
	public Portal() 
	{
		HorizontalPanel panel = new HorizontalPanel();
		panel.setCellHorizontalAlignment(buttonSøk, HasHorizontalAlignment.ALIGN_RIGHT);
		panel.setCellHorizontalAlignment(textBoxSøk, HasHorizontalAlignment.ALIGN_RIGHT);
		panel.addStyleName("portal");
		initWidget(panel);
		
		textBoxSøk = new TextBox();
		textBoxSøk.getElement().setPropertyString("placeholder", "Søk");
		buttonSøk = new Button("Søk");
		buttonSøk.addStyleName("btn btn-primary");
		
		textBoxSøk.setWidth("150px");
		buttonSøk.setWidth("10");
		
		
		
		panel.add(textBoxSøk);
		panel.add(buttonSøk);
		
		buttonSøk.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				Main.SimService.GetSimResultByProfileId(Integer.parseInt(textBoxSøk.getText()), new AsyncCallback<SimResult[]>() {
					public void onFailure(Throwable caught) 
					{
						Window.alert(caught.getMessage());
					}
					public void onSuccess(SimResult[] result)
					{
						updateData(result);
					}							
				
				});								
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
		
		for (int j = 0; j < results.length;	 j++) {
			for (int i = 0; i < 24; i++) {
				data.setValue(i, j + 1, results[j].getData()[i]);
			}
		}
													
		Main.mainPanel.showWidget(UI.SIMGRAPHICS);
		Main.simGraphics.shoveData(data);
	}
}
