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

public class AuthServletTest
{
    private AuthI mockAuthi;
    private HttpServletResponse resp;
    private ArgumentCaptor<Integer> statusCaptor;
    private ArgumentCaptor<Cookie> cookieCaptor;
    private ArgumentCaptor<String> headerNameCaptor;
    private ArgumentCaptor<String> headerValueCaptor;

    @After
    public void cleanup()
    {
        AuthS.setAuthI(null);
    }

    @Before
    public void setup()
    {
        mockAuthi = mock(AuthI.class);
        resp = mock(HttpServletResponse.class);
        statusCaptor = ArgumentCaptor.forClass(Integer.class);
        cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
        headerNameCaptor = ArgumentCaptor.forClass(String.class);
        headerValueCaptor = ArgumentCaptor.forClass(String.class);
        doNothing().when(resp).setStatus(statusCaptor.capture());
        doNothing().when(resp).addCookie(cookieCaptor.capture());
        doNothing().when(resp).addHeader(headerNameCaptor.capture(), headerValueCaptor.capture());
        // There is one good pin. Anything else will return null
        doReturn("abcd").when(mockAuthi).login("6502");
    }

    @Test
    public void shouldRejectNoAuthImpl() throws ServletException, IOException
    {
        AuthServlet as = new AuthServlet();
        as.setAuthi(null);
        as.init(null);
        HttpServletRequest req = mock(HttpServletRequest.class);
        as.doPost(req, resp);
        assertEquals(401,statusCaptor.getValue().intValue());
        assertEquals(0,cookieCaptor.getAllValues().size());
    }

    @Test
    public void shouldRejectNoPin() throws ServletException, IOException
    {
        AuthServlet as = new AuthServlet();
        as.setAuthi(mockAuthi);
        HttpServletRequest req = mock(HttpServletRequest.class);
        as.doPost(req, resp);
        assertEquals(401,statusCaptor.getValue().intValue());
        assertEquals(0,cookieCaptor.getAllValues().size());
    }

    @Test
    public void shouldRejectEmptyPin() throws ServletException, IOException
    {
        AuthServlet as = new AuthServlet();
        as.setAuthi(mockAuthi);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getParameter(AuthI.PINNAME)).thenReturn("");
        as.doPost(req, resp);
        assertEquals(401,statusCaptor.getValue().intValue());
        assertEquals(0,cookieCaptor.getAllValues().size());
    }

    @Test
    public void shouldRejectBadPin() throws ServletException, IOException
    {
        AuthServlet as = new AuthServlet();
        as.setAuthi(mockAuthi);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getParameter(AuthI.PINNAME)).thenReturn("0000");
        as.doPost(req, resp);
        assertEquals(401,statusCaptor.getValue().intValue());
        assertEquals(0,cookieCaptor.getAllValues().size());
    }

    @Test
    public void shouldLogIn() throws ServletException, IOException
    {
        AuthServlet as = new AuthServlet();
        as.setAuthi(mockAuthi);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getParameter(AuthI.PINNAME)).thenReturn("6502");
        as.doPost(req, resp);
        assertEquals(303,statusCaptor.getValue().intValue());
        assertEquals(1,cookieCaptor.getAllValues().size());
        Cookie c = cookieCaptor.getValue(); // that's good enough for me
        assertEquals(AuthI.COOKIENAME,c.getName());
        assertNotNull(c.getValue());
        assertEquals("Location",headerNameCaptor.getValue());
        assertEquals("/",headerValueCaptor.getValue());
    }
}
