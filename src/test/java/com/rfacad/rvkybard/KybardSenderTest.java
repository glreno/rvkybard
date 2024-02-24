package com.rfacad.rvkybard;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rfacad.rvkybard.interfaces.KybardFlag;

//
//Copyright (c) 2024 Gerald Reno, Jr.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//

public class KybardSenderTest
{
    private File f;
    private String fn;
    @Before
    public void setup() throws IOException
    {
        f = File.createTempFile("kybardUnitTest","bin");
        fn = f.toString();
    }

    @After
    public void cleanup()
    {
        f.delete();
    }

    private byte [] readTestFile() throws IOException
    {
        // read AT MOST 64 bytes from the stream
        byte [] bigbuf=new byte[64];
        int bytesread=-1;
        try ( BufferedInputStream in = new BufferedInputStream(new FileInputStream(f)) )
        {
            bytesread = in.read(bigbuf);
        }
        if ( bytesread < 0 )
        {
            throw new IOException("Nothing to read!");
        }
        byte [] ret = new byte[bytesread];
        System.arraycopy(bigbuf,0,ret,0,bytesread);
        return ret;
    }

    @Test
    public void shouldSendReleaseAll() throws IOException
    {
        KybardSender ks = new KybardSender(fn);
        ks.sendReleaseAllKeys();
        ks.shutdown();
        byte [] b = readTestFile();

        assertEquals(8,b.length);
        for(int i=0;i<8;i++)
        {
            assertEquals(0,b[i]);
        }
    }

    @Test
    public void shouldSendShift() throws IOException
    {
        KybardSender ks = new KybardSender(fn);
        ks.sendKeys(KybardFlag.RIGHT_SHIFT.getBits(),new byte[] {5} ); // B
        ks.shutdown();
        byte [] b = readTestFile();

        assertEquals(8,b.length);
        assertEquals(32,b[0]);
        assertEquals(0,b[1]);
        assertEquals(5,b[2]);
        assertEquals(0,b[3]);
        assertEquals(0,b[4]);
        assertEquals(0,b[5]);
        assertEquals(0,b[6]);
        assertEquals(0,b[7]);
    }

    @Test
    public void shouldSendKey() throws IOException
    {
        KybardSender ks = new KybardSender(fn);
        ks.sendKey((byte)6); // c
        ks.shutdown();

        byte [] b = readTestFile();
        assertEquals(8,b.length);
        assertEquals(0,b[0]);
        assertEquals(0,b[1]);
        assertEquals(6,b[2]);
        assertEquals(0,b[3]);
        assertEquals(0,b[4]);
        assertEquals(0,b[5]);
        assertEquals(0,b[6]);
        assertEquals(0,b[7]);
    }

    @Test
    public void shouldSendKeys() throws IOException
    {
        KybardSender ks = new KybardSender(fn);
        ks.sendKeys((byte)32,new byte[] {6,7,8,9,10,11,12}); // shift c d e f g h i
        ks.shutdown();

        byte [] b = readTestFile();
        assertEquals(8,b.length);
        assertEquals(32,b[0]);
        assertEquals(0,b[1]);
        assertEquals(6,b[2]);
        assertEquals(7,b[3]);
        assertEquals(8,b[4]);
        assertEquals(9,b[5]);
        assertEquals(10,b[6]);
        assertEquals(11,b[7]); // h
        // The i will not be sent -- max six keys
    }

}
