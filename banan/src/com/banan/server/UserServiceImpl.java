package com.banan.server;

import com.banan.shared.*;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.sql.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet implements UserService 
{
	private Database db;
	
	public UserServiceImpl()
	{
		db = new Database("kark.hin.no/gruppe16", "gruppe16", "php@hin-16");
	}
	
	public User login(User user) throws IllegalArgumentException 
	{
		try
		{
			db.connect();
			Statement statement = db.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM konsulent WHERE Brukernavn='" + user.getUsername() + "' AND Passord=MD5('" + user.getPassword() + "')");
			if (result.next())
			{
				user.login();
				user.setType(result.getString("status"));
			}
			else
			{
				user.setStatusMessage("Brukeren finnes ikke.");
			}
			return user;
		}
		catch (Exception ex)
		{
			user.setStatusMessage(ex.getMessage());
			return user;
		}
		finally
		{
			db.disconnect();
		}
	}
	
	public User register(User user) throws IllegalArgumentException
	{
		try
		{
			db.connect();
			Statement statement = db.createStatement();
			int i = statement.executeUpdate("INSERT konsulent (Brukernavn, Passord, status) VALUES('" + user.getUsername() + "',MD5('" + user.getPassword() + "'),'" + user.getType() + "')");
			
			if (i > 0)
			{
				user.setStatusMessage("Brukeren har blitt lagt til.");
			}
			else
			{
				user.setStatusMessage("Kunne ikke legge til brukeren.");
			}
			return user;
		}
		catch (Exception ex)
		{
			user.setStatusMessage(ex.getMessage());
			return user;
		}
		finally
		{
			db.disconnect();
		}
	}
	public User DeleteUser(User user) throws IllegalArgumentException 
	{
		boolean result;
		try
		{
			this.db.connect();
			Statement statement = db.createStatement();
			
			int i = statement.executeUpdate("DELETE FROM konsulent WHERE Brukernavn=" + user.getUsername());
			
			if(i > 0)
			{
				user.setStatusMessage("Brukeren ble slettet");
			}
			else
			{
				user.setStatusMessage("Kunne ikke slette brukeren");
			}
			return user;
		}
		catch (Exception ex)
		{
			user.setStatusMessage(ex.getMessage());
			return user;
		}
		finally
		{
			db.disconnect();
		}
	}

	@Override
	public User EditUser(User user) throws IllegalArgumentException 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User[] GetUsers() throws IllegalArgumentException 
	{
		try
		{
			this.db.connect();
			Statement statement = db.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM konsulent");
			ArrayList<User> tempUsers = new ArrayList<User>();
			
			while (result.next())
			{
				tempUsers.add(new User(result.getString("Brukernavn"), result.getString("Brukernavn"), result.getString("Passord"), result.getString("status")));
			}
			User[] users = new User[tempUsers.size()];
			
			for (int i = 0; i < tempUsers.size(); i++)
			{
				users[i] = tempUsers.get(i);
			}
			return users;
			
		}
		catch(Exception ex)
		{
			return null;
		}
		finally
		{
			db.disconnect();
		}
	}
}
