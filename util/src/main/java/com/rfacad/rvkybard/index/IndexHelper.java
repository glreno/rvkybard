package com.rfacad.rvkybard.index;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//

public class IndexHelper
{
    protected List<Theme> themes;

    public IndexHelper(String topdir)
    {
        themes = new ArrayList<>();
        File f = new File(topdir);
        load(f);
    }

    protected void load(File topdir)
    {
        File [] dirs = topdir.listFiles();
        // each dir COULD be a theme. Or not!
        for(int i=0;i<dirs.length;i++)
        {
            File f = dirs[i];
            if ( f.isDirectory() )
            {
                Theme child = Theme.load(f);
                if ( child != null )
                {
                    add(child);
                }
            }
        }
    }

    protected void add(Theme t)
    {
        themes.add(t);
    }

    // The kb directory is organized into:
    // Theme - the look & feel (Art Deco, Atari, ZX81, etc) - this is where most of the key svgt files are
    // Keyboard - the layout of the keyboard (numeric keypad, tenkeyless, etc) - this is where name.txt & desc.html & snapshot.png are, could be some svgt files too
    // Protocol - which hardware or emulator the keyboard is for - this is where kb.jsp is
    // The directory structure is: kb/theme/keyboard/protocol
    // The IndexHelper contains a list of Theme which contains list of Keyboard which contains list of Protocol

    public List<Theme> getThemes()
    {
        return themes;
    }
}
