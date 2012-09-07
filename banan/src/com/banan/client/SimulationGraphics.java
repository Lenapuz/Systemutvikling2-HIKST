package com.banan.client;

import java.util.Random;

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
	
	public SimulationGraphics() {
		herp2 = new TabPanel();
		initWidget(herp2);
		
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				Options chartOptions = Options.create();
				chartOptions.setWidth(956);
				chartOptions.setHeight(400);
				chartOptions.setTitle("Forbruk");
				chartOptions.setFontSize(10.0);
				DataTable data = DataTable.create();
				data.addColumn(ColumnType.STRING, "_");
				data.addColumn(ColumnType.NUMBER, "kW");

				String rapport = "<h1>Rapport</h1>";				
				
				data.addRows(24);
				for (int i = 0; i < 24; i++)
				{
					data.setValue(i, 0, i + ":00");
					data.setValue(i, 1, rand.nextInt(1337));
					
					rapport += "<p>" + i +":00 = " + rand.nextInt(1337) + "kW</p>";
				}
				
				chart = new LineChart(data, chartOptions);	
				VerticalPanel p = new VerticalPanel();
				p.add(chart);
				herp2.add(p, "Graf");
				
				VerticalPanel p2 = new VerticalPanel();
								
				p2.add(new HTML(rapport));
				herp2.add(p2, "Rapport");
				
				herp2.selectTab(0);
			}
		};
				
		VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);
	}
	
	public void shoveData(DataTable table)
	{
		//chart.draw(table, chartOptions);
	}
}
