package com.rfacad.rvkybard.auth;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

import javax.servlet.http.Cookie;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

public class AuthImplTest
{
    File toDelete = null;

    @Before
    public void setup()
    {
        toDelete = null;
    }
    @After
    public void cleanup()
    {
        AuthS.setAuthI(null);
        if ( toDelete != null )
        {
            toDelete.delete();
        }
    }

    @Test
    public void shouldRegisterWithSingleton()
    {
        AuthS.setAuthI(null);
        assertNull(AuthS.getAuthI());
        AuthImpl a = new AuthImpl();
        a.init();
        a.setLoginPageUrl("foo");
        assertEquals(a,AuthS.getAuthI());
        assertEquals("foo",AuthS.getAuthI().getLoginPageUrl());
    }

    @Test
    public void shouldRejectBadCookie()
    {
        AuthImpl a = new AuthImpl();
        a.setPinFile("src/test/resources/pin0"); // empty file
        // no one is logged in!
        assertFalse(a.isCookieValid(null));
        assertFalse(a.isCookieValid(""));
        assertFalse(a.isCookieValid("68000"));
        assertFalse(a.isCookieValid("6502"));
        assertFalse(a.isCookieValid("8080"));
        assertFalse(a.checkForValidCookie(null));
        assertFalse(a.checkForValidCookie(new Cookie[0]));
        assertFalse(a.checkForValidCookie(new Cookie[] {new Cookie("foo","bar") }));
        assertFalse(a.checkForValidCookie(new Cookie[] {new Cookie("foo","bar"), new Cookie(AuthI.COOKIENAME,"68000") }));
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
        assertTrue(a1.checkForValidCookie(new Cookie[] {new Cookie("foo","bar"), new Cookie(AuthI.COOKIENAME,c1) }));

        assertNull(a2.login("8080"));
        String c2 = a2.login("68000");
        assertNotNull(c2);
        assertTrue(a2.isCookieValid(c2));
        assertTrue(a2.checkForValidCookie(new Cookie[] {new Cookie("foo","bar"), new Cookie(AuthI.COOKIENAME,c2) }));

        // Make sure those cookies are different!
        // It is POSSIBLE that a1.randy and a2.randy have the same seed, and will generate the same random cookie. But not very likely.
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

    @Test
    public void shouldCreateAndUpdatePinFile() throws IOException
    {
        File f = File.createTempFile("pintest", "txt");
        f.deleteOnExit();
        toDelete = f;
        cat("Initial",f);
        f.delete();
        assertFalse(f.exists());


        // Setting pinfile to something that doesn't exist
        // will create that file, and load it with default 6502
        AuthImpl a1 = new AuthImpl();
        a1.setPinFile(f.getAbsolutePath());
        assertTrue(f.exists());
        cat("Created",f);
        assertNull( a1.login("8080") );
        assertNotNull( a1.login("6502") );

        a1.writePinFile(f.getAbsolutePath(), "8080");
        cat("Updated",f);
        assertNull( a1.login("6502") );
        assertNotNull( a1.login("8080") );

        // Now read that file into a new auth object
        AuthImpl a2 = new AuthImpl();
        a2.setPinFile(f.getAbsolutePath());
        cat("Unchanged",f);
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
                try(BufferedReader in = new BufferedReader(new FileReader(f)))
                {
                    String s;
                    while( ( s = in.readLine() ) != null )
                    {
                        System.err.println(s);
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
