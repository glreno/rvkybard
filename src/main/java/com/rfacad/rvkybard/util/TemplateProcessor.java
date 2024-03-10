package com.rfacad.rvkybard.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.mvel2.CompileException;
import org.mvel2.templates.TemplateRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
public class TemplateProcessor
{
    private static Logger LOG = LoggerFactory.getLogger(TemplateProcessor.class);

    protected Map<String,String> defaultParams;

    public TemplateProcessor()
    {
        defaultParams=new HashMap<>();
    }

    public void loadDefault(String key,String value)
    {
        defaultParams.put(key, value);
    }
    public void loadDefaults(String [] descArray)
    {
        defaultParams.putAll(parseParams(descArray));
    }

    public Map<String,String> parseParams(String[] descArray)
    {
        Map<String,String> ret = new HashMap<>();
        for(String s : descArray)
        {
            int eq=s.indexOf("=");
            if ( eq > 0 )
            {
                ret.put(s.substring(0,eq), s.substring(eq+1));
            }
        }
        return ret;
    }

    public void processStream(String fn,InputStream in, Writer out, Map<String,String> params) throws IOException
    {
        // Combine parameters
        Map<String,String> allparams=new ParamMap(defaultParams,params);

        String output;
        try
        {
            // Process the expressions
            output = (String) TemplateRuntime.eval(in, allparams);
        }
        catch (CompileException e)
        {
            LOG.error("Exception compiling expression in {}",fn,e);
            output = e.getMessage();
        }
        // Write the buffer out
        out.write(output);
    }

}