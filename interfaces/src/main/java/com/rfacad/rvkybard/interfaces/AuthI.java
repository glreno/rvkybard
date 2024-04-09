package com.rfacad.rvkybard.interfaces;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//

public interface AuthI
{

    public static final String COOKIENAME = "rvbiscuit";

    public static final String PINNAME = "pin";

    /**
     * Checks to see if this cookie is a currently logged-in session
     * @param cookie
     * @return true if cookie is valid.
     */
    boolean isCookieValid(String cookie);

    /**
     * Attempts to log in, checking pin against stored valid pins
     * @param pin
     * @return null or a new cookie
     */
    String login(String pin);

    /**
     * removes cookie from the list of logged-in cookies
     * @param cookie
     */
    void logout(String cookie);
}
