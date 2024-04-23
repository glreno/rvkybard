package com.rfacad.rvkybard.auth;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.rfacad.rvkybard.interfaces.AuthI;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//

@Configurable
public class AuthServlet extends HttpServlet
{
    private static final long serialVersionUID = 6831926817846586442L;

    Logger LOG = LoggerFactory.getLogger(AuthServlet.class);

    @Autowired
    private AuthI authi;

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public void setAuthi(AuthI authi)
    {
        this.authi = authi;
    }

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        // ANY get is a log out. That'll show 'em.
        // I mean, really it should just log out the provided cookie.
        // But since the goal of all this is to maintain only a SINGLE active user,
        // I can just log out everybody.
        LOG.debug("Logging out!");
        authi.logout("");
    }

    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String pin = req.getParameter(AuthI.PINNAME);
        LOG.trace("Attempting login "+pin);
        if ( authi == null )
        {
            LOG.error("Login attempt before Auth started");
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        else
        {
            String s = authi.login(pin);
            if ( s != null )
            {
                LOG.debug("Successful login");
                String u = req.getParameter(AuthI.UPDATEPINNAME);
                if ( u != null && ! u.trim().isEmpty() )
                {
                    LOG.warn("Updating PIN db");
                    authi.writePin(u.trim());
                }
                Cookie cookie = new Cookie(AuthI.COOKIENAME,s);
                int seconds = 60*60; // an hour
                cookie.setMaxAge(seconds);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                resp.addCookie(cookie);
                resp.sendRedirect("/");
                return;
            }
        }
        LOG.error("Unsuccessful login attempt");
        LOG.trace("Unsuccessful login with pin "+pin);
        resp.sendRedirect(authi.getLoginPageUrl());
    }
}