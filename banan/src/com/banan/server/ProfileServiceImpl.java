package com.banan.server;

import com.banan.shared.*;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.sql.*;
import java.util.ArrayList;


/***
 *  Dette er bare test Sp�rringer mot en test Database. Ting vil bli endret p�.
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
			int i = statement.executeUpdate("INSERT profil (build_year, is_isolated, profil_type, prim_heating, ) VALUES('" + profile.getBuildYear() + "','" + profile.getIsisolated() + "','" + profile.getTypePofile() + "','" + profile.getPrimHeating() + "')");
			
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
			//test
			db.connect();
			Statement statement = db.createStatement();
			int i = statement.executeUpdate("INSERT profil (name, build_year, is_isolated, profil_type, prim_heating, house_residents, house_size) VALUES('" + profile.getName() + "','" + profile.getBuildYear() + "'," +
					"'" + profile.getIsisolated() + "','" + profile.getTypePofile() + "','" + profile.getPrimHeating() +"','" + profile.getHouseResidents() + "','" + profile.getHouseSize() + "')");
			
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
				Profile p = new Profile(result.getString("name"), result.getString("build_year"), result.getString("profil_type"), result.getString("prim_heating"), result.getString("is_isolated"), result.getString("house_residents"), result.getString("house_size"));
				p.setID(result.getInt("profil_id"));
				tempProfiles.add(p);
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
			Profile p = new Profile();
			p.setStatusMessage(ex.getMessage());
			return new Profile[] { p };
		}
		finally
		{
			db.disconnect();
		}
	}
	
	public Heatsource[] getHeatsources() throws IllegalArgumentException {
		try
		{
			db.connect();
			Statement statement = db.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM heatsource");
			
			ArrayList<Heatsource> tempHeatsource = new ArrayList<Heatsource>();
			while (result.next())
			{
				tempHeatsource.add(new Heatsource(result.getInt("id"), result.getString("name"), result.getDouble("varmefaktor")));
			}
			
			Heatsource[] heatsource = new Heatsource[tempHeatsource.size()];
			for (int i = 0; i < tempHeatsource.size(); i++)
			{
				heatsource[i] = tempHeatsource.get(i);
			}
			return heatsource;
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}
		finally
		{
			db.disconnect();
		}
	}
	/**
	 * To do med Heatsource
	 * Skrive egen HeatsourceServiceImpl.java!
	 * 
	 * deleteHeatsource 		
	 * addHeatsource
	 * getHeatsourceById
	 * getHeatsourceByName
	 * editFactor(double factor)
	 */
}
