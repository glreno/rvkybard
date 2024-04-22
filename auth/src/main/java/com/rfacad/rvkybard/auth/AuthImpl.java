package com.rfacad.rvkybard.auth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Random;

import javax.servlet.http.Cookie;

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

public class AuthImpl implements AuthI
{
    Logger LOG = LoggerFactory.getLogger(AuthImpl.class);

    private String pindb = null;
    private String cookiedb = null;
    private String loginPageUrl = "/login.jsp";
    private Random randy = new SecureRandom();

    public void setPinFile(String fn)
    {
        loadPinFile(fn,true);
    }
    protected void loadPinFile(String fn, boolean allowRetry)
    {
        // We're just going to read this in to memory, it only contains a single pin.
        // Encryption? Multiple users? Who, me?
        try (BufferedReader in = new BufferedReader(new FileReader(fn)))
        {
            pindb = in.readLine();
        }
        catch(IOException e)
        {
            LOG.error("Could not load pin db {}",fn,e);
            if ( allowRetry )
            {
                LOG.error("Creating new pin db {} with default entry",fn,e);
                writePinFile(fn,"6502");
            }
        }
    }
    protected void writePinFile(String fn,String content)
    {
        File f = new File(fn);
        try (PrintWriter out = new PrintWriter(new FileWriter(fn)))
        {
            out.println(content);
        }
        catch (IOException e)
        {
            LOG.error("Could not create new pin db",e);
        }
        f.setExecutable(false, false); // non-executable, for everybody
        f.setReadable(false,false);     // non-readable, for everybody
        f.setReadable(true, true);      // readable, owner-only
        f.setWritable(false, false);    // non-writable, for everybody
        f.setWritable(true, true);      // writable, owner-only
        loadPinFile(fn,false);
    }

    public void setLoginPageUrl(String loginPageUrl)
    {
        this.loginPageUrl=loginPageUrl;
    }
    @Override
    public String getLoginPageUrl()
    {
        return loginPageUrl;
    }

    public void init()
    {
        LOG.debug("Auth bean starting");
        AuthS.setAuthI(this);
    }

    @Override
    public boolean isCookieValid(String cookie)
    {
        return cookiedb!=null && cookiedb.equals(cookie);
    }

    @Override
    public boolean checkForValidCookie(Cookie [] cookies)
    {
        if ( cookies == null )
        {
            LOG.trace("No cookies!");
            return false;
        }
        for (Cookie c : cookies)
        {
            LOG.trace("Login checking cookie {} = {}",c.getName(),c.getValue());
            if (COOKIENAME.equals(c.getName()))
            {
                return isCookieValid(c.getValue());
            }
        }
        LOG.trace("No matching cookie found in {}",cookies.length);
        return false;
    }


    @Override
    public String login(String pin)
    {
        if ( pindb == null || !pindb.equals(pin) )
        {
            return null;
        }
        LOG.debug("Logged in");
        // OK, it's good. Log them in by generating a cookie
        // NOTE: We only store one cookie, so this WILL log someone else out
        logout(cookiedb);
        String s = Integer.toHexString(randy.nextInt(0xffff));
        cookiedb = s;
        return s;
    }

    @Override
    public void logout(String cookie)
    {
        LOG.debug("Logged out "+cookie);
        cookiedb=null;
    }

}
