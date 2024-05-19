package com.rfacad.rvkybard.auth;

import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rfacad.rvkybard.interfaces.AuthI;
import com.rfacad.rvkybard.interfaces.AuthS;
import com.rfacad.rvkybard.interfaces.AuthTokenI;

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

    public static final long REFRESH_THRESHOLD = 60*1000; // 1 minute

    Logger LOG = LoggerFactory.getLogger(AuthImpl.class);

    private String pinfn = null;
    private byte[] pindb = null;
    private TokenDb tokendb = new TokenDb();
    private String loginPageUrl = "/login.jsp";
    private Random randy = new SecureRandom();

    public void setPinFile(String fn)
    {
        pinfn=fn;
        File f = new File(pinfn);
        if ( f.exists() )
        {
            loadPinFile();
        }
        else
        {
            LOG.warn("Creating new pin db {} with default entry",pinfn);
            writePin("6502");
        }
    }
    protected void loadPinFile()
    {
        // We're just going to read this in to memory, it only contains a single pin.
        // Multiple users? Who, me?
        try (InputStream in = new FileInputStream(pinfn))
        {
            byte [] b = new byte[1025];
            int n = in.read(b);
            pindb = new byte[n]; // NegativeArraySizeException if nothing was read
            System.arraycopy(b, 0, pindb, 0, n);
        }
        catch(IOException | NegativeArraySizeException e)
        {
            LOG.error("Could not load pin db {}",pinfn,e);
        }
    }

    @Override
    public void writePin(String pin)
    {
        byte [] hash = hash(pin);
        File f = new File(pinfn);
        try (FileOutputStream out = new FileOutputStream(f))
        {
            out.write(hash);
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
        loadPinFile();
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

    AuthTokenI findToken(String nonce)
    {
        AuthTokenI a = tokendb.getToken(nonce);
        if ( a!=null && a.getNonce().equals(nonce) )
        {
            return a;
        }
        // The nonce isn't in the DB. This token is therefore not valid.
        return AuthToken.NO_TOKEN;
    }

    @Override
    public AuthTokenI checkForValidCookie(Cookie [] cookies)
    {
        if ( cookies == null )
        {
            LOG.trace("No cookies!");
            return AuthToken.NO_TOKEN;
        }
        for (Cookie c : cookies)
        {
            LOG.trace("Login checking cookie {} = {}",c.getName(),c.getValue());
            if (COOKIENAME.equals(c.getName()))
            {
                return findToken(c.getValue());
            }
        }
        LOG.trace("No matching cookie found in {}",cookies.length);
        return AuthToken.NO_TOKEN;
    }

    @Override
    public AuthTokenI checkForValidCookie(HttpServletRequest request,HttpServletResponse response)
    {
        return checkForValidCookie(request.getCookies());
    }

    @Override
    public AuthTokenI login(String pin)
    {
        if ( pin == null )
        {
            return null;
        }
        byte[] hash = hash(pin.trim());

        if ( pindb == null || hash == null || ! Arrays.equals(pindb, hash) )
        {
            return null;
        }
        LOG.debug("Logged in");
        // OK, it's good. Log them in by generating a cookie
        // NOTE: We only store one cookie, so this WILL log someone else out
        logout(null);
        String s = Integer.toHexString(randy.nextInt(0xffff));
        AuthTokenI a = new AuthToken(s,AuthTokenI.DEFAULT_LIFESPAN_MILLIS);
        tokendb.storeToken(a);
        return tokendb.getToken(s);
    }

    private byte[] hash(String pin)
    {
        byte[] hash = null;
        try
        {
            String s = pin+pin; // well, it's SORTA salted
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            hash = md.digest(s.getBytes(StandardCharsets.UTF_8));
        }
        catch (NoSuchAlgorithmException e)
        {
            LOG.error("What, me encrypt?",e);
        }
        return hash;
    }

    @Override
    public void logout(AuthTokenI token)
    {
        LOG.debug("Logged out {}",token);
        tokendb.clear();
        // Should be doing this:
        //((AuthToken)token).setExpiration(0);
    }

    // needed for testing
    void expireToken(String nonce)
    {
        AuthTokenI a = findToken(nonce);
        if ( a != null )
        {
            ((AuthToken)a).setExpiration(0);
        }
    }

}
