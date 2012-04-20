package com.banan.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService 
{
	String login(String username, String password) throws IllegalArgumentException;
	String register(String fullName, String username, String password) throws IllegalArgumentException;
}
