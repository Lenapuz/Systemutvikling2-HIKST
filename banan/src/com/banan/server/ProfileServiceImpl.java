package com.banan.server;

import com.banan.shared.*;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.sql.*;
import java.util.ArrayList;


/***
 *  Dette er bare test Spørringer mot en test Database. Ting vil bli endret på.
 * @author Martin
 *Klassen implementere ProfileService.
 */
@SuppressWarnings("serial")
public class ProfileServiceImpl  extends RemoteServiceServlet implements ProfileService  
{	
	private Database db;
	
	public ProfileServiceImpl()
	{
		db = new Database("kark.hin.no/gruppe16", "gruppe16", "php@hin-16");
	}
	
	public Profile register(Profile profile) throws IllegalArgumentException
	{
		try
		{
			db.connect();
			Statement statement = db.createStatement();
			int i = statement.executeUpdate("INSERT profil (build_year, profil_type, prim_heating, is_isolated) VALUES('" + profile.getBuildYear() + "','" + profile.getTypePofile() + "','" + profile.getPrimHeating() + "','" + profile.getIsisolated() + "')");
			
			if (i >0)
			{
				profile.setStatusMessage("Registrering OK");
			}
			else
			{
				profile.setStatusMessage("Kunne ikk elegge inn profil");
			}
			return profile;
		}
		catch (Exception ex)
		{
			profile.setStatusMessage(ex.getMessage());
			return profile;
		}
		finally
		{
			db.disconnect();
		}
	}

	@Override
	public Profile profil(Profile profile) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		try
		{
			
			db.connect();
			Statement statement = db.createStatement();
			int i = statement.executeUpdate("INSERT profil (name, build_year, profil_type, prim_heating, is_isolated, house_residents, house_size) VALUES('" + profile.getName() + "','" + profile.getBuildYear() + "'," +
					"'" + profile.getTypePofile() + "','" + profile.getPrimHeating() + "','" + profile.getIsisolated() +"','" + profile.getHouseResidents() + "','" + profile.getHouseSize() + "')");
			
			if (i >0)
			{
				profile.setStatusMessage("Registreing OK");
			}
			else
			{
				profile.setStatusMessage("Kunne ikk elegge inn profil");
			}
			return profile;
		}
		catch (Exception ex)
		{
			profile.setStatusMessage(ex.getMessage());
			return profile;
		}
		finally
		{
			db.disconnect();
		}
	}
	
	public Profile[] getProfiles() throws IllegalArgumentException {
		try
		{
			db.connect();
			Statement statement = db.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM profil");
			
			ArrayList<Profile> tempProfiles = new ArrayList<Profile>();
			while (result.next())
			{
				tempProfiles.add(new Profile(result.getString("name"), result.getString("build_year"), result.getString("profil_type"), result.getString("prim_heating"), result.getString("is_isolated"), result.getString("house_residents"), result.getString("house_size")));
			}
			
			Profile[] profiles = new Profile[tempProfiles.size()];
			for (int i = 0; i < tempProfiles.size(); i++)
			{
				profiles[i] = tempProfiles.get(i);
			}
			return profiles;
		}
		catch (Exception ex)
		{
			return null;
		}
		finally
		{
			db.disconnect();
		}
	}
}
