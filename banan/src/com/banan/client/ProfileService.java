package com.banan.client;

import com.banan.shared.Profile;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("profile")

/***
 * Interface for profile
 */
public interface ProfileService extends RemoteService 
{
	Profile register(Profile profile) throws IllegalArgumentException;
	
	Profile profil(Profile profile) throws IllegalArgumentException;
	
	
}
