package com.rfacad.rvkybard.sender;

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
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.rfacad.rvkybard.interfaces.AuthI;
import com.rfacad.rvkybard.interfaces.AuthTokenI;
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
@Configurable
public class KybardPressServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String[] EMPTY = new String[0];

    private static Logger LOG = LoggerFactory.getLogger(KybardPressServlet.class);

    @Autowired
    protected KybardSender kybardSender;

    @Autowired
    protected KybardReader kybardReader;

    @Autowired
    private AuthI authi;

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public void setKybardSender(KybardSender kybardSender)
    {
        this.kybardSender = kybardSender;
    }

    public void setKybardReader(KybardReader kybardReader)
    {
        this.kybardReader = kybardReader;
    }

    public void setAuthI(AuthI authi)
    {
        this.authi = authi;
    }

    @Override
    public void destroy() 
    {
        LOG.info("Shutting down");
        kybardSender.shutdown();
        LOG.info("Shutdown complete");
    }

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        AuthTokenI tok = authi.checkForValidCookie(req,resp);
        if ( ! tok.isOK() )
        {
            LOG.debug("Expired token -- lifespan = {}",tok.getLifespan() );
            resp.sendError(401);
            return;
        }
        String q = req.getQueryString();
        byte flags = parseFlags(q);
        byte [] keys = parseKeys(q);
        try
        {
            kybardSender.sendKeys(flags,keys);
        }
        catch(IOException e)
        {
            // probably lost the USB connection!
            // Inform the kybardReader, it's probably still sitting around waiting
            kybardReader.setLastRead(-2);
            LOG.debug("Failed to send key: {}",e.getMessage());
        }
        // Read the state of the keyboard LEDs, send them as json list
        String leds = kybardReader.getCurrentFlags();
        PrintWriter out = resp.getWriter();
        out.println(leds);
        LOG.debug("LED status: {}",leds);
    }

    protected byte parseFlags(String queryString)
    {
        // Find the k= parameter
        String [] keys=findQueryParam(queryString,"f");
        byte ret = 0;
        for(String k : keys)
        {
            if ( ! k.isEmpty() )
            {
                try
                {
                    KybardFlag kf = KybardFlag.valueOf(k);
                    ret |= kf.getBits();
                }
                catch (IllegalArgumentException e)
                {
                    LOG.warn("Invalid modifier key '{}'",k);
                    // any not-found keys will be zeroes at the end of the array,
                    // which will be dutifully sent, and ignore by the keyboard driver.
                }
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
            if ( ! k.isEmpty() )
            {
                try
                {
                    KybardCode kc = KybardCode.lookup(k);
                    ret[i++]=kc.getCode();
                }
                catch (IllegalArgumentException e)
                {
                    LOG.warn("Invalid key '{}'",k);
                    // any not-found keys will be zeroes at the end of the array,
                    // which will be dutifully sent, and ignore by the keyboard driver.
                }
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
