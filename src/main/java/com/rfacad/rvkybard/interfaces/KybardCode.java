package com.rfacad.rvkybard.interfaces;

import java.util.HashMap;
import java.util.Map;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//

public enum KybardCode {
	RESERVED(0x00,"Reserved"),
	ROLLOVER(0x01,"ErrorRollOver"),
	POSTFAIL(0x02,"POSTFail"),
	ERROR(0x03,"ErrorUndefined"),
	KB_A(0x04,"A","a"),
	KB_B(0x05,"B","b"),
	KB_C(0x06,"C","c"),
	KB_D(0x07,"D","d"),
	KB_E(0x08,"E","e"),
	KB_F(0x09,"F","f"),
	KB_G(0x0A,"G","g"),
	KB_H(0x0B,"H","h"),
	KB_I(0x0C,"I","i"),
	KB_J(0x0D,"J","j"),
	KB_K(0x0E,"K","k"),
	KB_L(0x0F,"L","l"),
	KB_M(0x10,"M","m"),
	KB_N(0x11,"N","n"),
	KB_O(0x12,"O","o"),
	KB_P(0x13,"P","p"),
	KB_Q(0x14,"Q","q"),
	KB_R(0x15,"R","r"),
	KB_S(0x16,"S","s"),
	KB_T(0x17,"T","t"),
	KB_U(0x18,"U","u"),
	KB_V(0x19,"V","v"),
	KB_W(0x1A,"W","w"),
	KB_X(0x1B,"X","x"),
	KB_Y(0x1C,"Y","y"),
	KB_Z(0x1D,"Z","z"),
	KB_1(0x1E,"1","!"),
	KB_2(0x1F,"2","@"),
	KB_3(0x20,"3","#"),
	KB_4(0x21,"4","$"),
	KB_5(0x22,"5","%"),
	KB_6(0x23,"6","^"),
	KB_7(0x24,"7","&"),
	KB_8(0x25,"8","*"),
	KB_9(0x26,"9","("),
	KB_0(0x27,"0",")"),
	KB_ENTER(0x28,"Return","Keyboard Return","Keyboard Enter"), // Keypad Enter is 0x58
	KB_ESCAPE(0x29,"Escape"),
	KB_BACKSPACE(0x2A,"Backspace"), // officially called "Delete" but it' backspace
	KB_TAB(0x2B,"Tab"),
	KB_SPACE(0x2C,"Spacebar"), // or Space Barn
	KB_DASH(0x2D,"-","_"),
	KB_EQUAL(0x2E,"=","+"),
	KB_OPEN_BRACKET(0x2F,"[","{"),
	KB_CLOSE_BRACKET(0x30,"]","}"),
	KB_BACKSLASH(0x31,"\\","|"),
	KB_OCTOTHORPE(0x32,"NON-US #","NON-US ~"), // Non-US # and ~
	KB_SEMICOLON(0x33,";",":"),
	KB_APOSTROPHE(0x34,"'","\u2019","KB_QUOTE"), // and open double-quote
	KB_BACKQUOTE(0x35,"`","~"),
	KB_COMMA(0x36,",","Comma","<"), // note that this string is going to be sent in a comma-separated list!
	KB_DOT(0x37,".",">"),
	KB_SLASH(0x38,"/","?"),
	KB_CAPSLOCK(0x39,"Caps Lock"), // Use the flag instead
	KB_F1(0x3A,"F1"),
	KB_F2(0x3B,"F2"),
	KB_F3(0x3C,"F3"),
	KB_F4(0x3D,"F4"),
	KB_F5(0x3E,"F5"),
	KB_F6(0x3F,"F6"),
	KB_F7(0x40,"F7"),
	KB_F8(0x41,"F8"),
	KB_F9(0x42,"F9"),
	KB_F10(0x43,"F10"),
	KB_F11(0x44,"F11"),
	KB_F12(0x45,"F12"),
	KB_PRTSCR(0x46,"Print Screen"),
	KB_SCROLLLOCK(0x47,"Scroll Lock"), // Use the flag instead
	KB_PAUSE(0x48,"Pause","Break"),
	KB_INSERT(0x49,"Insert"),
	KB_HOME(0x4A,"Home"),
	KB_PGUP(0x4B,"Page Up"),
	KB_DELETE(0x4C,"Delete"), // officially "Delete Forward"
	KB_END(0x4D,"End"),
	KB_PGDN(0x4E,"Page Down"),
	KB_RIGHTARROW(0x4F,"Right"),
	KB_LEFTARROW(0x50,"Left"),
	KB_DOWNARROW(0x51,"Down"),
	KB_UPARROW(0x52,"Up"),

