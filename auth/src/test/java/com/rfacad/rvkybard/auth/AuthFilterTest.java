package com.rfacad.rvkybard.auth;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.rfacad.rvkybard.interfaces.AuthI;
import com.rfacad.rvkybard.interfaces.AuthS;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//

public class AuthFilterTest
{
    private static final String LOGINPAGE = "/loginPage";
    private static final String CC = "chocolatechip";
    private static final Cookie GOODCOOKIE = new Cookie(AuthI.COOKIENAME, CC);
    private static final Cookie BADCOOKIE = new Cookie(AuthI.COOKIENAME, "oatmealraisin");
    private AuthImpl mockAuthi;
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private ArgumentCaptor<Integer> statusCaptor;
    private ArgumentCaptor<String> redirectCaptor;
    private FilterChain chain;

    @After
    public void cleanup()
    {
        AuthS.setAuthI(null);
    }

    @Before
    public void setup() throws IOException
    {
        mockAuthi = spy(AuthImpl.class);
        AuthS.setAuthI(mockAuthi);
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        redirectCaptor = ArgumentCaptor.forClass(String.class);
        statusCaptor = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(resp).sendError(statusCaptor.capture());
        doNothing().when(resp).sendRedirect(redirectCaptor.capture());
        // There is one good cookie. All else return false.
        AuthToken goodToken = new AuthToken(CC,1L);
        doReturn(goodToken).when(mockAuthi).findToken(CC);
        doReturn(LOGINPAGE).when(mockAuthi).getLoginPageUrl();
    }

    @Test
    public void shouldRejectNoAuthImpl() throws ServletException, IOException
    {
        AuthFilter af = new AuthFilter();
        af.init(null);
        AuthS.setAuthI(null);
        af.doFilter(req, resp, chain);
        assertEquals(500,statusCaptor.getValue().intValue());
        assertEquals(0,redirectCaptor.getAllValues().size());
        verify(chain,times(0)).doFilter(req, resp);
        af.destroy();
    }

    @Test
    public void shouldRejectNullCookies() throws ServletException, IOException
    {
        AuthFilter af = new AuthFilter();
        af.init(null);
        af.doFilter(req, resp, chain);
        assertEquals(LOGINPAGE,redirectCaptor.getValue());
        assertEquals(0,statusCaptor.getAllValues().size());
        verify(chain,times(0)).doFilter(req, resp);
        af.destroy();
    }

    @Test
    public void shouldRejectNoCookies() throws ServletException, IOException
    {
        doReturn(new Cookie[0]).when(req).getCookies();
        AuthFilter af = new AuthFilter();
        af.init(null);
        af.doFilter(req, resp, chain);
        assertEquals(LOGINPAGE,redirectCaptor.getValue());
        assertEquals(0,statusCaptor.getAllValues().size());
        verify(chain,times(0)).doFilter(req, resp);
        af.destroy();
    }

    @Test
    public void shouldRejectBadCookies() throws ServletException, IOException
    {
        doReturn(new Cookie[] {BADCOOKIE}).when(req).getCookies();
        AuthFilter af = new AuthFilter();
        af.init(null);
        af.doFilter(req, resp, chain);
        assertEquals(LOGINPAGE,redirectCaptor.getValue());
        assertEquals(0,statusCaptor.getAllValues().size());
        verify(chain,times(0)).doFilter(req, resp);
        af.destroy();
    }

    @Test
    public void shouldIgnoreUnrelatedCookies() throws ServletException, IOException
    {
        doReturn(new Cookie[] {new Cookie("foo","bar"), new Cookie("baz","quux")}).when(req).getCookies();
        AuthFilter af = new AuthFilter();
        af.init(null);
        af.doFilter(req, resp, chain);
        assertEquals(LOGINPAGE,redirectCaptor.getValue());
        assertEquals(0,statusCaptor.getAllValues().size());
        verify(chain,times(0)).doFilter(req, resp);
        af.destroy();
    }

    @Test
    public void shouldAcceptGoodCookies() throws ServletException, IOException
    {
        doReturn(new Cookie[] {GOODCOOKIE}).when(req).getCookies();
        AuthFilter af = new AuthFilter();
        af.init(null);
        af.doFilter(req, resp, chain);
        assertEquals(0,redirectCaptor.getAllValues().size());
        assertEquals(0,statusCaptor.getAllValues().size());
        verify(chain,times(1)).doFilter(req, resp);
        af.destroy();
    }

}
