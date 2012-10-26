package com.banan.shared;

import com.banan.shared.*;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("sim")
public interface SimService extends RemoteService
{
	SimResult simulate(int profileID, int temperatur);
	SimResult[] GetSimResultByProfileId(int profileID);
	
}
