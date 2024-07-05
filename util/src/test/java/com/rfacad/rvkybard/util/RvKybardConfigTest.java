package com.rfacad.rvkybard.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
public class RvKybardConfigTest
{
    private static final String VAL1 = "val1";
    private static final String KEY1 = "key1";
    private File testfile;

    @Before
    public void setup() throws IOException
    {
        testfile=File.createTempFile("cfgtest", "txt");
        testfile.deleteOnExit();
    }

    @After
    public void cleanup()
    {
        RvKybardConfig.setConfig(null);
        testfile.delete();
    }

    @Test
    public void shouldReadWriteTextParamsToConfigFile()
    {
        RvKybardConfig c = new RvKybardConfig();
        c.setConfigFile(testfile.getAbsolutePath());
        c.init();
        assertEquals(c,RvKybardConfig.getConfig());

        assertNull(c.getValue(KEY1));
        c.setValue(KEY1,VAL1);
        assertEquals(VAL1,c.getValue(KEY1));

        // Create a new config object
        RvKybardConfig.setConfig(null);
        c = new RvKybardConfig();
        assertNull(c.getValue(KEY1));
        c.setConfigFile(testfile.getAbsolutePath());
        c.init();
        assertEquals(c,RvKybardConfig.getConfig());
        assertEquals(VAL1,c.getValue(KEY1));
    }

    @Test
    public void shouldReadWriteMouseModeToConfigFile()
    {
        RvKybardConfig c = new RvKybardConfig();
        c.setConfigFile(testfile.getAbsolutePath());
        c.init();
        assertEquals(c,RvKybardConfig.getConfig());

        // Default value of mousemode is touch
        assertEquals(MouseMode.TOUCH,c.getMouseMode());
        c.setMouseMode(MouseMode.MOUSE);
        assertEquals(MouseMode.MOUSE,c.getMouseMode());

        // Create a new config object
        RvKybardConfig.setConfig(null);
        c = new RvKybardConfig();
        assertEquals(MouseMode.TOUCH,c.getMouseMode());
        c.setConfigFile(testfile.getAbsolutePath());
        c.init();
        assertEquals(c,RvKybardConfig.getConfig());
        assertEquals(MouseMode.MOUSE,c.getMouseMode());
        c.setMouseMode(MouseMode.TOUCH);
        assertEquals(MouseMode.TOUCH,c.getMouseMode());

        // Create a new config object again
        RvKybardConfig.setConfig(null);
        c = new RvKybardConfig();
        assertEquals(MouseMode.TOUCH,c.getMouseMode());
        c.setConfigFile(testfile.getAbsolutePath());
        c.init();
        assertEquals(c,RvKybardConfig.getConfig());
        assertEquals(MouseMode.TOUCH,c.getMouseMode());

    }

    @Test
    public void shouldReadWriteSingleLoginModeToConfigFile()
    {
        RvKybardConfig c = new RvKybardConfig();
        c.setConfigFile(testfile.getAbsolutePath());
        c.init();
        assertEquals(c,RvKybardConfig.getConfig());

        // Default value of single login mode is TRUE
        assertTrue(c.isSingleLoginMode());
        c.setSingleLoginMode(false);
        assertFalse(c.isSingleLoginMode());

        // Create a new config object
        RvKybardConfig.setConfig(null);
        c = new RvKybardConfig();
        assertTrue(c.isSingleLoginMode());
        c.setConfigFile(testfile.getAbsolutePath());
        c.init();
        assertEquals(c,RvKybardConfig.getConfig());
        assertFalse(c.isSingleLoginMode());
        c.setSingleLoginMode(true);
        assertTrue(c.isSingleLoginMode());

        // Create a new config object again
        RvKybardConfig.setConfig(null);
        c = new RvKybardConfig();
        assertTrue(c.isSingleLoginMode());
        c.setConfigFile(testfile.getAbsolutePath());
        c.init();
        assertEquals(c,RvKybardConfig.getConfig());
        assertTrue(c.isSingleLoginMode());

    }

}
