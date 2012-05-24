package com.banan.server;

import com.banan.client.ProfileService;

import com.banan.shared.Profile;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.sql.*;


/***
 *  Dette er bare test Spørringer mot en test Database. Ting vil bli endret på.
 * @author Martin
 *Klassen implementere ProfileService.
 */
@SuppressWarnings("serial")
public class ProfileServiceImpl  extends RemoteServiceServlet implements ProfileService  
{
	
private Database db;
	//Db Tilkobling mot Kark.hin.no
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
			int i = statement.executeUpdate("INSERT profile (profilID, buildYear, houseSize) VALUES('" + profile.getProfilID() + "'('" + profile.getBuildYear() + "'),'" + profile.getHouseSize() + "')");
			
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
			int i = statement.executeUpdate("INSERT profile (profilID, buildYear, houseSize) VALUES('" + profile.getProfilID() + "',MD5('" + profile.getBuildYear() + "'),'" + profile.getHouseSize() + "')");
			
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
	

}
