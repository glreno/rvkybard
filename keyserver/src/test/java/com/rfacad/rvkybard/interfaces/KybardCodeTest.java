package com.rfacad.rvkybard.interfaces;

import static org.junit.Assert.*;

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

public class KybardCodeTest
{
	@Test
	public void shouldFindA()
	{
		// symbolic code
		KybardCode k1 = KybardCode.lookup("KB_A");
		assertEquals(KybardCode.KB_A,k1);
		assertEquals(4,k1.getCode());
		assertEquals("A",k1.getName());

		// Primary name
		KybardCode k2 = KybardCode.lookup("A");
		assertEquals(KybardCode.KB_A,k2);
		// Alternate name
		KybardCode k3 = KybardCode.lookup("a");
		assertEquals(KybardCode.KB_A,k3);
	}

	@Test
	public void shouldFindNumbers()
	{
		// symbolic code
		KybardCode k1 = KybardCode.lookup("KB_1");
		assertEquals(KybardCode.KB_1,k1);
		assertEquals(0x1E,k1.getCode());
		assertEquals("1",k1.getName());

		KybardCode k2 = KybardCode.lookup("KP_1");
		assertEquals(KybardCode.KP_1,k2);
		assertEquals(0x59,k2.getCode());
		assertEquals("Keypad 1",k2.getName());

		// Primary name
		KybardCode k3 = KybardCode.lookup("1");
		assertEquals(KybardCode.KB_1,k3);
		KybardCode k4 = KybardCode.lookup("Keypad 1");
		assertEquals(KybardCode.KP_1,k4);
		// Alternate name
		KybardCode k5 = KybardCode.lookup("!");
		assertEquals(KybardCode.KB_1,k5);
		KybardCode k6 = KybardCode.lookup("Keypad End");
		assertEquals(KybardCode.KP_1,k6);
	}

	@Test
	public void shouldThrowOnBadKey()
	{
		try
		{
			KybardCode.lookup("this is not a defined key");
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// expected
		}
	}
}
