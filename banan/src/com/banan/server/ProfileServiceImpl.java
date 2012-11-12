package com.banan.server;

import com.banan.shared.*;
import com.google.gwt.user.client.Window;
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
				profile.setStatusMessage("Kunne ikke legge inn profil");
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
				profile.setStatusMessage("Kunne ikke legge inn profil");
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
	
	public Profile getProfileByProfileId(int profile_id) throws IllegalArgumentException {
		try
		{
			db.connect();
			Statement statement = db.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM profil WHERE profil_id = '"+ profile_id+"'");
			
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
			return profiles[0];
		}
		catch(Exception e)
		{
			Profile p = new Profile();
			p.setStatusMessage(e.getMessage());
			return p;
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
				tempHeatsource.add(new Heatsource(result.getInt("id"),  result.getString("name").toLowerCase(), result.getDouble("varmefaktor")));
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
	public Heatsource register(Heatsource heatsource) throws IllegalArgumentException
	{
		try
		{
			db.connect();
			Statement statement = db.createStatement();
			int i = statement.executeUpdate("INSERT heatsource (id, name, varmefaktor) VALUES('" + heatsource.getId() + "','" + heatsource.getName() + "','" + heatsource.getheatFactor() + "')");
			
			if (i >0)
			{
				heatsource.setStatusMessage("Registrering OK");
			}
			else
			{
				heatsource.setStatusMessage("Kunne ikke legge til varmefaktor");
			}
			return heatsource;
		}
		catch (Exception ex)
		{
			heatsource.setStatusMessage(ex.getMessage());
			return heatsource;
		}
		finally
		{
			db.disconnect();
		}
	}
	public Heatsource delete(Heatsource heatsource) throws IllegalArgumentException
	{
		try
		{
			db.connect();
			Statement statement = db.createStatement();
			int i = statement.executeUpdate("DELETE heatsource ('" + heatsource.getId() + "','" + heatsource.getName() + "','" + heatsource.getheatFactor() + "')");
			
			if (i >0)
			{
				heatsource.setStatusMessage("sletting ok");
			}
			else
			{
				heatsource.setStatusMessage("Kunne ikke slette heatsource");
			}
			return heatsource;
		}
		catch (Exception ex)
		{
			heatsource.setStatusMessage(ex.getMessage());
			return heatsource;
		}
		finally
		{
			db.disconnect();
		}
	}

	@Override
	public Profile DeleteProfile(Profile profile)throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		try
		{
			this.db.connect();
			Statement statement = db.createStatement();
			String query = "DELETE FROM profil WHERE profil_id='" + profile.getID()+"'";
			int i = statement.executeUpdate(query);
			
			
			if(i > 0)
			{
				profile.setStatusMessage(profile.getName() + " ble slettet");
			}
			else
			{
				profile.setStatusMessage("Kunne ikke slette angitt profil: " + profile.getName());
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
	public Profile EditProfile(Profile profile) throws IllegalArgumentException 
	{
		try
		{
			this.db.connect();
			Statement statement = db.createStatement();
			
			String query = "UPDATE profil SET name = '" + profile.getName() + "', build_year = '" + profile.getBuildYear() + "', is_isolated = '" + profile.getIsisolated() + "', profil_type = '" + profile.getTypePofile() + "', prim_heating = '" + profile.getPrimHeating() + "', house_residents = '" + profile.getHouseResidents() + "', house_size = '" + profile.getHouseSize() +"'  WHERE name = '" + profile.getName() + "'";
			int i = statement.executeUpdate(query);
			
			if(i > 0)
			{
				profile.setStatusMessage("Profilen ble oppdatert");
				return profile;
			}
			else
			{
				profile.setStatusMessage("Profilen ble ikke oppdatert");
				return profile;
			}
		}
		catch(Exception ex)
		{
			profile.setStatusMessage(ex.getMessage());
			return profile;
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
	 * 		

	 * getHeatsourceById
	 * getHeatsourceByName
	 * editFactor(double factor)
	 */
}
	
