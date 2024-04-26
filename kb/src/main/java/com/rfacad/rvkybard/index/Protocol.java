package com.rfacad.rvkybard.index;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

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

public class Protocol
{
    private String name;
    private String link;

    protected Protocol(String link)
    {
        this.link=link;
        this.name="generic";
    }

    protected static Protocol load(String parent,File topdir)
    {
        Protocol ret = new Protocol(parent+"/"+topdir.getName()+"/kb.jsp");

        boolean jspFound = false;
        File [] dirs = topdir.listFiles();
        // each dir COULD be a theme. Or not!
        for(int i=0;i<dirs.length;i++)
        {
            try
            {
                File f = dirs[i];
                if ( ! f.isDirectory() )
                {
                    // The expected files are: kb.jsp protocol.txt
                    if ("protocol.txt".equals(f.getName()))
                    {
                        ret.setName(FileUtils.readFileToString(f, Charset.defaultCharset()));
                    }
                    if ("kb.jsp".equals(f.getName()))
                    {
                        jspFound=true;
                    }
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        if ( ! jspFound )
        {
            return null;
        }
        return ret;
    }

    public String getLink()
    {
        return link;
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
