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
import com.google.gwt.visualization.client.visualizations.LineChart;
import com.google.gwt.visualization.client.visualizations.LineChart.Options;


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
				@SuppressWarnings("deprecation")
				
				Options chartOptions = Options.create();
				chartOptions.setWidth(960);
				chartOptions.setHeight(400);
				chartOptions.setTitle("Strømforbruk");
				
				DataTable data = DataTable.create();
				data.addColumn(ColumnType.NUMBER, "kW");
			
				data.addRows(24);
				for (int i = 0; i < 24; i++)
				{
					data.setValue(i, 0, rand.nextInt(1337));
				}
				
				chart = new LineChart(data, chartOptions);
				
				herp2.add(chart);
			}
		};
				
		VisualizationUtils.loadVisualizationApi(onLoadCallback, LineChart.PACKAGE);
	}
	
	public void shoveData(DataTable table)
	{
		chart.draw(table, chartOptions);
	}
}
