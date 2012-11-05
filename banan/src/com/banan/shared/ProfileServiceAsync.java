package com.banan.shared;

import com.banan.shared.Profile;
import com.google.gwt.user.client.rpc.AsyncCallback;

/***
 * Inerface som kaller p� metoden registrer og Profilesom pusher til DB i tabell "profiler"
 * @author Martin
 * NB! NB! disse to metodene gj�r det samme, skal f� rettet dette opp til kun 1 metode.
 */

public interface ProfileServiceAsync 
{
	void register(Profile profile, AsyncCallback<Profile> callback);
	void profil(Profile profile, AsyncCallback<Profile> callback);
	void getProfiles(AsyncCallback<Profile[]> callback);
	
	void DeleteProfile(Profile profile, AsyncCallback<Profile> callback) throws IllegalArgumentException;
	
	
	
	
}
