package com.rfacad.rvkybard.interfaces;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//

/** The bit flags that represent the LEDs in a key message */
public enum KybardLed {
	UNDEFINED(0,"undefined"),
	NUMLOCK(0x01,"Num Lock"),
	CAPSLOCK(0x02,"Caps Lock"),
	SCROLLLOCK(0x04,"Scroll Lock"),
	COMPOSE(0x08,"Compose"),
	KANA(0x10,"Kana");

	private byte bits;
    private String name;
	KybardLed(int bits,String name)
	{
		this.bits=(byte)(bits&0xff);
        this.name=name;
	}

	public byte getBits()
	{
		return this.bits;
	}

    public String getName()
    {
        return this.name;
    }
}
