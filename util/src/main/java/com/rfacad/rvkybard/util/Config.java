package com.rfacad.rvkybard.util;

import com.rfacad.rvkybard.interfaces.MouseMode;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//

public class Config
{
    private static Config SINGLETON;

    public static Config getConfig()
    {
        return SINGLETON;
    }

    public static void setConfig(Config c)
    {
        SINGLETON=c;
    }

    private MouseMode mouseMode = MouseMode.TOUCH;

    public void setConfigFile(String fn)
    {
    }

    public void init()
    {
        // Read the config file
        // initialize singleton
        setConfig(this);
    }

    public MouseMode getMouseMode()
    {
        return mouseMode;
    }

    public void setMouseMode(MouseMode mouseMode)
    {
        this.mouseMode=mouseMode;
    }
}
