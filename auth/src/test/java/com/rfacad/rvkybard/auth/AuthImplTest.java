package com.rfacad.rvkybard.auth;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import com.rfacad.rvkybard.interfaces.AuthS;

public class AuthImplTest
{
    @After
    public void cleanup()
    {
        AuthS.setAuthI(null);
    }

    @Test
    public void shouldRegisterWithSingleton()
    {
        AuthS.setAuthI(null);
        assertNull(AuthS.getAuthI());
        AuthImpl a = new AuthImpl();
        a.startup();
        assertEquals(a,AuthS.getAuthI());
    }

    @Test
    public void shouldRejectBadCookie()
    {
        AuthImpl a = new AuthImpl();
        a.setPinFile("/this/file/does/not/exist");
        // no one is logged in!
        assertFalse(a.isCookieValid(null));
        assertFalse(a.isCookieValid(""));
        assertFalse(a.isCookieValid("68000"));
        assertFalse(a.isCookieValid("6502"));
        assertFalse(a.isCookieValid("8080"));
    }

    @Test
    public void shouldRejectBadPin()
    {
        AuthImpl a1 = new AuthImpl();
        a1.setPinFile("src/test/resources/pin1"); // 8080
        AuthImpl a2 = new AuthImpl();
        a2.setPinFile("src/test/resources/pin2"); // 68000

        assertNull(a1.login("68000"));
        String c1 = a1.login("8080");
        assertNotNull(c1);
        assertTrue(a1.isCookieValid(c1));

        assertNull(a2.login("8080"));
        String c2 = a2.login("68000");
        assertNotNull(c2);
        assertTrue(a2.isCookieValid(c2));

        // Make sure those cookies are different!
        assertFalse(a1.isCookieValid(c2));
        assertFalse(a2.isCookieValid(c1));
    }

    @Test
    public void shouldLogInAndOut()
    {
        AuthImpl a = new AuthImpl();
        a.setPinFile("src/test/resources/pin1");
        String cookie=a.login("8080");
        assertTrue(a.isCookieValid(cookie));
        a.logout(cookie);
        assertFalse(a.isCookieValid(cookie));
    }
}
