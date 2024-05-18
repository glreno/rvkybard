package com.rfacad.rvkybard.auth;

import static org.junit.Assert.*;

import javax.servlet.http.Cookie;

import org.junit.Test;

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

    @Test
    public void shouldCreateCookie()
    {
        // This token is not expired -- lifespan is 60 seconds
        AuthToken t = new AuthToken(N1, 60);
        // Check lifespan IMMEDIATELY!
        assertTrue(t.getLifespan()>55);
        assertTrue(t.getLifespan()<61);
        assertTrue(t.isOK());
        assertEquals(N1,t.getNonce());

        Cookie c = t.makeCookie();
        assertEquals(N1,c.getValue());
    }

    @Test
    public void shouldRejectExpiredToken()
    {
        // This token is not expired -- lifespan is 60 seconds
        AuthToken t = new AuthToken(N1, 0);
        assertTrue(t.getLifespan()<1);
        assertFalse(t.isOK());
    }
}
