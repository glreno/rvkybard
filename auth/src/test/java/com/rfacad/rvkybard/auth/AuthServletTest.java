package com.rfacad.rvkybard.auth;

import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rfacad.rvkybard.interfaces.AuthI;
import com.rfacad.rvkybard.interfaces.AuthS;

public class AuthServletTest
{
    private AuthI mockAuthi;

    @After
    public void cleanup()
    {
        AuthS.setAuthI(null);
    }

    @Before
    public void setup()
    {
        mockAuthi = mock(AuthI.class);
    }
    @Test
    public void shouldRejectNoAuthImpl()
    {
    }

    @Test
    public void shouldRejectNoPin()
    {
        AuthServlet as = new AuthServlet();
        as.setAuthi(mockAuthi);
        as.doPost(req, resp);
    }
}
