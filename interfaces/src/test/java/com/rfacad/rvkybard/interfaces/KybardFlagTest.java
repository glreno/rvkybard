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
public class KybardFlagTest
{
    @Test
    public void shouldFindShift()
    {
        KybardFlag k = KybardFlag.valueOf("LEFT_SHIFT");
        assertEquals("Left Shift",k.getName());
        assertEquals(2,k.getBits());
        assertEquals(KybardFlag.LEFT_SHIFT,k);
    }

    @Test
    public void shouldFindLock()
    {
        KybardLed k = KybardLed.valueOf("SCROLLLOCK");
        assertEquals("Scroll Lock",k.getName());
        assertEquals(4,k.getBits());
        assertEquals(KybardLed.SCROLLLOCK,k);
    }
}
