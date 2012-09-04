package com.banan.server;

import com.banan.shared.*;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SimServiceImpl extends RemoteServiceServlet implements SimService
{
	public SimResult simulate(int profileID)
	{
		return new SimResult(profileID * 1337);
	}
}
