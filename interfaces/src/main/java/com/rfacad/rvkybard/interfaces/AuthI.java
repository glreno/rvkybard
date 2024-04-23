package com.rfacad.rvkybard.interfaces;

import javax.servlet.http.Cookie;

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
    public static final String UPDATEPINNAME = "updatedpin";

    /**
     * Checks to see if this cookie is a currently logged-in session
     * @param cookie
     * @return true if cookie is valid.
     */
    boolean isCookieValid(String cookie);

    /**
     * Checks to see if the cookie exists and is a currently logged-in session
     * @param cookies
     * @return true if cookie is there, and isCookieValid() likes it.
     */
    boolean checkForValidCookie(Cookie [] cookies);

    /**
     * Attempts to log in, checking pin against stored valid pins
     * @param pin
     * @return null or a new cookie
     */
    String login(String pin);

    /**
     * Add a new valid PIN to the pin DB
     * @param pin
     */
    void writePin(String pin);

    /**
     * removes cookie from the list of logged-in cookies
     * @param cookie
     */
    void logout(String cookie);

    /**
     * @return the relative URL of the login page
     */
    String getLoginPageUrl();

}