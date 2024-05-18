package com.rfacad.rvkybard.auth;

import static org.junit.Assert.*;

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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

    @Before
    public void setup()
    {
        toDelete = new ArrayList<>();
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
        a.init();
        a.setLoginPageUrl("foo");
        assertEquals(a,AuthS.getAuthI());
        assertEquals("foo",AuthS.getAuthI().getLoginPageUrl());
    }

    @Test
    public void shouldRejectBadCookie() throws IOException
    {
        AuthImpl a = new AuthImpl();
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
        a1.setPinFile(createPinFile("8080"));
        AuthImpl a2 = new AuthImpl();
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
        a.setPinFile(createPinFile("8080"));
        AuthTokenI token=a.login("8080"); // token expires in ten minutes
        String nonce = token.getNonce();
        assertTrue(a.findToken(nonce).isOK());

        a.logout(token);
        assertFalse(a.findToken(nonce).isOK());

        token=a.login("  8080  ");
        nonce = token.getNonce();
        assertTrue(a.findToken(nonce).isOK());

        a.logout(token);
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
