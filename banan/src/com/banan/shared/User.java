package com.banan.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable
{
	private int id;
	private String name;
	private String username;
	private String password;
	private String type;
	private String statusMessage;
	private boolean loggedIn;
	
	public User() { type = ""; }
	
	public User(String username, String password)
	{
		this("", username, password, "");
	}
	
	public User(String name, String username, String password)
	{
		this(name, username, password, "");
	}
	
	public User(String name, String username, String password, String type)
	{
		this.name = name;
		this.username = username;
		this.password = password;
		this.type = type;
	}
	
	public User(int id, String name, String username, String password, String type)
	{
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.type = type;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getStatusMessage()
	{
		return statusMessage;
	}
	
	public void setStatusMessage(String message)
	{
		statusMessage = message;
	}
	
	public boolean isLoggedIn()
	{
		return loggedIn;
	}
	
	public void login()
	{
		loggedIn = true;
	}
}
