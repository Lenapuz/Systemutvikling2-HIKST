package com.banan.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

public class Registration extends Composite 
{
	private VerticalPanel panel;
	private TextBox textBoxFullName;
	private TextBox textBoxUsername;
	private PasswordTextBox textBoxPassword;
	private Button buttonOK;
	private ArrayList<ActionHandler> registerHandlers = new ArrayList<ActionHandler>();
	
	public Registration()
	{
		panel = new VerticalPanel();
		panel.addStyleName("register");
		initWidget(panel);
		
		textBoxFullName = new TextBox();
		textBoxFullName.getElement().setPropertyString("placeholder", "Navn");
		textBoxUsername = new TextBox();
		textBoxUsername.getElement().setPropertyString("placeholder", "Brukernavn");
		textBoxPassword = new PasswordTextBox();
		textBoxPassword.getElement().setPropertyString("placeholder", "Passord");
		buttonOK = new Button("OK");
		
		textBoxFullName.setWidth("150px");
		textBoxUsername.setWidth("150px");
		textBoxPassword.setWidth("150px");
		buttonOK.setWidth("160px");
		
		buttonOK.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				onRegister();
			}
		});
		
		panel.add(textBoxFullName);
		panel.add(textBoxUsername);
		panel.add(textBoxPassword);
		panel.add(buttonOK);
	}
	
	public String getFullName()
	{
		return textBoxFullName.getText();
	}
	
	public String getUsername()
	{
		return textBoxUsername.getText();
	}
	
	public String getPassword()
	{
		return textBoxPassword.getText();
	}
	
	public void addRegisterHandler(ActionHandler handler)
	{
		registerHandlers.add(handler);
	}
	
	protected void onRegister()
	{
		for (ActionHandler handler : registerHandlers)
		{
			handler.onAction();
		}
	}
}
