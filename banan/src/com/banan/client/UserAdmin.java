package com.banan.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UserAdmin extends Composite 
{
	private VerticalPanel panel;
	private TextBox tb = new TextBox();
	
	public UserAdmin()
	{
		panel = new VerticalPanel();
		panel.addStyleName("userAdmin");
		tb.setText("THIS IS A TEST");
		
		initWidget(panel);
		
		panel.add(tb);			
	}
}
