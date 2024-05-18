package com.rfacad.rvkybard.auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rfacad.rvkybard.interfaces.AuthI;
import com.rfacad.rvkybard.interfaces.AuthS;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
public class AuthFilter implements Filter
{
    Logger LOG = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        // This override is required by tomcat, do not remove
        LOG.debug("init");
    }

    @Override
    public void destroy()
    {
        // This override is required by tomcat, do not remove
        LOG.debug("destroy");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        LOG.debug("Auth attempt starting");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        AuthI authi=AuthS.getAuthI();
        if ( authi == null )
        {
            LOG.error("Auth service not yet started");
            httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        // checkForValidCookie returns number of remaining seconds.
        // 0 or negative indicate an expired cookie.
        // (-1 might mean there is no cookie)
        long life = authi.checkForValidCookie(httpServletRequest,httpServletResponse).getLifespan();
        if ( life > 0 )
        {
            if ( life < 60 )
            {
                LOG.debug("Auth succeeded! Seconds remaining={}",life);
            }
            LOG.debug("Auth succeeded!");
            chain.doFilter(request, response);
        }
        else
        {
            LOG.warn("Auth failed, redirecting to login page");
            // Authentication failed! Redirect to login page
            httpServletResponse.sendRedirect(authi.getLoginPageUrl());
        }
    }
}
