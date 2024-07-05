package com.rfacad.rvkybard.auth;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.rfacad.rvkybard.interfaces.AuthConfigI;
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

public class AuthImplTest
{
    List<File> toDelete;
    AuthConfigI config;

    @Before
    public void setup()
    {
        toDelete = new ArrayList<>();
        config = mock(AuthConfigI.class);
    }
    @After
    public void cleanup()
    {
        AuthS.setAuthI(null);
        if ( toDelete != null )
        {
            for(File f : toDelete)
            {
                f.delete();
            }
        }
    }
    private String createPinFile(String pin) throws IOException
    {
        File f = File.createTempFile("pintest", "txt");
        String fn = f.getAbsolutePath();
        f.deleteOnExit();
        toDelete.add(f);
        AuthImpl a1 = new AuthImpl();
        a1.setConfig(config);
        a1.setPinFile(fn);
        a1.writePin(pin);
        return fn;
    }

    @Test
    public void shouldRegisterWithSingleton()
    {
        AuthS.setAuthI(null);
        assertNull(AuthS.getAuthI());
        AuthImpl a = new AuthImpl();
        a.setConfig(config);
        a.init();
        a.setLoginPageUrl("foo");
        assertEquals(a,AuthS.getAuthI());
        assertEquals("foo",AuthS.getAuthI().getLoginPageUrl());
        assertEquals(config,a.getConfig());
    }

    @Test
    public void shouldRejectBadCookie() throws IOException
    {
        AuthImpl a = new AuthImpl();
        a.setConfig(config);
        a.setPinFile(createPinFile(""));
        // no one is logged in!
        assertFalse(a.findToken(null).isOK());
        assertFalse(a.findToken("").isOK());
        assertFalse(a.findToken("68000").isOK());
        assertFalse(a.findToken("6502").isOK());
        assertFalse(a.findToken("8080").isOK());
        assertFalse(a.checkForValidCookie(null).isOK());
        assertFalse(a.checkForValidCookie(new Cookie[0]).isOK());
        assertFalse(a.checkForValidCookie(new Cookie[] {new Cookie("foo","bar") }).isOK());
        assertFalse(a.checkForValidCookie(new Cookie[] {new Cookie("foo","bar"), new Cookie(AuthI.COOKIENAME,"68000") }).isOK());
    }

    @Test
    public void shouldRejectExpiredToken() throws IOException
    {
        AuthImpl a1 = new AuthImpl();
        a1.setConfig(config);
        a1.setPinFile(createPinFile("8080"));

        assertNull(a1.login("68000"));
        AuthTokenI t1 = a1.login("8080");
        assertNotNull(t1);
        String c1 = t1.getNonce();
        assertNotNull(c1);
        assertTrue(a1.findToken(c1).isOK());

        a1.expireToken(c1);
        assertFalse(a1.findToken(c1).isOK());
    }

    @Test
    public void shouldRejectBadPin() throws IOException
    {
        AuthImpl a1 = new AuthImpl();
        a1.setConfig(config);
        a1.setPinFile(createPinFile("8080"));
        AuthImpl a2 = new AuthImpl();
        a2.setConfig(config);
        a2.setPinFile(createPinFile("68000")); // 68000

        assertNull(a1.login(null));
        assertNull(a1.login(""));
        assertNull(a1.login(" "));

        assertNull(a1.login("68000"));
        AuthTokenI t1 = a1.login("8080");
        assertNotNull(t1);
        String c1 = t1.getNonce();
        assertNotNull(c1);
        assertTrue(a1.findToken(c1).isOK());
        assertTrue(a1.checkForValidCookie(new Cookie[] {new Cookie("foo","bar"), new Cookie(AuthI.COOKIENAME,c1) }).isOK());

        assertNull(a2.login("8080"));
        AuthTokenI t2 = a2.login("68000");
        assertNotNull(t2);
        String c2 = t2.getNonce();
        assertNotNull(c2);
        assertTrue(a2.findToken(c2).isOK());
        assertTrue(a2.checkForValidCookie(new Cookie[] {new Cookie("foo","bar"), new Cookie(AuthI.COOKIENAME,c2) }).isOK());

        // Make sure those cookies are different!
        // It is POSSIBLE that a1.randy and a2.randy have the same seed, and will generate the same random cookie. But not very likely.
        assertFalse(a1.findToken(c2).isOK());
        assertFalse(a2.findToken(c1).isOK());
    }

