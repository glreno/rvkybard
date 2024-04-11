package com.rfacad.rvkybard.sender;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import com.rfacad.rvkybard.interfaces.KybardLed;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
public class KybardReaderTest
{
    @Test(timeout=5000)
    public void shouldCloseInputStream() throws IOException
    {
        // Need to bypass init and use our own InputStream for this
        KybardReader kr = new KybardReader();
        InputStream in = mock(InputStream.class);
        kr.in=in;
        assertEquals(-1,kr.getLastRead());
        kr.shutdown();
        assertEquals(-1,kr.getLastRead());
        verify(in,times(1)).close();
        // run() should NOT block here!
        kr.run();
        assertEquals(-1,kr.getLastRead());
    }

    @Test(timeout=5000)
    public void shouldCloseInputStreamEx() throws IOException
    {
        // Same thing, but close throws an exception
        KybardReader kr = new KybardReader();
        InputStream in = mock(InputStream.class);
        doThrow(new IOException("xx")).when(in).close();
        kr.in=in;
        assertEquals(-1,kr.getLastRead());
        kr.shutdown();
        assertEquals(-1,kr.getLastRead());
        verify(in,times(1)).close();
        // run() should NOT block here!
        kr.run();
        assertEquals(-1,kr.getLastRead());
    }

    @Test(timeout=5000)
    public void shouldStopReadingAfterError() throws IOException
    {
        // Note that this tests an IOException, but closing a FileInputStream does not cause
        // read() to throw an exception. And read() doesn't respond to InterruptedException in real life.
        // This test therefore does NOT reflect a normal shutdown.

        // Create an InputStream that will block read() until a Latch is released.
        CountDownLatch cd = new CountDownLatch(1);
        KybardReader kr = new KybardReader();
        InputStream in = new InputStream() {
            @Override
            public int read() throws IOException
            {
                try
                {
                    cd.await();
                } catch (InterruptedException e)
                {
                    throw new IOException("interrupted");
                }
                return 65;
            }
        };
        kr.in=in;
        assertEquals(-1,kr.getLastRead());
        kr.startThread(); // this should immediately wait on the latch
        pause();
        kr.shutdown(); // this should close the inputstream with an IOException, causing it to return with lastread=-2
        pause();
        assertEquals(-2,kr.getLastRead());
        cd.countDown();
        pause();
        assertEquals(-2,kr.getLastRead());
        // At no point does it actually read that value
    }

    @Test(timeout=5000)
    public void shouldReadFile() throws IOException
    {
        // Same thing, but close throws an exception
        KybardReader kr = new KybardReader();
        kr.setDev("src/test/resources/flags1.txt");
        assertEquals(-1,kr.getLastRead());
        kr.init();
        pause();
        assertEquals(65,kr.getLastRead());
        kr.shutdown();
        // that sets in=null, causing it to either exit with the lastRead unchanged
        // or triggering a StreamClosed exception and returning with a -2
        pause();
    }

    @Test(timeout=5000)
    public void shouldReadFlags() throws IOException
    {
        // Need to bypass init and use our own InputStream for this
        KybardReader kr = new KybardReader();
        InputStream in = mock(InputStream.class);
        doReturn(7).when(in).read();
        kr.in=in;
        assertEquals(-1,kr.getLastRead());
        kr.startThread();
        pause();
        kr.shutdown();
        assertEquals(7,kr.getLastRead());
        assertEquals("{\"NUMLOCK\":true,\"CAPSLOCK\":true,\"SCROLLLOCK\":true,\"COMPOSE\":false,\"KANA\":false}",kr.getCurrentFlags());
    }

    private void verifyFlags(int f,String expected)
    {
        KybardReader kr = new KybardReader();
        kr.lastRead=f;
        assertEquals(expected,kr.getCurrentFlags());
    }
    @Test
    public void shouldDecodeFlags()
    {
        verifyFlags(-2,"{\"NUMLOCK\":false,\"CAPSLOCK\":false,\"SCROLLLOCK\":false,\"COMPOSE\":false,\"KANA\":false}");
        verifyFlags(-1,"{\"NUMLOCK\":false,\"CAPSLOCK\":false,\"SCROLLLOCK\":false,\"COMPOSE\":false,\"KANA\":false}");
        verifyFlags(0,"{\"NUMLOCK\":false,\"CAPSLOCK\":false,\"SCROLLLOCK\":false,\"COMPOSE\":false,\"KANA\":false}");
        verifyFlags(KybardLed.NUMLOCK.getBits(),"{\"NUMLOCK\":true,\"CAPSLOCK\":false,\"SCROLLLOCK\":false,\"COMPOSE\":false,\"KANA\":false}");
        verifyFlags(KybardLed.CAPSLOCK.getBits(),"{\"NUMLOCK\":false,\"CAPSLOCK\":true,\"SCROLLLOCK\":false,\"COMPOSE\":false,\"KANA\":false}");
        verifyFlags(KybardLed.SCROLLLOCK.getBits(),"{\"NUMLOCK\":false,\"CAPSLOCK\":false,\"SCROLLLOCK\":true,\"COMPOSE\":false,\"KANA\":false}");
        verifyFlags(KybardLed.COMPOSE.getBits(),"{\"NUMLOCK\":false,\"CAPSLOCK\":false,\"SCROLLLOCK\":false,\"COMPOSE\":true,\"KANA\":false}");
        verifyFlags(KybardLed.KANA.getBits(),"{\"NUMLOCK\":false,\"CAPSLOCK\":false,\"SCROLLLOCK\":false,\"COMPOSE\":false,\"KANA\":true}");
    }

    private void pause()
    {
        try
        {
            Thread.sleep(100);
        }
        catch(InterruptedException e)
        {
        }
    }
}
