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

public class Keyboard
{
    private List<Protocol> protocols;
    private String name;
    private String description;
    private String snap;

    protected Keyboard()
    {
        protocols=new ArrayList<>();
        name=null;
        description=null;
        snap=null;
    }

    protected static Keyboard load(String parent, File topdir)
    {
        Keyboard ret = new Keyboard();
        String fn = parent+"/"+topdir.getName();
        File [] dirs = topdir.listFiles();
        // each dir COULD be a theme. Or not!
        for(int i=0;i<dirs.length;i++)
        {
            try
            {
                File f = dirs[i];
                if ( f.isDirectory() )
                {
                    Protocol child = Protocol.load(fn,dirs[i]);
                    if ( child != null )
                    {
                        ret.add(child);
                    }
                }
                else
                {
                    // The expected files are: name.txt desc.html snapshot.png/jpg
                    String n = f.getName();
                    if ("name.txt".equals(n))
                    {
                        ret.setName(FileUtils.readFileToString(f, Charset.defaultCharset()));
                    }
                    if ("desc.html".equals(n))
                    {
                        ret.setDescription(FileUtils.readFileToString(f, Charset.defaultCharset()));
                    }
                    if (n.startsWith("snapshot."))
                    {
                        ret.setSnap(fn+"/"+n);
                    }
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        if ( ret.getProtocols().isEmpty() )
        {
            return null;
        }
        return ret;
    }

    protected void add(Protocol p)
    {
        protocols.add(p);
    }

    public List<Protocol> getProtocols()
    {
        return protocols;
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

    public void setDescription(String description)
    {
        this.description=description;
    }
    public String getDescription()
    {
        return description;
    }

    public void setSnap(String fn)
    {
        this.snap=fn;
    }
    public String getSnapshotOrBlank()
    {
        if ( snap == null )
        {
            return "";
        }
        return "<img src='/kb/"+snap+"'/>";
    }
}
