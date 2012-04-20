package com.banan.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync 
{
	void login(String username, String password, AsyncCallback<String> callback) throws IllegalArgumentException;
	void register(String fullName, String username, String password, AsyncCallback<String> callback) throws IllegalArgumentException;
}
