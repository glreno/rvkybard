package com.rfacad.rvkybard.index;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//

public class Theme
{
    private List<Keyboard> keyboards;
    private String name;

    protected Theme()
    {
        keyboards=new ArrayList<>();
        name = null;
    }

    protected static Theme load(File topdir)
    {
        Theme ret = new Theme();
        String fn = topdir.getName();

        File [] dirs = topdir.listFiles();
        // each dir COULD be a theme. Or not!
        for(int i=0;i<dirs.length;i++)
        {
            try
            {
                File f = dirs[i];
                if ( f.isDirectory() )
                {
                    Keyboard child = Keyboard.load(fn,f);
                    if ( child != null )
                    {
                        ret.add(child);
                    }
                }
                else
                {
                    // The expected files are: name.txt
                    if ("name.txt".equals(f.getName()))
                    {
                        ret.setName(FileUtils.readFileToString(f, Charset.defaultCharset()));
                    }
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        if ( ret.getKeyboards().isEmpty() )
        {
            return null;
        }
        return ret;
    }

    protected void add(Keyboard k)
    {
        keyboards.add(k);
    }

    public List<Keyboard> getKeyboards()
    {
        return keyboards;
    }

    public void setName(String name)
    {
        if ( name != null )
        {
            this.name = name.trim();
        }
    }
    public String getName()
    {
        return name;
    }
}
