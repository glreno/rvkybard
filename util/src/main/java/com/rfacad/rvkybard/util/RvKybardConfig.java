package com.rfacad.rvkybard.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rfacad.rvkybard.interfaces.AuthConfigI;
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

public class RvKybardConfig implements AuthConfigI
{
    private static Logger LOG = LoggerFactory.getLogger(RvKybardConfig.class);

    private static final String MOUSEMODE = "MouseMode";

    private static final String SINGLELOGINMODE = "SingleLoginMode";

    private static RvKybardConfig SINGLETON;

    public static RvKybardConfig getConfig()
    {
        return SINGLETON;
    }

    public static void setConfig(RvKybardConfig c)
    {
        SINGLETON=c;
    }

    private Properties content = new Properties();

    private File configFile = null;

    public void setConfigFile(String fn)
    {
        configFile = new File(fn);
    }

    public void init()
    {
        // Read the config file
        if ( configFile != null && configFile.canRead() )
        {
            try (FileReader in = new FileReader(configFile))
            {
                content.load(in);
            }
            catch (IOException e)
            {
                LOG.error("Unable to load config file {}",configFile,e);
            }
        }
        // initialize singleton
        setConfig(this);
    }

    public String toString()
    {
        return content.toString();
    }

    public void setValue(String key,String value)
    {
        setValues(Collections.singletonMap(key, new String[]{value}));
    }

    public void setValues(Map<String,String[]> values)
    {
        // update cache
        for(Map.Entry<String, String[]> e : values.entrySet() )
        {
            LOG.debug("Storing setting "+e.getKey()+" = "+e.getValue()[0]);
            content.setProperty(e.getKey(), e.getValue()[0]);
        }
        // rewrite the file
        if ( configFile != null )
        {
            try(FileWriter out = new FileWriter(configFile) )
            {
                content.store(out,"");
            }
            catch (IOException e)
            {
                LOG.error("Unable to save config file {}",configFile,e);
            }
        }
    }

    public String getValue(String key)
    {
        return content.getProperty(key);
    }

    public MouseMode getMouseMode()
    {
        String s = getValue(MOUSEMODE);
        if ( s != null )
        {
            return MouseMode.valueOf(s);
        }
        return MouseMode.TOUCH; // default value
    }

    public void setMouseMode(MouseMode mouseMode)
    {
        setValue(MOUSEMODE,mouseMode.toString());
    }

    @Override
    public boolean isSingleLoginMode()
    {
        String s = getValue(SINGLELOGINMODE);
        if ( s != null && !Boolean.valueOf(s) )
        {
            return false;
        }
        return true;
    }

    @Override
    public void setSingleLoginMode(boolean b)
    {
        setValue(SINGLELOGINMODE,Boolean.toString(b));
    }
}
