package com.rfacad.rvkybard.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.mvel2.CompileException;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
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

    protected Map<String,Object> defaultParams;

    public TemplateProcessor()
    {
        defaultParams=new HashMap<>();
    }

    public void loadDefault(String key,Object value)
    {
        defaultParams.put(key, value);
    }
    public void loadDefaults(String [] descArray)
    {
        defaultParams.putAll(parseParams(descArray));
    }

    public Map<String,Object> parseParams(String[] descArray)
    {
        Map<String,Object> ret = new HashMap<>();
        for(String s : descArray)
        {
            int eq=s.indexOf("=");
            if ( eq > 0 )
            {
                ret.put(s.substring(0,eq), parseValue(s.substring(eq+1)));
            }
        }
        return ret;
    }

    public Object parseValue(String s)
    {
        String t = s.trim();
        if ( t.isEmpty() )
        {
            return t;
        }
        // scan for numbers, dots, minus, and anything else
        int nums=0;
        int dots=0;
        int minus=0;
        int plus=0;
        int letters=0;
        char first = t.charAt(0);
        for(char c : t.toCharArray())
        {
            if ( c=='.' ) ++dots;
            else if ( c=='-' ) ++minus;
            else if ( c=='+' ) ++plus;
            else if ( Character.isDigit(c) ) nums++;
            else ++letters;
        }
        if ( letters == 0 && nums > 0 && dots < 2)
        {
            // Could be a number
            if ( minus==0 || (minus==1 && first=='-') )
            {
                // Could be a positive or negative number
                if ( plus==0 || (plus==1 && first=='+') )
                {
                    // Could still be a positive or negative number
                    if ( dots == 0 )
                    {
                        return Long.parseLong(t);
                    }
                    else
                    {
                        return Double.parseDouble(t);
                    }
                }
            }
        }
        return t;
    }

    public void processStream(String fn,InputStream in, Writer out, Map<String,Object> params) throws IOException
    {
        // Combine parameters
        Map<String,Object> allparams=new ParamMap<Object>(defaultParams,params,"");

        String output;
        try
        {
            // Prepare the parser, load custom functions
            // TODO can I just do this once? Or do I need to create a new context for each run?
            ParserContext parserContext = ParserContext.create();
            Method method = MVEL.getStaticMethod(Raster.class, Raster.getFunctionName(), Raster.getFunctionSignature());
            parserContext.addImport("raster",method);
            allparams.put("raster", method);

            // Process the expressions
            output = (String) TemplateRuntime.eval(in, parserContext, allparams);
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