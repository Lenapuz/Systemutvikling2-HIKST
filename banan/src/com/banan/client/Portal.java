package com.banan.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.*;

public class Portal extends Composite {
	private DeckPanel mainPanel;
	
	public Portal() {
		mainPanel = new DeckPanel();
		initWidget(mainPanel);
		
		VerticalPanel p1 = new VerticalPanel();
		p1.add(new Label("DETTAN E PORTALN!111!1"));
		mainPanel.add(p1);
		
		mainPanel.showWidget(0);
	}
}
