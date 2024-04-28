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

public enum MouseMode
{
    TOUCH("ontouchstart","ontouchend"),
    MOUSE("onmousedown","onmouseup"),
    CLICK("onmousedown","onclick");

    private String down;
    private String up;

    MouseMode(String down, String up)
    {
        this.down=down;
        this.up=up;
    }

    public String getDown()
    {
        return down;
    }

    public String getUp()
    {
        return up;
    }
}
