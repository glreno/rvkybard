package com.rfacad.rvkybard.auth;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

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

public class AuthServletTest
{
    private static final String LOGINPAGE = "/loginPage";
    private AuthI mockAuthi;
    private HttpServletResponse resp;
    private ArgumentCaptor<Integer> statusCaptor;
    private ArgumentCaptor<String> redirectCaptor;
    private ArgumentCaptor<String> updateCaptor;
    private ArgumentCaptor<Cookie> cookieCaptor;
    private ArgumentCaptor<String> headerNameCaptor;
    private ArgumentCaptor<String> headerValueCaptor;

    @After
    public void cleanup()
    {
        AuthS.setAuthI(null);
    }

    @Before
    public void setup() throws IOException
    {
        mockAuthi = mock(AuthI.class);
        resp = mock(HttpServletResponse.class);
        redirectCaptor = ArgumentCaptor.forClass(String.class);
        updateCaptor = ArgumentCaptor.forClass(String.class);
        statusCaptor = ArgumentCaptor.forClass(Integer.class);
        cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
        headerNameCaptor = ArgumentCaptor.forClass(String.class);
        headerValueCaptor = ArgumentCaptor.forClass(String.class);
        doNothing().when(resp).sendError(statusCaptor.capture());
        doNothing().when(resp).sendRedirect(redirectCaptor.capture());
        doNothing().when(resp).addCookie(cookieCaptor.capture());
        doNothing().when(resp).addHeader(headerNameCaptor.capture(), headerValueCaptor.capture());
        // There is one good pin. Anything else will return null
        AuthToken token = new AuthToken("abcd",AuthTokenI.DEFAULT_LIFESPAN_MILLIS);
        doReturn(token).when(mockAuthi).login("6502");
        doNothing().when(mockAuthi).writePin(updateCaptor.capture());
        doReturn(LOGINPAGE).when(mockAuthi).getLoginPageUrl();
    }

    @Test
    public void shouldRejectNoAuthImpl() throws ServletException, IOException
    {
        AuthServlet as = new AuthServlet();
        as.setAuthi(null);
        as.init(null);
        HttpServletRequest req = mock(HttpServletRequest.class);
        as.doPost(req, resp);
        assertEquals(500,statusCaptor.getValue().intValue());
        assertEquals(0,redirectCaptor.getAllValues().size());
        assertEquals(0,cookieCaptor.getAllValues().size());
        assertEquals(0,updateCaptor.getAllValues().size());
    }

    @Test
    public void shouldRejectNoPin() throws ServletException, IOException
    {
        AuthServlet as = new AuthServlet();
        as.setAuthi(mockAuthi);
        HttpServletRequest req = mock(HttpServletRequest.class);
        as.doPost(req, resp);
        assertEquals(0,statusCaptor.getAllValues().size());
        assertEquals(LOGINPAGE,redirectCaptor.getValue());
        assertEquals(0,cookieCaptor.getAllValues().size());
        assertEquals(0,updateCaptor.getAllValues().size());
    }

