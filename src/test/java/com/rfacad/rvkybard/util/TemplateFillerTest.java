package com.rfacad.rvkybard.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
public class TemplateFillerTest
{
    File tmpdir;
    @Before
    public void setup() throws IOException
    {
        tmpdir = File.createTempFile("kybardUnitTest","dir");
        tmpdir.delete();
        tmpdir.mkdir();
    }

    @After
    public void cleanup()
    {
        File [] files = tmpdir.listFiles();
        if ( files != null )
        {
            for(File f:files)
            {
                f.delete();
            }
        }
        tmpdir.delete();
    }

    @Test
    public void shouldReadParams() throws IOException
    {
        TemplateProcessor tf=new TemplateFiller(tmpdir.toString(),"src.txt");
        Map<String,String> ret;

        ret = tf.parseParams(new String[] {});
        assertEquals(0,ret.size());

        ret = tf.parseParams(new String[] {"foo","bar","x=1","y=2"});
        assertEquals(2,ret.size());
        assertEquals("1",ret.get("x"));
        assertEquals("2",ret.get("y"));

        ret = tf.parseParams(new String[] {"foo="});
        assertEquals(1,ret.size());
        assertEquals("",ret.get("foo"));
    }

    private String processLine(TemplateProcessor tf,String line,Map<String,String> params) throws IOException
    {
        StringWriter out = new StringWriter();
        InputStream in = new ByteArrayInputStream(line.getBytes(Charset.defaultCharset()));
        tf.processStream("x",in, out, params);
        return out.toString();
    }
    @Test
    public void shouldReplaceParams() throws IOException
    {
        TemplateProcessor tf=new TemplateFiller(tmpdir.toString(),"src.txt");
        String ret;
        tf.loadDefault("a", "alice");
        tf.loadDefaults(new String[]{"b=bob","quote=&#x2019;"});
        Map<String, String> params = tf.parseParams(new String[] {"foo","bar","x=1","y=2","a=override"});

        ret=processLine(tf,"",params);
        assertEquals("",ret);

        ret=processLine(tf,"No params here",params);
        assertEquals("No params here",ret);

        ret=processLine(tf,"Stray open @{ brace",params);
        assertTrue(ret.contains("Error: unbalanced braces"));

        ret=processLine(tf,"Stray close } brace",params);
        assertEquals("Stray close } brace",ret);

        ret=processLine(tf,"This param @{p} does not exist.",params);
        assertEquals("This param  does not exist.",ret);

        ret=processLine(tf,"@{b}",params);
        assertEquals("bob",ret);

        ret=processLine(tf,"This line ends with @{a}",params);
        assertEquals("This line ends with override",ret);

        ret=processLine(tf,"@{x} starts this line.",params);
        assertEquals("1 starts this line.",ret);

        ret=processLine(tf,"@{x}+@{x}=@{y}",params); // TODO that should be @{x+x} instead of @{y}
        assertEquals("1+1=2",ret);

        ret=processLine(tf,"@{quote}@{b}@{quote}",params);
        assertEquals("&#x2019;bob&#x2019;",ret);
    }

    @Test
    public void shouldGenerateFile() throws IOException
    {
        // Create a script
        File src=new File(tmpdir,"src.txt");
        File template=new File(tmpdir,"templatefile.txt");
        try( PrintWriter out=new PrintWriter(new FileWriter(src)) )
        {
            out.println("// This is a comment");
            out.println("noCommaHere");
            out.println("DEFAULTS,x=1,y=2 // These are default values");
            out.println("outfile.txt,templatefile.txt,z=3,//a=nothing");
        }
        try( PrintWriter out=new PrintWriter(new FileWriter(template)) )
        {
            out.println("This is a template");
            out.println("It uses the values @{y} and @{a} and @{z}");
        }
        TemplateFiller tf=new TemplateFiller(tmpdir.toString(),tmpdir.toString());
        tf.runScript("src.txt");

        File dest=new File(tmpdir,"outfile.txt");
        assertTrue(dest.exists());
        try( BufferedReader in=new BufferedReader(new FileReader(dest)) )
        {
            String s;
            s=in.readLine();
            assertEquals("This is a template",s);
            s=in.readLine();
            assertEquals("It uses the values 2 and  and 3",s);
            s=in.readLine();
            assertNull(s);
        }
    }
}
