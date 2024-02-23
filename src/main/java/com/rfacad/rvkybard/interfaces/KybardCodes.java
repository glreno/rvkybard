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
public enum KybardCodes {
	RESERVED(0x00,"Reserved"),
	ROLLOVER(0x01,"ErrorRollOver"),
	POSTFAIL(0x02,"POSTFail"),
	ERROR(0x03,"ErrorUndefined"),
	A(0x04,"a"),
	B(0x05,"b"),
	C(0x06,"c"),
	D(0x07,"d"),
	E(0x08,"e"),
	F(0x09,"f"),
	G(0x0A,"g"),
	H(0x0B,"h"),
	I(0x0C,"i"),
	J(0x0D,"j"),
	K(0x0E,"k"),
	L(0x0F,"l"),
	M(0x10,"m"),
	N(0x11,"n"),
	O(0x12,"o"),
	P(0x13,"p"),
	Q(0x14,"q"),
	R(0x15,"r"),
	S(0x16,"s"),
	T(0x17,"t"),
	U(0x18,"u"),
	V(0x19,"v"),
	W(0x1A,"w"),
	X(0x1B,"x"),
	Y(0x1C,"y"),
	Z(0x1D,"z"),
	KB_1(0x1E,"1"), // and !
	KB_2(0x1F,"2"), // and @
	KB_3(0x20,"3"), // and #
	KB_4(0x21,"4"), // and $
	KB_5(0x22,"5"), // and %
	KB_6(0x23,"6"), // and ^
	KB_7(0x24,"7"), // and &
	KB_8(0x25,"8"), // and *
	KB_9(0x26,"9"), // and (
	KB_0(0x27,"0"), // and )
	KB_ENTER(0x28,"Return"), // or Keyboard Enter; Keypad Enter is 0x58
	KB_ESCAPE(0x29,"Escape"),
	KB_BACKSPACE(0x2A,"Backspace"), // officially called "Delete" but it' backspace
	KB_TAB(0x2B,"Tab"),
	KB_SPACE(0x2C,"Spacebar"), // or Space Barn
	KB_DASH(0x2D,"-"), // and _
	KB_EQUAL(0x2E,"="), // and +
	KB_OPEN_BRACKET(0x2F,"["), // and {
	KB_CLOSE_BRACKET(0x30,"]"), // and }
	KB_BACKSLASH(0x31,"\\"), // and |
	KB_OCTOTHORPE(0x32,"#"), // Non-US # and ~
	KB_SEMICOLON(0x33,";"), // and :
	KB_OPENQUOTE(0x34,"\u2019"), // and open double-quote
	KB_BACKQUOTE(0x35,"`"), // and ~
	KB_COMMA(0x36,","), // and <
	KB_DOT(0x37,"."), // and >
	KB_SLASH(0x38,"/"), // and ?
	KB_CAPSLOCK(0x39,"Caps Lock"), // Use the flag instead
	KB_F1(0x3A,"F1"),
	KB_F12(0x45,"F12"),
	KB_PRTSCR(0x46,"Print Screen"),
	KB_SCROLLLOCK(0x47,"Scroll Lock"), // Use the flag instead
	KB_PAUSE(0x48,"Pause"), // and Break
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
	KP_DIVIDE(0x54,"Divide"), // actually /
	KP_MULTIPLY(0x55,"Multiply"), // actually *
	KP_MINUS(0x56,"Minus"), // actually -
	KP_ADD(0x57,"Add"), // actually +
	KP_ENTER(0x58,"Enter"), // Keyboard enter is 0x28
	KP_1(0x59,"Keypad 1"), // and End
	KP_2(0x5A,"Keypad 2"), // and Down Arrow
	KP_3(0x5B,"Keypad 3"), // and Page Down
	KP_4(0x5C,"Keypad 4"), // and Left Arrow
	KP_5(0x5D,"Keypad 5"), //
	KP_6(0x5E,"Keypad 6"), // and Right Arrow
	KP_7(0x5F,"Keypad 7"), // and Home
	KP_8(0x60,"Keypad 8"), // and Up Arrow
	KP_9(0x61,"Keypad 9"), // and Page Up
	KP_0(0x62,"Keypad 0"), // and Insert
	KP_DOT(0x63,"Keypad ."), // and Delete
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

	private byte code;
    private String name;

	KybardCodes(int code, String name)
	{
		this.code=(byte)(code&0xff);
        this.name=name;
	}

	public byte getCode()
	{
		return this.code;
	}

    public String getName()
    {
        return this.name;
    }
}
