package com.banan.shared;

import com.banan.shared.*;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SimServiceAsync
{
	void simulate(int profileID, int temperatur, AsyncCallback<SimResult> callback);
	void GetSimResultByProfileId(int profileID, AsyncCallback<SimResult[]> callback);
	void purgeIt(AsyncCallback<Void> callback);
}