    @Test
    public void shouldLogInAndOut() throws IOException
    {
        AuthImpl a = new AuthImpl();
        a.setConfig(config);
        doReturn(true).when(config).isSingleLoginMode();
        a.setPinFile(createPinFile("8080"));
        AuthTokenI token=a.login("8080"); // token expires in ten minutes
        String nonce = token.getNonce();
        assertTrue(a.findToken(nonce).isOK());

        a.logout(token);
        assertFalse(a.findToken(nonce).isOK());

        // Check that whitespace is ignored
        token=a.login("  8080  ");
        nonce = token.getNonce();
        assertTrue(a.findToken(nonce).isOK());

        // Verify that there can be only one login
        AuthTokenI token2=a.login("8080");
        String nonce2 = token2.getNonce();
        assertTrue(a.findToken(nonce2).isOK());
        assertFalse(a.findToken(nonce).isOK());

        a.logout(token2);
        assertFalse(a.findToken(nonce2).isOK());
        assertFalse(a.findToken(nonce).isOK());
    }

    @Test
    public void shouldLogInMultipleUsers() throws IOException
    {
        AuthImpl a = new AuthImpl();
        a.setConfig(config);
        doReturn(false).when(config).isSingleLoginMode();
        a.setPinFile(createPinFile("8080"));
        AuthTokenI token=a.login("8080"); // token expires in ten minutes
        String nonce = token.getNonce();
        assertTrue(a.findToken(nonce).isOK());

        a.logout(token);
        assertFalse(a.findToken(nonce).isOK());

        // Check that whitespace is ignored
        token=a.login("  8080  ");
        nonce = token.getNonce();
        assertTrue(a.findToken(nonce).isOK());

        // Verify that there can be two logins
        AuthTokenI token2=a.login("8080");
        String nonce2 = token2.getNonce();
        assertTrue(a.findToken(nonce2).isOK());
        assertTrue(a.findToken(nonce).isOK());

        // Logging out one doesn't log out the other
        a.logout(token2);
        assertFalse(a.findToken(nonce2).isOK());
        assertTrue(a.findToken(nonce).isOK());

        a.logout(token);
        assertFalse(a.findToken(nonce2).isOK());
        assertFalse(a.findToken(nonce).isOK());
    }

