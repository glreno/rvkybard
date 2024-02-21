package com.rfacad.rvkybard;

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
public enum KybardFlags {
	NONE(0),
	SHIFT(32);

	private byte bits;
	KybardFlags(int bits)
	{
		this.bits=(byte)(bits&0xff);
	}

	public byte getBits()
	{
		return this.bits;
	}
}
