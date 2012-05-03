package com.banan.server;

import java.sql.*;

public class Database 
{
	private Connection conn;
	
	private String url;
	private String username;
	private String password;
	
	public Database(String url, String username, String password)
	{
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public void connect() throws SQLException, ClassNotFoundException
	{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://" + url, username, password);
	}
	
	public void disconnect()
	{
		try
		{
			conn.close();
		}
		catch(Exception ex)
		{
		}
	}
	
	public Statement createStatement() throws SQLException
	{
		return conn.createStatement();
	}
}
