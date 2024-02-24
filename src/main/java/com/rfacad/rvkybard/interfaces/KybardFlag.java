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

/** The bit flags that represent shift,ctrl,alt,etc in byte 0 of the key message */
public enum KybardFlag {
	NONE(0,""),
	LEFT_CTRL(0x01,"Left Control"),
	LEFT_SHIFT(0x02,"Left Shift"),
	LEFT_ALT(0x04,"Left Alt"),
	LEFT_GUI(0x08,"Left Gui"), // Windows, Apple, etc
	RIGHT_CTRL(0x10,"Right Control"),
	RIGHT_SHIFT(0x20,"Right Shift"),
	RIGHT_ALT(0x40,"Right Alt"),
	RIGHT_GUI(0x80,"Right Gui"); // Windows, Apple, etc

	private byte bits;
    private String name;

	KybardFlag(int bits, String name)
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