	KP_NUMLOCK(0x53,"Num Lock"), // Use the flag instead
	KP_DIVIDE(0x54,"Keypad /","Divide"), // actually /
	KP_MULTIPLY(0x55,"Keypad *","Multiply"), // actually *
	KP_MINUS(0x56,"Keypad -","Minus"), // actually -
	KP_ADD(0x57,"Keypad +","Add"), // actually +
	KP_ENTER(0x58,"Enter","Keypad Enter","Keypad Return"), // Keyboard enter is 0x28
	KP_1(0x59,"Keypad 1","Keypad End"), // and End
	KP_2(0x5A,"Keypad 2","Keypad Down Arrow"),
	KP_3(0x5B,"Keypad 3","Keypad Page Down"),
	KP_4(0x5C,"Keypad 4","Keypad Left Arrow"),
	KP_5(0x5D,"Keypad 5"), //
	KP_6(0x5E,"Keypad 6","Keypad Right Arrow"),
	KP_7(0x5F,"Keypad 7","Keypad Home"),
	KP_8(0x60,"Keypad 8","Keypad Up Arrow"),
	KP_9(0x61,"Keypad 9","Keypad Page Up"),
	KP_0(0x62,"Keypad 0","Keypad Insert"),
	KP_DOT(0x63,"Keypad .","Keypad Delete"),
	KP_BACKSLASH(0x64,"Keypad \\"), // Non-US \ and |

	KB_WINDOWS(0x65,"Windows"), // or Compose; Officially "Keyboard Application" but see 0xE3
	KB_POWER(0x66,"Power"), // Reserved for typical status and errors, not a physical key
	KP_EQUALS(0x67,"Keypad ="),
	KB_F13(0x68,"F13"),
    // 0x68..73 F13..F24
	KB_EXECUTE(0x74,"Execute"),
	KB_HELP(0x75,"Help"),
	KB_MENU(0x76,"Menu"),
    // and then a whole BUNCH of stuff
	KB_LEFT_CTRL(0xE0,"Left Control"),
	KB_LEFT_SHIFT(0xE1,"Left Shift"),
	KB_LEFT_ALT(0xE2,"Left Alt"),
	KB_LEFT_GUI(0xE3,"Left GUI"), // Windows, Apple, but see 0x65
	KB_RIGHT_CTRL(0xE4,"Right Control"),
	KB_RIGHT_SHIFT(0xE5,"Right Shift"),
	KB_RIGHT_ALT(0xE6,"Right Alt"),
	KB_RIGHT_GUI(0xE7,"Right Gui"), // Windows, Apple
    KB_RESERVED_E8(0xE8,"Reserved E8"); // All remaining keys are RESERVED

	private static Map<String,KybardCode> lookup;
	private byte code;
    private String name;

	KybardCode(int code, String ... names)
	{
		this.code=(byte)(code&0xff);
        this.name=names[0];
        load(this,names);
	}

	private synchronized static void load(KybardCode k,String [] names)
	{
		if ( lookup == null )
		{
			 lookup = new HashMap<>();
		}
		for(String n : names)
		{
			if ( lookup.get(n) != null )
			{
				throw new IllegalArgumentException("Duplicate key "+n);
			}
			lookup.put(n,k);
		}
	}
	public byte getCode()
	{
		return this.code;
	}

    public String getName()
    {
        return this.name;
    }

    public static KybardCode lookup(String s)
    {
    	try
    	{
    		return valueOf(s);
    	}
    	catch (IllegalArgumentException e)
    	{
    		KybardCode ret = lookup.get(s);
    		if ( ret == null )
    		{
    			throw(e);
    		}
    		return ret;
    	}
    }
}
