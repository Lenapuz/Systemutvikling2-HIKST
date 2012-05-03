package com.banan.client;

import com.banan.shared.*;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync 
{
	void login(User user, AsyncCallback<User> callback) throws IllegalArgumentException;
	void register(User user, AsyncCallback<User> callback) throws IllegalArgumentException;
}
