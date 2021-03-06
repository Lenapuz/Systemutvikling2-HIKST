package com.banan.client;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.CalendarUtil;

public class Login extends Composite 
{
	private VerticalPanel panel;
	private TextBox textBoxUsername;
	private PasswordTextBox textBoxPassword;
	private CheckBox checkBoxPassword;
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
		checkBoxPassword = new CheckBox();
		checkBoxPassword.setText("Husk passord");
		buttonOK = new Button("Logg inn");
		buttonOK.addStyleName("btn btn-primary");
		
		textBoxUsername.setWidth("150px");
		textBoxPassword.setWidth("150px");
		buttonOK.setWidth("164px");
		
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
		panel.add(checkBoxPassword);
		panel.add(buttonOK);
		
		if (Cookies.getCookie("lastUser") != null) {
			textBoxUsername.setText(Cookies.getCookie("lastUser"));
		}
		if (Cookies.getCookie("lastPassword") != null) {
			textBoxPassword.setText(Cookies.getCookie("lastPassword"));
			checkBoxPassword.setChecked(true);
		}
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
		Date date = new Date();
		CalendarUtil.addDaysToDate(date, 7);
		
		Cookies.setCookie("lastUser", getUsername(), date);
		
		if (checkBoxPassword.isChecked()) {
			Cookies.setCookie("lastPassword", getPassword(), date);
		}
		else {
			Cookies.setCookie("lastPassword", null);
		}
		
		for (ActionHandler handler : loginHandlers)
		{
			handler.onAction();
		}
	}
}
