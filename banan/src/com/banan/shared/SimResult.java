package com.banan.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SimResult implements Serializable
{
	private int magic;
	
	public SimResult() {}
	
	public SimResult(int magic)
	{
		this.magic = magic;
	}
	
	public int getMagic()
	{
		return magic;
	}
}
