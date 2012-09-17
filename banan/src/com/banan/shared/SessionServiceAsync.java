package com.banan.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SessionServiceAsync {
	void get(String attr, AsyncCallback<Integer> callback);
	void set(String attr, Integer value, AsyncCallback<Void> callback);
}
