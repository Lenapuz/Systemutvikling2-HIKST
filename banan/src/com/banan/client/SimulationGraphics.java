package com.banan.client;

import java.util.Random;

import com.banan.server.SimServiceImpl;
import com.banan.shared.SimResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;


public class SimulationGraphics extends Composite {
	
	private TabPanel herp2;
	LineChart chart;
	Options chartOptions;
	Random rand = new Random();
	HTML rapport;
	
	public SimulationGraphics() {
		herp2 = new TabPanel();
		herp2.addStyleName("simResultPanel");
		initWidget(herp2);
		
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				final Options chartOptions = Options.create();
				chartOptions.setWidth(960);
				chartOptions.setHeight(400);
				chartOptions.setTitle("Forbruk");
				chartOptions.setFontSize(8.0);
				final DataTable data = DataTable.create();
				data.addColumn(ColumnType.STRING, "_");
				data.addColumn(ColumnType.NUMBER, "kW");
								
				final StringBuilder sb = new StringBuilder();
								
				Main.SimService.GetSimResultByProfileId(22, new AsyncCallback<SimResult[]>(){
					@Override
					public void onFailure(Throwable caught) { }

					@Override
					public void onSuccess(SimResult[] result) {
						String r = "<h1>Rapport</h1>";
						data.addRows(24);
						Integer[] simdata = result[0].getData();
						
						for (int i = 0; i < simdata.length; i++)
						{
							data.setValue(i, 0, i + ":00");
							data.setValue(i, 1, simdata[i]);
							//data.setValue(i, 0, result);
							r += "<p>" + i +":00 = " + simdata[i] + "kW</p>";
						}
						sb.append(r);
						chart = new LineChart(data, chartOptions);	
						VerticalPanel p = new VerticalPanel();
						p.add(chart);
						herp2.add(p, "Graf");
						
						VerticalPanel p2 = new VerticalPanel();
								
						rapport = new HTML();
						p2.add(rapport);
						herp2.add(p2, "Rapport");
						
						herp2.selectTab(0);
						
					}});
			}
		};
				
		VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);
	}
	
	public void shoveData(DataTable table)
	{
		String r = "<div style=\"margin: 20px 30px; margin-top: 0px;\"><div class=\"clearfix\"><h1>Rapport</h1>";	
		int total = 0;
		for (int i = 0; i < 24; i++)
		{
			int f = table.getValueInt(i, 1);
			total += f;
			r += "<div class=\"rapport_item\">" +
					"<p>" + i +":00</p>" +
					"<p class=\"rapport_kw\">" + f + "kW</p>" +
				 "</div>";
		}
		r += "</div><p class=\"rapport_summary\">Totalt: <b>" + total + " kWh</b></p></div>";
		rapport.setHTML(r);
		chart.draw(table, chartOptions);
	}
}
