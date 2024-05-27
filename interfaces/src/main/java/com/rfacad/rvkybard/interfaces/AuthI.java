package com.rfacad.rvkybard.interfaces;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public static final String SAMENEWPINNAME = "samenewpin";

    /**
     * Checks to see if the cookie exists and is a currently logged-in session
     * @param cookies
     * @return AuthToken that matches the cookie's nonce, OR AuthToken.NO_TOKEN (which is an expired token)
     */
    AuthTokenI checkForValidCookie(Cookie [] cookies);

    /**
     * Checks to see if the cookie exists in the Request, and is a currently logged-in session.
     * If the token will be expiring soon, then create a new "refresh" token, and add it as
     * a new Cookie to the Response.
     *
     * @param cookies
     * @return AuthToken that matches the cookie's nonce, OR AuthToken.NO_TOKEN (which is an expired token)
     */
    AuthTokenI checkForValidCookie(HttpServletRequest request,HttpServletResponse response);

    /**
     * Attempts to log in, checking pin against stored valid pins
     * @param pin
     * @return null or a new token
     */
    AuthTokenI login(String pin);

    /**
     * Add a new valid PIN to the pin DB
     * @param pin
     */
    void writePin(String pin);

    /**
     * Expires token immediately, removing it from the list of logged-in tokens
     * @param token
     */
    void logout(AuthTokenI token);

    /**
     * @return the relative URL of the login page
     */
    String getLoginPageUrl();

}
