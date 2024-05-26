package com.rfacad.rvkybard.auth;

import static org.junit.Assert.*;

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
public class TokenDbTest
{
    private static final String N1 = "nonce one";
    private static final String N2 = "nonce two";

    @Test
    public void shouldStoreToken()
    {
        TokenDb db=new TokenDb();
        AuthToken tok1 = new AuthToken(N1,AuthToken.DEFAULT_LIFESPAN_MILLIS);
        db.storeToken(tok1);
        AuthTokenI r1 = db.getToken(N1);
        assertEquals(tok1,r1);
        db.removeToken(N1);
        AuthTokenI r2 = db.getToken(N1);
        assertNull(r2);
    }

    @Test
    public void shouldRemoveExpiredTokensOnInsert()
    {
        TokenDb db=new TokenDb();
        AuthToken tok1 = new AuthToken(N1,10000);
        db.storeToken(tok1);
        tok1.setExpiration(0); // mark it expired
        AuthTokenI r1 = db.getToken(N1);
        assertEquals(tok1,r1);
        assertFalse(tok1.isOK());
        AuthToken tok2 = new AuthToken(N2,AuthToken.DEFAULT_LIFESPAN_MILLIS);
        db.storeToken(tok2);
        AuthTokenI r2 = db.getToken(N2);
        assertEquals(tok2,r2);
        AuthTokenI r3 = db.getToken(N1);
        assertNull(r3);
    }
}
