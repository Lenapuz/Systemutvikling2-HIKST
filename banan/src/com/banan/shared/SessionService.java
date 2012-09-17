package com.banan.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.banan.shared.*;

@RemoteServiceRelativePath("session")
public interface SessionService extends RemoteService {
	Integer get(String attr);
	void set(String attr, Integer value);
}
