package com.rfacad.rvkybard.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.rfacad.rvkybard.interfaces.AuthI;
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

public class ConfigServlet extends HttpServlet
{

    private static final long serialVersionUID = -5327620974859966704L;

    private static Logger LOG = LoggerFactory.getLogger(ConfigServlet.class);

    @Autowired
    private AuthI authi;

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public void setAuthI(AuthI authi)
    {
        this.authi = authi;
    }

    /** Return all config data */
    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Cookie [] cookies = req.getCookies();
        if ( ! authi.checkForValidCookie(cookies) )
        {
            resp.sendError(401);
            return;
        }
        PrintWriter out = resp.getWriter();
        String q = req.getQueryString();
        if ( q != null && q.trim().length() > 0 )
        {
            String v = RvKybardConfig.getConfig().getValue(q.trim());
            if ( v == null )
            {
                v="";
            }
            out.println(v);
        }
        else
        {
            out.println(RvKybardConfig.getConfig().toString());
        }
    }
    
    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Cookie [] cookies = req.getCookies();
        if ( ! authi.checkForValidCookie(cookies) )
        {
            resp.sendError(401);
            return;
        }

        RvKybardConfig.getConfig().setValues(req.getParameterMap());

        resp.sendRedirect("/");
    }
}
