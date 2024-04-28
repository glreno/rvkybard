package com.rfacad.rvkybard.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static Logger LOG = LoggerFactory.getLogger(Config.class);

    private static final String MOUSEMODE = "MouseMode";

    private static Config SINGLETON;

    public static Config getConfig()
    {
        return SINGLETON;
    }

    public static void setConfig(Config c)
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
        // update cache
        content.setProperty(key, value);
        // rewrite the file
        if ( configFile != null )
        {
            try(FileWriter out = new FileWriter(configFile) )
            {
                content.store(out, value);
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
}
