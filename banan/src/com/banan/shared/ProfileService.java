package com.banan.shared;

import com.banan.shared.*;

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
	Profile[] getProfiles() throws IllegalArgumentException;	
	
	//User DeleteUser(User user) throws IllegalArgumentException;
	Profile DeleteProfile(Profile profile) throws IllegalArgumentException;
	
	//Profile editProfile(Profile profile) throws IllegalArgumentException;
}
