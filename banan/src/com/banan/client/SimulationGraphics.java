package com.banan.client;

import java.util.Random;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
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
	
	private VerticalPanel herp2;
	LineChart chart;
	Options chartOptions;
	Random rand = new Random();
	
	public SimulationGraphics() {
		herp2 = new VerticalPanel();
		initWidget(herp2);
		
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				
				Options chartOptions = Options.create();
				chartOptions.setWidth(960);
				chartOptions.setHeight(400);
				chartOptions.setTitle("Forbruk");
				chartOptions.setFontSize(10.0);
				DataTable data = DataTable.create();
				data.addColumn(ColumnType.STRING, "_");
				data.addColumn(ColumnType.NUMBER, "kW");
				
				data.addRows(24);
				for (int i = 0; i < 24; i++)
				{
					data.setValue(i, 0, i + ":00");
					data.setValue(i, 1, rand.nextInt(1337));
				}
				
				chart = new LineChart(data, chartOptions);
				
				herp2.add(chart);
			}
		};
				
		VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);
	}
	
	public void shoveData(DataTable table)
	{
		//chart.draw(table, chartOptions);
	}
}
