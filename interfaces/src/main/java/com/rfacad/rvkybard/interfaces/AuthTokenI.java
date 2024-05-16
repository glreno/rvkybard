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

public interface AuthTokenI
{
    /**
     * Cookies should be storing nonsense, not something useful.
     * This value is the key used in the token DB.
     * @return a nonce value to store in the Cookie
     */
    String getNonce();

    /**
     * @return true if the token is not expired
     */
    default boolean isOK() { return getLifespan() > 0; }

    /**
     * @return number of seconds remaining for this token
     */
    long getLifespan();

    /**
     * Generate a cookie for this token
     * @return a token containing the nonce value
     */
    Cookie makeCookie();
}
