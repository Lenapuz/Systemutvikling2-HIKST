package com.banan.server;

import com.banan.client.UserService;
import com.banan.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.sql.*;

@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet implements UserService 
{
	private Database db;
	
	public UserServiceImpl()
	{
		db = new Database("kark.hin.no/gruppe16", "gruppe16", "php@hin-16");
	}
	
	public String login(String username, String password) throws IllegalArgumentException 
	{
		try
		{
			db.connect();
			Statement statement = db.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM konsulent WHERE Brukernavn='" + username + "' AND Passord=MD5('" + password + "')");
			if (result.next())
			{
				return "OK";
			}
			else
			{
				return "Brukeren finnes ikke.";
			}
		}
		catch (Exception ex)
		{
			return ex.getMessage();
		}
		finally
		{
			db.disconnect();
		}
	}
	
	public String register(String fullName, String username, String password) throws IllegalArgumentException
	{
		try
		{
			db.connect();
			Statement statement = db.createStatement();
			int i = statement.executeUpdate("INSERT konsulent (Brukernavn, Passord) VALUES('" + username + "','" + password + "')");
			
			if (i > 0)
			{
				return "Konsulenten har blitt lagt til.";
			}
			else
			{
				return "Kunne ikke legge til konsulenten.";
			}
		}
		catch (Exception ex)
		{
			return ex.getMessage();
		}
		finally
		{
			db.disconnect();
		}
	}
}
