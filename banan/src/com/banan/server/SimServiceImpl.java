package com.banan.server;

import com.banan.shared.*;

public class SimServiceImpl 
{
	public SimResult simulate(int profileID)
	{
		return new SimResult(profileID * 1337);
	}
}
