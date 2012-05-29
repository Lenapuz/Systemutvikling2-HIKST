package com.banan.client;
import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.*;

public class Login extends Composite 
{
	private VerticalPanel panel;
	private TextBox textBoxUsername;
	private PasswordTextBox textBoxPassword;
	private Button buttonOK;
	private ArrayList<ActionHandler> loginHandlers = new ArrayList<ActionHandler>();
	
	public Login()
	{
		panel = new VerticalPanel();
		panel.addStyleName("login");
		initWidget(panel);
		
		textBoxUsername = new TextBox();
		textBoxUsername.getElement().setPropertyString("placeholder", "Brukernavn");
		textBoxPassword = new PasswordTextBox();
		textBoxPassword.getElement().setPropertyString("placeholder", "Passord");
		buttonOK = new Button("Logg inn");		
		
		textBoxUsername.setWidth("150px");
		textBoxPassword.setWidth("150px");
		buttonOK.setWidth("160px");
		
		buttonOK.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				onLogin();
			}
		});
		
		textBoxPassword.addKeyPressHandler(new KeyPressHandler()
		{
			public void onKeyPress(KeyPressEvent event)
			{
				if(event.getCharCode() == KeyCodes.KEY_ENTER)
				{
					onLogin();
				}
			}
		});
		
		panel.add(textBoxUsername);
		panel.add(textBoxPassword);
		panel.add(buttonOK);
	}
	
	public String getUsername()
	{
		return textBoxUsername.getText();
	}
	
	public String getPassword()
	{
		return textBoxPassword.getText();
	}
	
	public void addLoginHandler(ActionHandler handler)
	{
		loginHandlers.add(handler);
	}
	
	protected void onLogin()
	{
		for (ActionHandler handler : loginHandlers)
		{
			handler.onAction();
		}
	}
}
