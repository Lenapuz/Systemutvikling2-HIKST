package com.banan.client;

import java.util.Random;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.google.gwt.core.client.JsArray;
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
	Random r = new Random();
	
	public SimulationGraphics() {
		herp2 = new VerticalPanel();
		initWidget(herp2);
		
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				@SuppressWarnings("deprecation")
				
				Options o = Options.create();
				o.setWidth(400);
				o.setHeight(400);				
				
				DataTable t = DataTable.create();
				t.addColumn(ColumnType.NUMBER, "kW");
			
				t.addRows(24);
				for (int i = 0; i < 24; i++)
				{
					t.setValue(i, 0, r.nextInt(1337));
				}
				
				LineChart c = new LineChart(t, o);
				c.setWidth("400px");
				c.setHeight("400px");
				c.setTitle("Herp");
				
				herp2.add(c);
			}
		};
		
		VisualizationUtils.loadVisualizationApi(onLoadCallback, LineChart.PACKAGE);
	}
	
	private Options createOptions() 
	 {
	    Options options = Options.create();
	    options.setWidth(400);
	    options.setHeight(240);	    
	    options.setTitle("Test of ssameting");
	    return options;
	  }
	
	private SelectHandler createSelectHandler(final LineChart chart){
		return new SelectHandler(){
			
			public void onSelect(SelectEvent event){
				String message = "";
				
			/*	// May be multiple selections.
		        JsArray<Selection> selections = chart.getSelections();

		        for (int i = 0; i < selections.length(); i++) {
		          // add a new line for each selection
		          message += i == 0 ? "" : "\n";
		          
		          Selection selection = selections.get(i);

		          if (selection.isCell()) {
		            // isCell() returns true if a cell has been selected.
		            
		            // getRow() returns the row number of the selected cell.
		            int row = selection.getRow();
		            // getColumn() returns the column number of the selected cell.
		            int column = selection.getColumn();
		            message += "cell " + row + ":" + column + " selected";
		          } else if (selection.isRow()) {
		            // isRow() returns true if an entire row has been selected.
		            
		            // getRow() returns the row number of the selected row.
		            int row = selection.getRow();
		            message += "row " + row + " selected";
		          } else {
		            // unreachable
		            message += "Pie chart selections should be either row selections or cell selections.";
		            message += "  Other visualizations support column selections as well.";
		          }
		        }*/

				Window.alert(message);
			}
		};
	}
	
	


}//end of class
