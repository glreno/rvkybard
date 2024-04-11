package com.rfacad.rvkybard.auth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

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
        // We're just going to read this in to memory, it only contains a single pin.
        // Encryption? Multiple users? Who, me?
        try (BufferedReader in = new BufferedReader(new FileReader(fn)))
        {
            pindb = in.readLine();
        }
        catch(IOException e)
        {
            LOG.error("Could not load pin db {}",fn,e);
        }
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
