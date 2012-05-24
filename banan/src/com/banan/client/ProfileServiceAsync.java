package com.banan.client;

import com.banan.shared.Profile;
import com.google.gwt.user.client.rpc.AsyncCallback;

/***
 * Inerface som kaller på metoden registrer og Profilesom pusher til DB i tabell "profiler"
 * @author Martin
 * NB! NB! disse to metodene gjør det samme, skal få rettet dette opp til kun 1 metode.
 */

public interface ProfileServiceAsync 
{
	void register(Profile profile, AsyncCallback<Profile> callback);

	void profil(Profile profile, AsyncCallback<Profile> callback);

	//void profil(Profile profile, Object callback);
}
