package com.rfacad.rvkybard.auth;

import javax.servlet.http.Cookie;

import com.rfacad.rvkybard.interfaces.AuthI;
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

public class AuthToken implements AuthTokenI
{
    public static final AuthToken NO_TOKEN = new AuthToken("",0,true);

    private String nonce;
    private long expiration;

    public AuthToken(String nonce, long lifespanMillis)
    {
        this.nonce = nonce;
        this.expiration = lifespanMillis + System.currentTimeMillis();
    }

    public AuthToken(String nonce, long lifespanMillis, boolean absolute)
    {
        this.nonce = nonce;
        this.expiration = lifespanMillis + (absolute?0L:System.currentTimeMillis());
    }

    @Override
    public String getNonce()
    {
        return nonce;
    }

    @Override
    public long getLifespan()
    {
        return expiration-System.currentTimeMillis(); // <=0 means expired
    }

    public void forceExpire()
    {
        expiration=0;
    }

    @Override
    public String toString()
    {
        return nonce+" "+expiration;
    }

    @Override
    public Cookie makeCookie()
    {
        Cookie cookie = new Cookie(AuthI.COOKIENAME,getNonce());
        int seconds = 60*60; // an hour -- NOT the same as the token's expiration, which is probably more like ten minutes
        cookie.setMaxAge(seconds);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }
}