    @Test
    public void shouldRejectEmptyPin() throws ServletException, IOException
    {
        AuthServlet as = new AuthServlet();
        as.setAuthi(mockAuthi);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getParameter(AuthI.PINNAME)).thenReturn("");
        as.doPost(req, resp);
        assertEquals(0,statusCaptor.getAllValues().size());
        assertEquals(LOGINPAGE,redirectCaptor.getValue());
        assertEquals(0,cookieCaptor.getAllValues().size());
        assertEquals(0,updateCaptor.getAllValues().size());
    }

    @Test
    public void shouldRejectBadPin() throws ServletException, IOException
    {
        AuthServlet as = new AuthServlet();
        as.setAuthi(mockAuthi);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getParameter(AuthI.PINNAME)).thenReturn("0000");
        as.doPost(req, resp);
        assertEquals(0,statusCaptor.getAllValues().size());
        assertEquals(LOGINPAGE,redirectCaptor.getValue());
        assertEquals(0,cookieCaptor.getAllValues().size());
        assertEquals(0,updateCaptor.getAllValues().size());
    }

    @Test
    public void shouldLogIn() throws ServletException, IOException
    {
        AuthServlet as = new AuthServlet();
        as.setAuthi(mockAuthi);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getParameter(AuthI.PINNAME)).thenReturn("6502");
        as.doPost(req, resp);
        assertEquals(0,statusCaptor.getAllValues().size());
        assertEquals(0,updateCaptor.getAllValues().size());
        assertEquals("/",redirectCaptor.getValue());
        assertEquals(1,cookieCaptor.getAllValues().size());
        Cookie c = cookieCaptor.getValue(); // that's good enough for me
        assertEquals(AuthI.COOKIENAME,c.getName());
        assertNotNull(c.getValue());
    }

    @Test
    public void shouldLogOutAll() throws ServletException, IOException
    {
        AuthServlet as = new AuthServlet();
        as.singlelogin=true; // make this a real setting
        as.setAuthi(mockAuthi);
        AuthTokenI tok = mockAuthi.login("6502");
        Cookie[] cookies = new Cookie[] { tok.makeCookie() };
        when(mockAuthi.checkForValidCookie(cookies)).thenReturn(tok);

        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getCookies()).thenReturn(cookies);

        as.doGet(req, resp);
        assertEquals(0,statusCaptor.getAllValues().size());
        assertEquals(0,updateCaptor.getAllValues().size());
        assertEquals("/loginPage",redirectCaptor.getValue());
        assertEquals(0,cookieCaptor.getAllValues().size());
        verify(mockAuthi,times(1)).logout(null);
    }

    @Test
    public void shouldLogOutOne() throws ServletException, IOException
    {
        AuthServlet as = new AuthServlet();
        as.singlelogin=false; // todo make this a proper setting
        as.setAuthi(mockAuthi);
        AuthTokenI tok = mockAuthi.login("6502");
        Cookie[] cookies = new Cookie[] { tok.makeCookie() };
        when(mockAuthi.checkForValidCookie(cookies)).thenReturn(tok);

        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getCookies()).thenReturn(cookies);

        as.doGet(req, resp);
        assertEquals(0,statusCaptor.getAllValues().size());
        assertEquals(0,updateCaptor.getAllValues().size());
        assertEquals("/loginPage",redirectCaptor.getValue());
        assertEquals(0,cookieCaptor.getAllValues().size());
        verify(mockAuthi,times(1)).logout(tok);
    }

    @Test
    public void shouldChangePin() throws ServletException, IOException
    {
        AuthServlet as = new AuthServlet();
        as.setAuthi(mockAuthi);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getParameter(AuthI.PINNAME)).thenReturn("6502");
        when(req.getParameter(AuthI.UPDATEPINNAME)).thenReturn("8080");
        when(req.getParameter(AuthI.SAMENEWPINNAME)).thenReturn("8080");
        as.doPost(req, resp);
        assertEquals(0,statusCaptor.getAllValues().size());
        assertEquals("/",redirectCaptor.getValue());
        assertEquals(1,cookieCaptor.getAllValues().size());
        Cookie c = cookieCaptor.getValue(); // that's good enough for me
        assertEquals(AuthI.COOKIENAME,c.getName());
        assertNotNull(c.getValue());
        assertEquals("8080",updateCaptor.getValue());
    }

    @Test
    public void shouldRejectPinChangeWithInvalidPin() throws ServletException, IOException
    {
        AuthServlet as = new AuthServlet();
        as.setAuthi(mockAuthi);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getParameter(AuthI.PINNAME)).thenReturn("0000");
        when(req.getParameter(AuthI.UPDATEPINNAME)).thenReturn("8080");
        when(req.getParameter(AuthI.SAMENEWPINNAME)).thenReturn("8080");
        as.doPost(req, resp);
        assertEquals(0,statusCaptor.getAllValues().size());
        assertEquals(LOGINPAGE,redirectCaptor.getValue());
        assertEquals(0,cookieCaptor.getAllValues().size());
        assertEquals(0,updateCaptor.getAllValues().size());
    }

    @Test
    public void shouldRejectPinChangeWithPinMismatch() throws ServletException, IOException
    {
        AuthServlet as = new AuthServlet();
        as.setAuthi(mockAuthi);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getParameter(AuthI.PINNAME)).thenReturn("6502");
        when(req.getParameter(AuthI.UPDATEPINNAME)).thenReturn("8080");
        when(req.getParameter(AuthI.SAMENEWPINNAME)).thenReturn("8088");
        as.doPost(req, resp);
        assertEquals(0,statusCaptor.getAllValues().size());
        assertEquals("/mismatch.html",redirectCaptor.getValue());
        assertEquals(0,updateCaptor.getAllValues().size());
        // Still a valid login though
        assertEquals(1,cookieCaptor.getAllValues().size());
        Cookie c = cookieCaptor.getValue(); // that's good enough for me
        assertEquals(AuthI.COOKIENAME,c.getName());
        assertNotNull(c.getValue());
    }

}