    @Test
    public void shouldCreateAndUpdatePinFile() throws IOException
    {
        File f = File.createTempFile("pintest", "txt");
        f.deleteOnExit();
        toDelete.add(f);
        //cat("Initial",f);
        f.delete();
        assertFalse(f.exists());

        // Setting pinfile to something that doesn't exist
        // will create that file, and load it with default 6502
        AuthImpl a1 = new AuthImpl();
        a1.setConfig(config);
        a1.setPinFile(f.getAbsolutePath());
        assertTrue(f.exists());
        //cat("Created",f);
        assertNull( a1.login("8080") );
        assertNotNull( a1.login("6502") );

        a1.writePin("8080");
        //cat("Updated",f);
        assertNull( a1.login("6502") );
        assertNotNull( a1.login("8080") );

        // Now read that file into a new auth object
        AuthImpl a2 = new AuthImpl();
        a2.setConfig(config);
        a2.setPinFile(f.getAbsolutePath());
        //cat("Unchanged",f);
        assertNull( a2.login("6502") );
        assertNotNull( a2.login("8080") );

        // Check file permissions -- should be 0600
        PosixFileAttributes attrs = Files.readAttributes(f.toPath(), PosixFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
        Set<PosixFilePermission> p = attrs.permissions();
        assertTrue(p.contains(PosixFilePermission.OWNER_READ));
        assertTrue(p.contains(PosixFilePermission.OWNER_WRITE));
        assertFalse(p.contains(PosixFilePermission.OWNER_EXECUTE));

        assertFalse(p.contains(PosixFilePermission.GROUP_READ));
        assertFalse(p.contains(PosixFilePermission.GROUP_WRITE));
        assertFalse(p.contains(PosixFilePermission.GROUP_EXECUTE));

        assertFalse(p.contains(PosixFilePermission.OTHERS_READ));
        assertFalse(p.contains(PosixFilePermission.OTHERS_WRITE));
        assertFalse(p.contains(PosixFilePermission.OTHERS_EXECUTE));
    }

    @Test
    public void shouldRefreshToken() throws IOException
    {
        // Log in, get token with nonce n1
        // findToken(n1), verify that it is not expired, and does not need refresh
        // force-update the token so it expires in one minute
        // findToken(n1), verify that it is not expired, and needs to be refreshed
        // make a mock REST call (n1)
        // First Rest call will have returned a 'refresh' token with a new nonce n2
        // make another mock REST call with the old nonce (n1)
        // Second Rest call will have returned a 'refresh' token with the same nonce n2 as the prior refresh
        // findtoken(n1), verify that it is not expired, needsRefresh, and has a refreshToken(n2) attached
        // findToken(n2), verify that it is not expired and does not need refresh

        AuthImpl a1 = new AuthImpl();
        a1.setConfig(config);
        a1.setPinFile(createPinFile("8080"));

        // Log in, get token with nonce n1
        AuthTokenI t1 = a1.login("8080");
        assertNotNull(t1);
        String n1  = t1.getNonce();

        // findToken(n1), verify that it is not expired, and does not need refresh
        AuthTokenI t2 = a1.findToken(n1);
        assertTrue(t2.isOK());
        assertTrue(t2.getLifespan() > AuthImpl.REFRESH_THRESHOLD);
        // force-update the token so it expires in one minute
        ((AuthToken)t2).setExpiration(System.currentTimeMillis()+30*1000); // 30 seconds in the future

        // findToken(n1), verify that it is not expired, and needs to be refreshed
        AuthTokenI t3 = a1.findToken(n1);
        assertTrue(t3.isOK());
        assertTrue(t3.getLifespan() < AuthImpl.REFRESH_THRESHOLD);

        Cookie [] cookies = new Cookie[] {new Cookie(AuthI.COOKIENAME,n1)};
        // make a mock REST call (n1)
        ArgumentCaptor<Cookie> cookieCaptor4 = ArgumentCaptor.forClass(Cookie.class);
        HttpServletRequest req4 = mock(HttpServletRequest.class);
        doReturn(cookies).when(req4).getCookies();
        HttpServletResponse resp4 = mock(HttpServletResponse.class);
        doNothing().when(resp4).addCookie(cookieCaptor4.capture());

        // make another mock REST call with the old nonce (n1)
        ArgumentCaptor<Cookie> cookieCaptor5 = ArgumentCaptor.forClass(Cookie.class);
        HttpServletRequest req5 = mock(HttpServletRequest.class);
        doReturn(cookies).when(req5).getCookies();
        HttpServletResponse resp5 = mock(HttpServletResponse.class);
        doNothing().when(resp5).addCookie(cookieCaptor5.capture());

        // Make the two classes one immediately after the other!
        // Ideally these should be tested on two threads!
        AuthTokenI t4 = a1.checkForValidCookie(req4, resp4);
        AuthTokenI t5 = a1.checkForValidCookie(req5, resp5);

        // First Rest call will have returned a 'refresh' token with a new nonce n2
        assertFalse(t3==t4);
        assertTrue(t4.isOK());
        assertTrue(t4.getLifespan() < AuthImpl.REFRESH_THRESHOLD);
        String n4=t4.getNonce();
        Cookie c4 = cookieCaptor4.getValue();
        assertNotNull(c4);
        assertEquals(n4,c4.getValue());

        // Second Rest call will have returned a 'refresh' token with the same nonce n2 as the prior refresh
        assertFalse(t3==t5);
        assertTrue(t5.isOK());
        assertTrue(t5.getLifespan() < AuthImpl.REFRESH_THRESHOLD);
        String n5=t5.getNonce();
        Cookie c5 = cookieCaptor5.getValue();
        assertNotNull(c5);
        assertEquals(n5,c5.getValue());

        assertEquals(n4,n5);
        assertFalse(n1.equals(n4));

        // findtoken(n1), verify that it is not expired, needsRefresh, and has a refreshToken(n2) attached
        AuthTokenI t6 = a1.findToken(n1);
        assertTrue(t6.isOK());
        assertTrue(t6.getLifespan() < AuthImpl.REFRESH_THRESHOLD);
        AuthTokenI t7 = ((AuthToken)t6).getRefreshToken();
        assertNotNull(t7);
        assertTrue(t7.isOK());
        assertEquals(n4,t7.getNonce());

        // findToken(n2), verify that it is not expired and does not need refresh
        AuthTokenI t8 = a1.findToken(n4);
        assertTrue(t8.isOK());
        assertTrue(t8.getLifespan() > AuthImpl.REFRESH_THRESHOLD);

    }

    private void cat(String text,File f)
    {
        boolean exists = f.exists();
        System.err.println(text+"... Exists?"+exists);
        if (exists)
        {
            try
            {
                PosixFileAttributes attrs = Files.readAttributes(f.toPath(), PosixFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
                System.err.println(attrs.isRegularFile()+" "+attrs.size()+" "+attrs.permissions());
                try(FileInputStream in = new FileInputStream(f))
                {
                    int b;
                    while( ( b = in.read() ) >= 0 )
                    {
                        System.err.print(Integer.toHexString(b)+" ");
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            System.err.println(".");
        }
    }
}
