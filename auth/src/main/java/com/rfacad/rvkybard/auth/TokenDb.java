package com.rfacad.rvkybard.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
public class TokenDb
{
    protected Map<String,AuthTokenI> store = new ConcurrentHashMap<>();

    public AuthTokenI getToken(String nonce)
    {
        if ( nonce == null )
        {
            return null;
        }
        return store.get(nonce);
    }

    public void storeToken(AuthTokenI token)
    {
        store.put(token.getNonce(), token);
        // cleanup expired tokens
        List<String> todelete = new ArrayList<>(0);
        for(AuthTokenI e : store.values() )
        {
            if ( ! e.isOK() )
            {
                todelete.add(e.getNonce());
            }
        }
        for(String n : todelete)
        {
            store.remove(n);
        }
    }

    public void removeToken(String nonce)
    {
        store.remove(nonce);
    }

    public void clear()
    {
        store.clear();
    }
}
