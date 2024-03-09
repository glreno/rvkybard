package com.rfacad.rvkybard.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
public class TemplateFiller extends TemplateProcessor
{
    public static void main(String [] args) throws IOException
    {
        new TemplateFiller(args[0],args[1]).runScript(args[2]);
    }

    protected File srcdir;
    protected File destdir;

    public TemplateFiller(String srcdir, String destdir)
    {
        super();
        this.srcdir=new File(srcdir);
        this.destdir=new File(destdir);
        this.defaultParams=new HashMap<>();
    }

    public void runScript(String fn) throws IOException
    {
        File inf=new File(srcdir,fn);
        try(BufferedReader in=new BufferedReader(new FileReader(inf)) )
        {
            String s;
            while( (s=in.readLine()) != null)
            {
                generateAllFiles(s);
            }
        }
    }

    public void generateAllFiles(String desc)
    {
        int comment = desc.indexOf("//");
        if ( comment > -1 )
        {
            desc=desc.substring(0,comment);
        }
        desc=desc.trim();
        String [] descArray = desc.split(",");
        if ( descArray.length < 2 )
        {
            return;
        }
        if ( "DEFAULTS".equals(descArray[0]) )
        {
            loadDefaults(descArray);
            return;
        }
        generate(descArray);
    }

    protected void generate(String [] descArray)
    {
        File dest=new File(destdir,descArray[0]);
        File src=new File(srcdir,descArray[1]);
        Map<String,String> params = parseParams(descArray);
        processFile(src,dest,params);
    }

    protected void processFile(File src,File dest,Map<String,String> params)
    {
        try(BufferedReader in=new BufferedReader(new FileReader(src)))
        {
            try(FileWriter out=new FileWriter(dest))
            {
                processStream(in,out,params);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
