package com.rfacad.rvkybard;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//

public class KybardSenderTest
{
	@Test
	public void shouldSendReleaseAll() throws IOException
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		KybardSender ks = new KybardSender(out);
		ks.sendReleaseAllKeys();
		byte [] b = out.toByteArray();
		assertEquals(8,b.length);
		for(int i=0;i<8;i++)
		{
			assertEquals(0,b[i]);
		}
	}

	@Test
	public void shouldSendShift() throws IOException
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		KybardSender ks = new KybardSender(out);
		ks.sendKey(KybardFlags.SHIFT.getBits(),(byte)5); // B
		byte [] b = out.toByteArray();
		assertEquals(8,b.length);
		assertEquals(32,b[0]);
		assertEquals(0,b[1]);
		assertEquals(5,b[2]);
		assertEquals(0,b[3]);
		assertEquals(0,b[4]);
		assertEquals(0,b[5]);
		assertEquals(0,b[6]);
		assertEquals(0,b[7]);
	}

	@Test
	public void shouldSendKey() throws IOException
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		KybardSender ks = new KybardSender(out);
		ks.sendKey((byte)6); // c
		byte [] b = out.toByteArray();
		assertEquals(8,b.length);
		assertEquals(0,b[0]);
		assertEquals(0,b[1]);
		assertEquals(6,b[2]);
		assertEquals(0,b[3]);
		assertEquals(0,b[4]);
		assertEquals(0,b[5]);
		assertEquals(0,b[6]);
		assertEquals(0,b[7]);
	}

}
