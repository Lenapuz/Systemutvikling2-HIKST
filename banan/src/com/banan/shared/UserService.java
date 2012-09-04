package com.banan.shared;

import com.banan.shared.*;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService 
{
	User login(User user) throws IllegalArgumentException;
	User register(User user) throws IllegalArgumentException;
}
