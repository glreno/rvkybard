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
    public static final AuthToken NO_TOKEN = new AuthToken("",-1);

    private String nonce;
    private long lifespan;

    public AuthToken(String nonce, long lifespan)
    {
        this.nonce = nonce;
        this.lifespan = lifespan;
    }

    @Override
    public String getNonce()
    {
        return nonce;
    }

    @Override
    public long getLifespan()
    {
        return lifespan; // <=0 means expired
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
