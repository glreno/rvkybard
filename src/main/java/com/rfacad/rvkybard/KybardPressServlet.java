package com.rfacad.rvkybard;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rfacad.rvkybard.interfaces.KybardCode;
import com.rfacad.rvkybard.interfaces.KybardFlag;

//
// Copyright (c) 2024 Gerald Reno, Jr.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//  http://www.apache.org/licenses/LICENSE-2.0
//

public class KybardPressServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String[] EMPTY = new String[0];

    Logger LOG = LoggerFactory.getLogger(KybardPressServlet.class);

    protected KybardSender kybardSender;

    @Override
    public void init(ServletConfig config) throws ServletException
    {
    }

    public void setKybardSender(KybardSender kybardSender)
    {
        this.kybardSender = kybardSender;
    }

    @Override
    public void destroy() 
    {
        kybardSender.shutdown();
    }

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String q = req.getQueryString();
        byte flags = parseFlags(q);
        byte [] keys = parseKeys(q);
        kybardSender.sendKeys(flags,keys);
    }

    protected byte parseFlags(String queryString)
    {
        // Find the k= parameter
        String [] keys=findQueryParam(queryString,"f");
        int n = keys.length;
        byte ret = 0;
        int i=0;
        for(String k : keys)
        {
            try
            {
                KybardFlag kf = KybardFlag.valueOf(k);
                ret |= kf.getBits();
            
            }
            catch (IllegalArgumentException e)
            {
                LOG.info("Invalid modifier key '{0}'",k);
                // any not-found keys will be zeroes at the end of the array,
                // which will be dutifully sent, and ignore by the keyboard driver.
            }
        }
        return ret;
    }

    protected byte [] parseKeys(String queryString)
    {
        // Find the k= parameter
        String [] keys=findQueryParam(queryString,"k");
        int n = keys.length;
        byte[] ret = new byte[n];
        int i=0;
        for(String k : keys)
        {
            try
            {
                KybardCode kc = KybardCode.lookup(k);
                ret[i++]=kc.getCode();
            
            }
            catch (IllegalArgumentException e)
            {
                LOG.info("Invalid key '{0}'",k);
                // any not-found keys will be zeroes at the end of the array,
                // which will be dutifully sent, and ignore by the keyboard driver.
            }
        }
        return ret;
    }

    protected String [] findQueryParam(String q,String key)
    {
        int kstart = q.indexOf(key+"=");
        if ( kstart < 0 )
        {
            return EMPTY;
        }
        int kend = q.indexOf('&',kstart);
        if ( kend == -1 )
        {
            return q.substring(kstart+2).trim().split(",");
        }
        else
        {
            return q.substring(kstart+2,kend).trim().split(",");
        }
    }
}
