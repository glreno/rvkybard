package com.rfacad.rvkybard.config;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Test;

import com.rfacad.rvkybard.interfaces.AuthI;
import com.rfacad.rvkybard.interfaces.MouseMode;
import com.rfacad.rvkybard.util.RvKybardConfig;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//

public class ConfigServletTest
{
    @After
    public void cleanup()
    {
        RvKybardConfig.setConfig(null);
    }

    @Test
    public void shouldGetConfigFile() throws ServletException, IOException
    {
        AuthI mockAuth = mock(AuthI.class);
        doReturn(true).when(mockAuth).checkForValidCookie(any());

        RvKybardConfig cfg=new RvKybardConfig();
        cfg.init();
        cfg.setValue("foo", "bar");

        ConfigServlet cs = new ConfigServlet();
        cs.init();
        cs.setAuthI(mockAuth);

        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        StringWriter stringWriter = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(stringWriter));
        cs.doGet(req,resp);

        assertEquals("{foo=bar}\n",stringWriter.toString());
    }

    @Test
    public void shouldGetSingleProperty() throws ServletException, IOException
    {
        AuthI mockAuth = mock(AuthI.class);
        doReturn(true).when(mockAuth).checkForValidCookie(any());

        RvKybardConfig cfg=new RvKybardConfig();
        cfg.init();
        cfg.setValue("foo", "bar");

        ConfigServlet cs = new ConfigServlet();
        cs.init();
        cs.setAuthI(mockAuth);

        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getQueryString()).thenReturn("foo");
        StringWriter stringWriter = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(stringWriter));
        cs.doGet(req,resp);

        assertEquals("bar\n",stringWriter.toString());
    }

    @Test
    public void shouldUpdateConfig() throws ServletException, IOException
    {
        AuthI mockAuth = mock(AuthI.class);
        doReturn(true).when(mockAuth).checkForValidCookie(any());

        RvKybardConfig cfg=new RvKybardConfig();
        cfg.init();
        assertEquals(MouseMode.TOUCH,cfg.getMouseMode());

        ConfigServlet cs = new ConfigServlet();
        cs.init();
        cs.setAuthI(mockAuth);

        Vector<String> keys=new Vector(Arrays.asList("foo","MouseMode"));
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        StringWriter stringWriter = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(stringWriter));
        when(req.getParameterNames()).thenReturn(keys.elements());
        when(req.getParameter("foo")).thenReturn("bar");
        when(req.getParameter("MouseMode")).thenReturn("MOUSE");

        cs.doPost(req,resp);

        assertEquals("bar",cfg.getValue("foo"));
        assertEquals(MouseMode.MOUSE,cfg.getMouseMode());
    }
}
