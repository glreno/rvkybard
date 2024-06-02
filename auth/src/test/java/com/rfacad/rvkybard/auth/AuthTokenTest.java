package com.rfacad.rvkybard.auth;

import static org.junit.Assert.*;

import javax.servlet.http.Cookie;

import org.junit.Test;

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

public class AuthTokenTest
{
    private static final String N1 = "Nonce One";
    private static final String N2 = "Nonce Two";
    private static final String N3 = "Nonce Three";

    @Test
    public void shouldCreateCookie()
    {
        // This token is not expired -- lifespan is 10 minutes
        AuthToken t = new AuthToken(N1, AuthTokenI.DEFAULT_LIFESPAN_MILLIS);
        // Check lifespan IMMEDIATELY!
        assertTrue(t.getLifespan()>9*60*1000);
        assertTrue(t.getLifespan()<=10*60*1000);
        assertTrue(t.isOK());
        assertEquals(N1,t.getNonce());

        Cookie c = t.makeCookie();
        assertEquals(N1,c.getValue());
    }

    @Test
    public void shouldRejectExpiredToken()
    {
        // This token is expired -- lifespan is less than 1
        AuthToken t = new AuthToken(N1, 0);
        assertTrue(t.getLifespan()<1);
        assertFalse(t.isOK());
    }

    @Test
    public void shouldStoreRefreshToken()
    {
        AuthToken t1 = new AuthToken(N1, 0);
        AuthToken t2 = new AuthToken(N2, 0);
        AuthToken t3 = new AuthToken(N3, 0);

        t1.setRefreshTokenIfNull(t2);
        assertEquals(t2,t1.getRefreshToken());
        // Verify that the first refreshtoken wins
        t1.setRefreshTokenIfNull(t3);
        assertEquals(t2,t1.getRefreshToken());
    }
}
