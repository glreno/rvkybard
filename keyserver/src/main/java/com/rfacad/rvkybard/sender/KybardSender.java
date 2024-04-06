package com.rfacad.rvkybard.sender;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class KybardSender
{
    Logger LOG = LoggerFactory.getLogger(KybardSender.class);
    private static final byte [] ALLUP = new byte[] { 0,0,0,0, 0,0,0,0 };

    private String dev;
    private OutputStream out; // very much NOT buffered

    public KybardSender(String dev)
    {
        this.dev = dev;
    }

    public KybardSender()
    {
        this.dev="";
    }

    public void setDev(String dev)
    {
        this.dev=dev;
    }

    protected void send(byte [] b) throws IOException
    {
        if ( out == null  )
        {
            out = new FileOutputStream(dev);
        }
        out.write(b);
        out.flush();
    }

    protected void doClose()
    {
        OutputStream o = out;
        out = null;
        try
        {
            o.close();
        }
        catch (IOException e)
        {
            LOG.error("Failed to close stream",e);
        }
    }

    public synchronized void shutdown()
    {
        doClose();
    }

    public synchronized void sendReleaseAllKeys() throws IOException
    {
        LOG.info("SEND RELEASE ALL");
        send(ALLUP);
    }

    public synchronized void sendKeys(byte flags,byte [] keycodes) throws IOException
    {
        byte [] buf = new byte[8];
        LOG.info("SEND {} {}",flags,keycodes);
        buf[0]=flags;
        int n = keycodes.length > 6 ? 6 : keycodes.length;
        for(int i=0; i < n; i++)
        {
            buf[i+2]=keycodes[i];
        }
        send(buf);
    }

    public void sendKey(byte keycode) throws IOException
    {
        sendKeys(KybardFlag.NONE.getBits(),new byte[] {keycode});
    }
}
