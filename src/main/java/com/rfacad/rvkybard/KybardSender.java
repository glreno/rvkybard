package com.rfacad.rvkybard;

import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rfacad.rvkybard.interfaces.KybardFlag;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//

public class KybardSender
{
	Logger LOG = LoggerFactory.getLogger(KybardSender.class);
    private static final byte [] ALLUP = new byte[] { 0,0,0,0, 0,0,0,0 };

    private byte [] buf = new byte[8];

	private OutputStream out;

	public KybardSender(OutputStream out)
	{
		this.out = out;
	}

	public synchronized void sendReleaseAllKeys() throws IOException
	{
		LOG.info("SEND RELEASE ALL");
		out.write(ALLUP);
	}

	public synchronized void sendKey(byte flags,byte keycode) throws IOException
	{
		LOG.info("SEND {} {}",flags,keycode);
		buf[0]=flags;
		buf[2]=keycode;
		out.write(buf);
	}
	
	public void sendKey(byte keycode) throws IOException
	{
		sendKey(KybardFlag.NONE.getBits(),keycode);
	}
}
